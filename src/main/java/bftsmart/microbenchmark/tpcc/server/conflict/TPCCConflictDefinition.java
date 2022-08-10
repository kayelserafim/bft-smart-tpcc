package bftsmart.microbenchmark.tpcc.server.conflict;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.domain.Command;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.input.NewOrderInput;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.input.OrderStatusInput;
import bftsmart.microbenchmark.tpcc.server.transaction.payment.input.PaymentInput;
import bftsmart.microbenchmark.tpcc.util.KryoHelper;
import bftsmart.microbenchmark.tpcc.util.Numbers;
import bftsmart.util.MultiOperationRequest;
import parallelism.MessageContextPair;
import parallelism.late.ConflictDefinition;

@Singleton
public class TPCCConflictDefinition extends ConflictDefinition {

    private Map<String, Boolean> conflictMap = new ConcurrentHashMap<>();

    @Override
    public boolean isDependent(MessageContextPair arg0, MessageContextPair arg1) {
        final Command command1 = Command.deserialize(new MultiOperationRequest(arg0.request.getContent()));
        final Command command2 = Command.deserialize(new MultiOperationRequest(arg1.request.getContent()));

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
        
        // Não tem conflito entre last name diferentes
        // Tem conflito entre last name e customer id
        
        final int customer1 = getCustomerId(command1);
        final int customer2 = getCustomerId(command2);

        final boolean isDependent = customer1 == 0 || customer2 == 0 || customer1 == customer2;
        conflictMap.put(command1.getCommandId(), isDependent);
        conflictMap.put(command2.getCommandId(), isDependent);

        return isDependent;
    }

    public boolean isDependent(String commandId) {
        return conflictMap.getOrDefault(commandId, Boolean.FALSE);
    }

    private int getCustomerId(Command command) {
        final int transactionType = command.getTransactionType();

        switch (transactionType) {
        case 1:
            NewOrderInput newOrder = (NewOrderInput) KryoHelper.getInstance().fromBytes(command.getRequest());
            return newOrder.getCustomerId();
        case 2:
            PaymentInput payment = (PaymentInput) KryoHelper.getInstance().fromBytes(command.getRequest());
            return Optional.of(payment).map(PaymentInput::getCustomerId).filter(Numbers::isGreaterThanZero).orElse(0);
        case 3:
            OrderStatusInput orderStatus = (OrderStatusInput) KryoHelper.getInstance().fromBytes(command.getRequest());
            return Optional.of(orderStatus)
                    .map(OrderStatusInput::getCustomerId)
                    .filter(Numbers::isGreaterThanZero)
                    .orElse(0);
        case 4:
        case 5:
        default:
            throw new IllegalStateException("Stock level and delivery must be resolved at transaction level");
        }
    }

}
