package bftsmart.microbenchmark.tpcc.client.command;

import java.util.UUID;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionType;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.request.DeliveryRequest;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class DeliveryCommand implements TPCCCommand {

    @Override
    public TransactionType transactionType() {
        return TransactionType.DELIVERY;
    }

    @Override
    public TransactionRequest createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        final int orderCarrierId = random.nextInt(1, 10);

        return new DeliveryRequest().withCommandId(UUID.randomUUID().toString())
                .withTransactionType(transactionType().getClassId())
                .withWarehouseId(terminalData.getWarehouseId())
                .withOrderCarrierId(orderCarrierId);
    }

}
