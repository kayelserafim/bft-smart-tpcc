package bftsmart.microbenchmark.tpcc.server.conflict;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TransactionType;
import bftsmart.microbenchmark.tpcc.server.repository.CustomerRepository;
import bftsmart.microbenchmark.tpcc.server.repository.NewOrderRepository;
import bftsmart.microbenchmark.tpcc.server.repository.OrderRepository;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.input.DeliveryInput;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.input.NewOrderInput;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.input.OrderStatusInput;
import bftsmart.microbenchmark.tpcc.server.transaction.payment.input.PaymentInput;
import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.table.NewOrder;
import bftsmart.microbenchmark.tpcc.table.Order;
import bftsmart.util.MultiOperationRequest;
import parallelism.MessageContextPair;
import parallelism.late.ConflictDefinition;

@Singleton
public class TPCCConflictDefinition extends ConflictDefinition {

    @Inject
    private NewOrderRepository newOrderRepository;
    @Inject
    private OrderRepository orderRepository;
    @Inject
    private CustomerRepository customerRepository;

    @Override
    public boolean isDependent(MessageContextPair arg0, MessageContextPair arg1) {
        TPCCCommand command1 = TPCCCommand.deserialize(new MultiOperationRequest(arg0.request.getContent()));
        TPCCCommand command2 = TPCCCommand.deserialize(new MultiOperationRequest(arg1.request.getContent()));

        if (TransactionConflicts.hasNotConflict(command1.getTransactionType(), command2.getTransactionType())) {
            return false;
        }
        if (command1.getTransactionType() == TransactionType.STOCK_LEVEL
                || command2.getTransactionType() == TransactionType.STOCK_LEVEL) {
            return true;
        }
        return CollectionUtils.containsAny(getCustomerIds(command1), getCustomerIds(command2));
    }

    private List<Integer> getCustomerIds(TPCCCommand command) {
        switch (command.getTransactionType()) {
        case NEW_ORDER:
            NewOrderInput newOrderInput = (NewOrderInput) command.getRequest();
            return Arrays.asList(newOrderInput.getCustomerId());
        case PAYMENT:
            PaymentInput paymentInput = (PaymentInput) command.getRequest();
            return Arrays.asList(getCustomerId(paymentInput));
        case ORDER_STATUS:
            OrderStatusInput orderStatusInput = (OrderStatusInput) command.getRequest();
            return Arrays.asList(getCustomerId(orderStatusInput));
        case DELIVERY:
            DeliveryInput deliveryInput = (DeliveryInput) command.getRequest();
            return getCustomerIds(deliveryInput);
        case STOCK_LEVEL:
        default:
            throw new IllegalStateException("Stock Level shoud be resolved in transaction conflicts");
        }
    }

    public Integer getCustomerId(PaymentInput input) {
        return getCustomerId(input.getCustomerLastName(), input.getCustomerId(), input.getDistrictId(),
                input.getWarehouseId());
    }

    public Integer getCustomerId(OrderStatusInput input) {
        return getCustomerId(input.getCustomerLastName(), input.getCustomerId(), input.getDistrictId(),
                input.getWarehouseId());
    }

    public Integer getCustomerId(String customerLastName, Integer customerId, Integer districtId, Integer warehouseId) {
        if (customerId != null) {
            return customerId;
        }
        // clause 2.6.2.2 (dot 3, Case 2)
        Customer customer = customerRepository.find(customerLastName, districtId, warehouseId);
        if (customer == null) {
            return -1;
        }
        return customer.getCustomerId();
    }

    public List<Integer> getCustomerIds(DeliveryInput input) {
        List<Integer> customerIds = new ArrayList<>();
        for (int districtId = 1; districtId <= TPCCConfig.DIST_PER_WHSE; districtId++) {
            Integer warehouseId = input.getWarehouseId();
            // clause 2.7.4.2 (dot 3)
            Integer orderId =
                    newOrderRepository.findFirst(districtId, warehouseId).map(NewOrder::getOrderId).orElse(-1);

            if (orderId != -1) {
                Order order = orderRepository.findByOrderId(orderId, districtId, warehouseId);
                if (order != null) {
                    Customer customer = customerRepository.find(order.getCustomerId(), districtId, warehouseId);
                    customerIds.add(customer.getCustomerId());
                }
            }
        }
        return customerIds;
    }
}
