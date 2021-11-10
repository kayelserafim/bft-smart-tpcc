package bftsmart.microbenchmark.tpcc.server.transaction.delivery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;
import bftsmart.microbenchmark.tpcc.repository.CustomerRepository;
import bftsmart.microbenchmark.tpcc.repository.NewOrderRepository;
import bftsmart.microbenchmark.tpcc.repository.OrderLineRepository;
import bftsmart.microbenchmark.tpcc.repository.OrderRepository;
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
    private ObjectMapper objectMapper;
    @Inject
    private NewOrderRepository newOrderRepository;
    @Inject
    private OrderRepository orderRepository;
    @Inject
    private OrderLineRepository orderLineRepository;
    @Inject
    private CustomerRepository customerRepository;

    @Override
    public TPCCCommandType commandType() {
        return TPCCCommandType.DELIVERY;
    }

    @Override
    public TPCCCommand process(final TPCCCommand aRequest) {
        Map<String, Serializable> params = aRequest.getRequest();

        DeliveryInput input = objectMapper.convertValue(params, DeliveryInput.class);
        DeliveryOutput.Builder deliveryBuilder = DeliveryOutput.builder()
                .dateTime(LocalDateTime.now())
                .warehouseId(input.getWarehouseId())
                .orderCarrierId(input.getOrderCarrierId());

        // clause 2.7.4.1
        List<OrderOutput> orderIds = new ArrayList<>();
        for (int districtId = 1; districtId <= TPCCConfig.DIST_PER_WHSE; districtId++) {
            Integer warehouseId = input.getWarehouseId();
            Integer orderId =
                    newOrderRepository.findFirstBy(districtId, warehouseId).map(NewOrder::getOrderId).orElse(-1);
            orderIds.add(new OrderOutput(districtId, orderId));

            if (orderId != -1) {
                newOrderRepository.deleteBy(orderId, districtId, warehouseId);
                Order order = orderRepository.findByOrderId(orderId, districtId, warehouseId);
                if (order == null) {
                    String message = String.format("Order [%s] not found", orderId);
                    return TPCCCommand.newErrorMessage(aRequest, message);
                }
                orderRepository.save(Order.from(order).carrierId(input.getOrderCarrierId()).build());

                List<OrderLine> orderLines = orderLineRepository.findBy(orderId, districtId, warehouseId)
                        .parallelStream()
                        .map(OrderLine::from)
                        .map(builder -> builder.deliveryDateTime(Times.currentTimeMillis()))
                        .map(OrderLine.Builder::build)
                        .map(orderLineRepository::save)
                        .collect(Collectors.toList());

                BigDecimal orderLineTotal =
                        orderLines.parallelStream().map(OrderLine::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

                Optional<Customer> customer = customerRepository.find(order.getCustomerId(), districtId, warehouseId)
                        .map(Customer::from)
                        .map(builder -> builder.addBalance(orderLineTotal).deliveryCntIncrement())
                        .map(Customer.Builder::build)
                        .map(customerRepository::save);

                if (!customer.isPresent()) {
                    String msg = "Customer [%s] not found. D_ID [%s], W_ID [%s]";
                    return TPCCCommand.newErrorMessage(aRequest, msg, order.getCustomerId(), districtId, warehouseId);
                }
            }
        }
        deliveryBuilder.orderIds(orderIds);

        return TPCCCommand.newSuccessMessage(aRequest, outputScreen(deliveryBuilder.build()));
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
