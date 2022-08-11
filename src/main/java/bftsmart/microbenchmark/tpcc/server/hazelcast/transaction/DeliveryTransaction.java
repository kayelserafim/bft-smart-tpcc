package bftsmart.microbenchmark.tpcc.server.hazelcast.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionOptions;

import bftsmart.microbenchmark.tpcc.config.TPCCConstants;
import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.CustomerRepository;
import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.NewOrderRepository;
import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.OrderLineRepository;
import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.OrderRepository;
import bftsmart.microbenchmark.tpcc.server.transaction.Transaction;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionResponse;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionType;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.request.DeliveryRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.response.DeliveryOutput;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.response.OrderResponse;
import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.table.NewOrder;
import bftsmart.microbenchmark.tpcc.table.Order;
import bftsmart.microbenchmark.tpcc.table.OrderLine;
import bftsmart.microbenchmark.tpcc.util.Dates;

public class DeliveryTransaction implements Transaction {

    @Inject
    private NewOrderRepository newOrderRepository;
    @Inject
    private OrderRepository orderRepository;
    @Inject
    private OrderLineRepository orderLineRepository;
    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private TransactionOptions transactionOptions;
    @Inject
    private HazelcastInstance hazelcastInstance;

    @Override
    public TransactionType transactionType() {
        return TransactionType.DELIVERY;
    }

    @Override
    public TransactionResponse process(final TransactionRequest command) {
        DeliveryRequest input = (DeliveryRequest) command;
        DeliveryOutput deliveryOutput = new DeliveryOutput().withDateTime(LocalDateTime.now())
                .withWarehouseId(input.getWarehouseId())
                .withOrderCarrierId(input.getOrderCarrierId());

        TransactionContext txCxt = hazelcastInstance.newTransactionContext(transactionOptions);
        txCxt.beginTransaction();

        // clause 2.7.4.1
        List<OrderResponse> orderIds = new ArrayList<>();
        for (int districtId = 1; districtId <= TPCCConstants.DIST_PER_WHSE; districtId++) {
            Integer warehouseId = input.getWarehouseId();
            // clause 2.7.4.2 (dot 3)
            NewOrder newOrder = newOrderRepository.findFirst(districtId, warehouseId);
            if (newOrder != null) {
                int orderId = newOrder.getOrderId();
                Order order = orderRepository.findByOrderId(orderId, districtId, warehouseId);
                if (order == null) {
                    String message = String.format("Order [%s] not found", orderId);
                    return new TransactionResponse().withStatus(-1).withResponse(message);
                }
                orderIds.add(new OrderResponse().withDistrictId(districtId).withOrderId(orderId));
                
                newOrderRepository.delete(txCxt, warehouseId, districtId, orderId);

                Customer customer = customerRepository.find(order.getCustomerId(), districtId, warehouseId);

                List<OrderLine> orderLineList = orderLineRepository.find(orderId, districtId, warehouseId)
                        .stream()
                        .map(orderLine -> orderLine.withDeliveryDateTime(Dates.now()))
                        .map(orderLine -> orderLineRepository.save(txCxt, orderLine))
                        .collect(Collectors.toList());

                BigDecimal orderLineTotal =
                        orderLineList.stream().map(OrderLine::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

                customer.addBalance(orderLineTotal);
                
                customerRepository.save(txCxt, customer);
                orderRepository.save(txCxt, order.withCarrierId(input.getOrderCarrierId()));
            }
        }
        
        deliveryOutput.withOrderIds(orderIds);
        
        txCxt.commitTransaction();

        return new TransactionResponse().withStatus(0).withResponse(outputScreen(deliveryOutput));
    }

    private String outputScreen(DeliveryOutput delivery) {
        StringBuilder message = new StringBuilder();
        message.append("\n+---------------------------- DELIVERY ---------------------------+\n");
        message.append(" Date: ");
        message.append(Dates.format(delivery.getDateTime(), Dates.DATE_TIME_FORMAT));
        message.append("\n\n Warehouse: ");
        message.append(delivery.getWarehouseId());
        message.append("\n Carrier:   ");
        message.append(delivery.getOrderCarrierId());
        message.append("\n\n Delivered Orders\n");
        for (OrderResponse order : delivery.getOrderIds()) {
            if (order.getDistrictId() >= 0) {
                message.append("  District ");
                message.append(order.getDistrictId() < TPCCConstants.DIST_PER_WHSE ? " " : "");
                message.append(order.getDistrictId());
                message.append(": Order number ");
                message.append(order.getOrderId());
                message.append(" was delivered.\n");
            } else {
                // clause 2.7.4.2 (dot 3) : delivery skipped
                message.append("  District ");
                message.append(order.getDistrictId() < TPCCConstants.DIST_PER_WHSE ? " " : "");
                message.append(order.getDistrictId());
                message.append(": No orders to be delivered.\n");
            }
        }
        message.append("+-----------------------------------------------------------------+\n\n");
        return message.toString();
    }

}
