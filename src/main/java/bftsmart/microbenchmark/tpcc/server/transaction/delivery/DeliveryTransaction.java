package bftsmart.microbenchmark.tpcc.server.transaction.delivery;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.config.TPCCConstants;
import bftsmart.microbenchmark.tpcc.domain.CommandRequest;
import bftsmart.microbenchmark.tpcc.domain.CommandResponse;
import bftsmart.microbenchmark.tpcc.domain.TransactionType;
import bftsmart.microbenchmark.tpcc.server.repository.CustomerRepository;
import bftsmart.microbenchmark.tpcc.server.repository.NewOrderRepository;
import bftsmart.microbenchmark.tpcc.server.repository.OrderLineRepository;
import bftsmart.microbenchmark.tpcc.server.repository.OrderRepository;
import bftsmart.microbenchmark.tpcc.server.transaction.Transaction;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.input.DeliveryInput;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.output.DeliveryOutput;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.output.OrderOutput;
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

    @Override
    public TransactionType transactionType() {
        return TransactionType.DELIVERY;
    }

    @Override
    public CommandResponse process(final CommandRequest command) {
        DeliveryInput input = (DeliveryInput) command;
        DeliveryOutput deliveryOutput = new DeliveryOutput().withDateTime(LocalDateTime.now())
                .withWarehouseId(input.getWarehouseId())
                .withOrderCarrierId(input.getOrderCarrierId());

        // clause 2.7.4.1
        List<Customer.Builder> customers = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        List<OrderLine> orderLines = new ArrayList<>();
        for (int districtId = 1; districtId <= TPCCConstants.DIST_PER_WHSE; districtId++) {
            Integer warehouseId = input.getWarehouseId();
            // clause 2.7.4.2 (dot 3)
            Integer orderId =
                    newOrderRepository.findFirst(districtId, warehouseId).map(NewOrder::getOrderId).orElse(-1);

            if (orderId != -1) {
                Order order = orderRepository.findByOrderId(orderId, districtId, warehouseId);
                if (order == null) {
                    String message = String.format("Order [%s] not found", orderId);
                    return new CommandResponse().withStatus(-1).withResponse(message);
                }
                Customer customer = customerRepository.find(order.getCustomerId(), districtId, warehouseId);

                List<OrderLine> orderLineList = orderLineRepository.find(orderId, districtId, warehouseId)
                        .stream()
                        .map(OrderLine::from)
                        .map(builder -> builder.deliveryDateTime(Dates.now()))
                        .map(OrderLine.Builder::build)
                        .collect(Collectors.toList());

                BigDecimal orderLineTotal =
                        orderLineList.stream().map(OrderLine::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

                customers.add(Customer.from(customer).addBalance(orderLineTotal));
                orders.add(Order.from(order).carrierId(input.getOrderCarrierId()).build());
                orderLines.addAll(orderLineList);
            }
        }

        OrderOutput[] orderOutputs = new OrderOutput[orders.size()];
        orders.forEach(order -> {
            int index = orders.indexOf(order);
            orderRepository.save(order);
            newOrderRepository.delete(order);
            orderOutputs[index] =
                    new OrderOutput().withDistrictId(order.getDistrictId()).withOrderId(order.getOrderId());
        });
        deliveryOutput.withOrderIds(orderOutputs);
        customers.stream().map(builder -> builder.deliveryCntIncrement().build()).forEach(customerRepository::save);
        orderLines.forEach(orderLineRepository::save);

        return new CommandResponse().withStatus(0).withResponse(outputScreen(deliveryOutput));
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
        for (OrderOutput order : delivery.getOrderIds()) {
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
