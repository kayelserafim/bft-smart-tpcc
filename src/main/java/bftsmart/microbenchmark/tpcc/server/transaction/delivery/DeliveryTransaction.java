package bftsmart.microbenchmark.tpcc.server.transaction.delivery;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TransactionType;
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
import bftsmart.microbenchmark.tpcc.util.Times;

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
    public TPCCCommand process(final TPCCCommand command) {
        DeliveryInput input = (DeliveryInput) command.getRequest();
        DeliveryOutput.Builder deliveryBuilder = DeliveryOutput.builder()
                .dateTime(LocalDateTime.now())
                .warehouseId(input.getWarehouseId())
                .orderCarrierId(input.getOrderCarrierId());

        // clause 2.7.4.1
        List<Customer.Builder> customers = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        List<OrderLine> orderLines = new ArrayList<>();
        for (int districtId = 1; districtId <= TPCCConfig.DIST_PER_WHSE; districtId++) {
            Integer warehouseId = input.getWarehouseId();
            Integer orderId =
                    newOrderRepository.findFirst(districtId, warehouseId).map(NewOrder::getOrderId).orElse(-1);

            if (orderId != -1) {
                Order order = orderRepository.findByOrderId(orderId, districtId, warehouseId);
                if (order == null) {
                    String message = String.format("Order [%s] not found", orderId);
                    return TPCCCommand.newErrorMessage(command, message);
                }
                Customer customer = customerRepository.find(order.getCustomerId(), districtId, warehouseId);

                List<OrderLine> orderLineList = orderLineRepository.find(orderId, districtId, warehouseId)
                        .parallelStream()
                        .map(OrderLine::from)
                        .map(builder -> builder.deliveryDateTime(Times.now()))
                        .map(OrderLine.Builder::build)
                        .collect(Collectors.toList());

                BigDecimal orderLineTotal = orderLineList.parallelStream()
                        .map(OrderLine::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                customers.add(Customer.from(customer).addBalance(orderLineTotal));
                orders.add(Order.from(order).carrierId(input.getOrderCarrierId()).build());
                orderLines.addAll(orderLineList);
            }
        }

        orders.forEach(order -> {
            orderRepository.save(order);
            newOrderRepository.delete(order);
            deliveryBuilder.orderId(new OrderOutput(order.getDistrictId(), order.getOrderId()));
        });
        customers.parallelStream()
                .map(builder -> builder.deliveryCntIncrement().build())
                .forEach(customerRepository::save);
        orderLines.forEach(orderLineRepository::save);

        return TPCCCommand.newSuccessMessage(command, outputScreen(deliveryBuilder.build()));
    }

    private String outputScreen(DeliveryOutput delivery) {
        StringBuilder message = new StringBuilder();
        message.append("\n+---------------------------- DELIVERY ---------------------------+\n");
        message.append(" Date: ");
        message.append(delivery.getDateTime().format(Times.DATE_TIME_FORMAT));
        message.append("\n\n Warehouse: ");
        message.append(delivery.getWarehouseId());
        message.append("\n Carrier:   ");
        message.append(delivery.getOrderCarrierId());
        message.append("\n\n Delivered Orders\n");
        for (OrderOutput order : delivery.getOrderIds()) {
            if (order.getDistrictId() >= 0) {
                message.append("  District ");
                message.append(order.getDistrictId() < TPCCConfig.DIST_PER_WHSE ? " " : "");
                message.append(order.getDistrictId());
                message.append(": Order number ");
                message.append(order.getOrderId());
                message.append(" was delivered.\n");
            } else {
                // clause 2.7.4.2 (dot 3) : delivery skipped
                message.append("  District ");
                message.append(order.getDistrictId() < TPCCConfig.DIST_PER_WHSE ? " " : "");
                message.append(order.getDistrictId());
                message.append(": No orders to be delivered.\n");
            }
        }
        message.append("+-----------------------------------------------------------------+\n\n");
        return message.toString();
    }

}
