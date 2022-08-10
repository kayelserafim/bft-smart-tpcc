package bftsmart.microbenchmark.tpcc.server.transaction.orderstatus;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.domain.CommandRequest;
import bftsmart.microbenchmark.tpcc.domain.CommandResponse;
import bftsmart.microbenchmark.tpcc.domain.TransactionType;
import bftsmart.microbenchmark.tpcc.server.repository.CustomerRepository;
import bftsmart.microbenchmark.tpcc.server.repository.OrderLineRepository;
import bftsmart.microbenchmark.tpcc.server.repository.OrderRepository;
import bftsmart.microbenchmark.tpcc.server.transaction.Transaction;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.input.OrderStatusInput;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.output.OrderLineOutput;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.output.OrderStatusOutput;
import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.table.Order;
import bftsmart.microbenchmark.tpcc.table.OrderLine;
import bftsmart.microbenchmark.tpcc.util.Dates;
import bftsmart.microbenchmark.tpcc.util.Numbers;

public class OrderStatusTransaction implements Transaction {

    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private OrderRepository orderRepository;
    @Inject
    private OrderLineRepository orderLineRepository;

    @Override
    public TransactionType transactionType() {
        return TransactionType.ORDER_STATUS;
    }

    @Override
    public CommandResponse process(final CommandRequest command) {
        OrderStatusInput input = (OrderStatusInput) command;
        OrderStatusOutput orderStatusOutput = new OrderStatusOutput().withDateTime(LocalDateTime.now());

        int warehouseId = input.getWarehouseId();
        int districtId = input.getDistrictId();
        Customer customer;
        if (BooleanUtils.isTrue(input.getCustomerByName())) {
            // clause 2.6.2.2 (dot 3, Case 2)
            customer = customerRepository.find(input.getCustomerLastName(), districtId, warehouseId);
            if (customer == null) {
                String text = "C_LAST [%s] not found. D_ID [%s], W_ID [%s]";
                String msg = String.format(text, input.getCustomerLastName(), districtId, warehouseId);
                return new CommandResponse().withStatus(-1).withResponse(msg);
            }
        } else {
            // clause 2.6.2.2 (dot 3, Case 1)
            customer = customerRepository.find(input.getCustomerId(), districtId, warehouseId);
        }

        orderStatusOutput.withWarehouseId(warehouseId)
                .withDistrictId(districtId)
                .withCustomerId(customer.getCustomerId())
                .withCustomerFirst(customer.getFirst())
                .withCustomerMiddle(customer.getMiddle())
                .withCustomerLast(customer.getLast())
                .withCustomerBalance(customer.getBalance());

        Order order = orderRepository.findByCustomerId(customer.getCustomerId(), districtId, warehouseId);
        if (order != null) {
            orderStatusOutput.withOrderId(order.getOrderId())
                    .withEntryDate(order.getEntryDate())
                    .withCarrierId(Numbers.toInt(order.getCarrierId()));
            // clause 2.6.2.2 (dot 5)
            List<OrderLine> orderLines = orderLineRepository.find(order.getOrderId(), districtId, warehouseId);

            OrderLineOutput[] orderLineOutputs = new OrderLineOutput[orderLines.size()];
            for (OrderLine orderLine : orderLines) {
                int index = orderLines.indexOf(orderLine);
                orderLineOutputs[index] =
                        new OrderLineOutput().withSupplierWarehouseId(orderLine.getSupplyWarehouseId())
                                .withItemId(orderLine.getItemId())
                                .withOrderQuantities(orderLine.getQuantity())
                                .withAmount(orderLine.getAmount())
                                .withDeliveryDateTime(Numbers.toLong(orderLine.getDeliveryDateTime()));
            }
            orderStatusOutput.withOrderLines(orderLineOutputs);
        }

        return new CommandResponse().withStatus(0).withResponse(outputScreen(orderStatusOutput));
    }

    private String outputScreen(OrderStatusOutput orderStatus) {
        StringBuilder message = new StringBuilder();
        message.append("\n");
        message.append("+-------------------------- ORDER-STATUS -------------------------+\n");
        message.append(" Date: ");
        message.append(Dates.format(orderStatus.getDateTime(), Dates.DATE_TIME_FORMAT));
        message.append("\n\n Warehouse: ");
        message.append(orderStatus.getWarehouseId());
        message.append("\n District:  ");
        message.append(orderStatus.getDistrictId());
        message.append("\n\n Customer:  ");
        message.append(orderStatus.getCustomerId());
        message.append("\n   Name:    ");
        message.append(orderStatus.getCustomerFirst());
        message.append(" ");
        message.append(orderStatus.getCustomerMiddle());
        message.append(" ");
        message.append(orderStatus.getCustomerLast());
        message.append("\n   Balance: ");
        message.append(orderStatus.getCustomerBalance());
        message.append("\n\n");
        if (orderStatus.getOrderId() == 0) {
            message.append(" Customer has no orders placed.\n");
        } else {
            message.append(" Order-Number: ");
            message.append(orderStatus.getOrderId());
            message.append("\n    Entry-Date: ");
            message.append(orderStatus.getEntryDate());
            message.append("\n    Carrier-Number: ");
            message.append(orderStatus.getCarrierId());
            message.append("\n\n");
            if (ArrayUtils.isNotEmpty(orderStatus.getOrderLines())) {
                message.append(" [Supply_W - Item_ID - Qty - Amount - Delivery-Date]\n");

                for (OrderLineOutput oLine : orderStatus.getOrderLines()) {
                    message.append("[");
                    message.append(oLine.getSupplierWarehouseId());
                    message.append(" - ");
                    message.append(oLine.getItemId());
                    message.append(" - ");
                    message.append(oLine.getOrderQuantities());
                    message.append(" - ");
                    message.append(oLine.getAmount());
                    message.append(" - ");
                    // 2.6.3.3 (case 2)
                    message.append(oLine.getDeliveryDateTime() == 0 ? "99-99-9999" : oLine.getDeliveryDateTime());
                    message.append("]");
                }

                message.append("\n");
            } else {
                message.append(" This Order has no Order-Lines.\n");
            }
        }
        message.append("+-----------------------------------------------------------------+\n\n");
        return message.toString();
    }

}
