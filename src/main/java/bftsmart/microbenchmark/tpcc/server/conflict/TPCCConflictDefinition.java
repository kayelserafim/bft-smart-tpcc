package bftsmart.microbenchmark.tpcc.server.conflict;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.domain.CommandRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.input.NewOrderInput;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.input.OrderStatusInput;
import bftsmart.microbenchmark.tpcc.server.transaction.payment.input.PaymentInput;
import bftsmart.util.MultiOperationRequest;
import parallelism.MessageContextPair;
import parallelism.late.ConflictDefinition;

@Singleton
public class TPCCConflictDefinition extends ConflictDefinition {

    private Map<String, Boolean> conflictMap = new ConcurrentHashMap<>();

    @Override
    public boolean isDependent(MessageContextPair arg0, MessageContextPair arg1) {
        final CommandRequest req1 = CommandRequest.deserialize(new MultiOperationRequest(arg0.request.getContent()));
        final CommandRequest req2 = CommandRequest.deserialize(new MultiOperationRequest(arg1.request.getContent()));

        if (TransactionConflicts.hasNotConflict(req1.getTransactionType(), req2.getTransactionType())) {
            conflictMap.put(req1.getCommandId(), Boolean.FALSE);
            conflictMap.put(req2.getCommandId(), Boolean.FALSE);
            return false;
        }
        if (TransactionConflicts.isPessimistic(req1.getTransactionType(), req2.getTransactionType())) {
            conflictMap.put(req1.getCommandId(), Boolean.TRUE);
            conflictMap.put(req2.getCommandId(), Boolean.TRUE);
            return true;
        }

        final Customer customer1 = getCustomerId(req1);
        final Customer customer2 = getCustomerId(req2);

        boolean isDependent;
        // customer1 não possui ID
        if (customer1.getId() == -1) {
            // customer2 não possui ID
            if (customer2.getId() == -1) {
                // Se os customers possuírem o mesmo sobrenome, assumimos o pior cenário (conflito)
                isDependent = Objects.equals(customer1.getLastName(), customer2.getLastName());
            } else {
                // Se o customer1 não possuir ID e o customer2 possuir ID, assumimos o pior cenário
                isDependent = true;
            }
        } else {
            // customer1 possui ID
            // customer2 não possui ID
            if (customer2.getId() == -1) {
                // Se o customer1 possuir ID e o customer2 não possui ID, assumimos o pior cenário
                isDependent = true;
            } else {
                // Se os customers possuírem o mesmo nome, assumimos o pior cenário (conflito)
                isDependent = customer1.getId() == customer2.getId();
            }
        }

        conflictMap.put(req1.getCommandId(), isDependent);
        conflictMap.put(req2.getCommandId(), isDependent);

        return isDependent;
    }

    public boolean isDependent(String commandId) {
        return conflictMap.getOrDefault(commandId, Boolean.FALSE);
    }

    private Customer getCustomerId(CommandRequest command) {
        final int transactionType = command.getTransactionType();

        switch (transactionType) {
        case 1:
            NewOrderInput newOrder = (NewOrderInput) command;
            return new Customer(newOrder.getCustomerId(), null);
        case 2:
            PaymentInput payment = (PaymentInput) command;
            return new Customer(payment.getCustomerId(), payment.getCustomerLastName());
        case 3:
            OrderStatusInput orderStatus = (OrderStatusInput) command;
            return new Customer(orderStatus.getCustomerId(), orderStatus.getCustomerLastName());
        case 4:
        case 5:
        default:
            throw new IllegalStateException("Stock level and delivery must be resolved at transaction level");
        }
    }

    private static class Customer {
        private final int id;
        private final String lastName;

        public Customer(int id, String lastName) {
            super();
            this.id = id;
            this.lastName = lastName;
        }

        public int getId() {
            return id;
        }

        public String getLastName() {
            return lastName;
        }

    }

}
