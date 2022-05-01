package bftsmart.microbenchmark.tpcc.server.conflict;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.server.repository.CustomerRepository;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.input.NewOrderInput;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.input.OrderStatusInput;
import bftsmart.microbenchmark.tpcc.server.transaction.payment.input.PaymentInput;
import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.util.Numbers;
import bftsmart.util.MultiOperationRequest;
import parallelism.MessageContextPair;
import parallelism.late.ConflictDefinition;

@Singleton
public class TPCCConflictDefinition extends ConflictDefinition {

    private Map<String, Boolean> conflictMap = new ConcurrentHashMap<>();

    @Inject
    private CustomerRepository customerRepository;

    @Override
    public boolean isDependent(MessageContextPair arg0, MessageContextPair arg1) {
        TPCCCommand command1 = TPCCCommand.deserialize(new MultiOperationRequest(arg0.request.getContent()));
        TPCCCommand command2 = TPCCCommand.deserialize(new MultiOperationRequest(arg1.request.getContent()));

        if (TransactionConflicts.hasNotConflict(command1.getTransactionType(), command2.getTransactionType())) {
            conflictMap.put(command1.getCommandId(), Boolean.FALSE);
            conflictMap.put(command2.getCommandId(), Boolean.FALSE);
            return false;
        }
        if (TransactionConflicts.isPessimistic(command1.getTransactionType(), command2.getTransactionType())) {
            conflictMap.put(command1.getCommandId(), Boolean.TRUE);
            conflictMap.put(command2.getCommandId(), Boolean.TRUE);
            return true;
        }
        Integer customer1 = getCustomerId(command1);
        Integer customer2 = getCustomerId(command2);

        boolean isDependent = customer1 != null && customer1.equals(customer2);
        conflictMap.put(command1.getCommandId(), isDependent);
        conflictMap.put(command2.getCommandId(), isDependent);

        return isDependent;
    }

    public Boolean isDependent(String commandId) {
        return conflictMap.get(commandId);
    }

    private Integer getCustomerId(TPCCCommand command) {
        switch (command.getTransactionType()) {
        case NEW_ORDER:
            NewOrderInput newOrder = (NewOrderInput) command.getRequest();
            return newOrder.getCustomerId();
        case PAYMENT:
            PaymentInput payment = (PaymentInput) command.getRequest();
            return Optional.of(payment)
                    .map(PaymentInput::getCustomerId)
                    .filter(Numbers::isNotNullOrZero)
                    .orElseGet(() -> {
                        String lastName = payment.getCustomerLastName();
                        Integer districtId = payment.getCustomerDistrictId();
                        Integer warehouseId = payment.getCustomerWarehouseId();
                        return getCustomerId(lastName, districtId, warehouseId);
                    });
        case ORDER_STATUS:
            OrderStatusInput orderStatus = (OrderStatusInput) command.getRequest();
            return Optional.of(orderStatus)
                    .map(OrderStatusInput::getCustomerId)
                    .filter(Numbers::isNotNullOrZero)
                    .orElseGet(() -> {
                        String lastName = orderStatus.getCustomerLastName();
                        Integer districtId = orderStatus.getDistrictId();
                        Integer warehouseId = orderStatus.getWarehouseId();
                        return getCustomerId(lastName, districtId, warehouseId);
                    });
        case DELIVERY:
        case STOCK_LEVEL:
        default:
            throw new IllegalStateException("Stock level and delivery must be resolved at transaction level");
        }
    }

    private Integer getCustomerId(String customerLastName, Integer districtId, Integer warehouseId) {
        // clause 2.6.2.2 (dot 3, Case 2)
        Customer customer = customerRepository.find(customerLastName, districtId, warehouseId);
        if (customer == null) {
            return null;
        }
        return customer.getCustomerId();
    }

}
