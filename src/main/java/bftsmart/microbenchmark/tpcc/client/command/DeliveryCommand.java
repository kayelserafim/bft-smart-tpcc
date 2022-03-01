package bftsmart.microbenchmark.tpcc.client.command;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TransactionType;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.input.DeliveryInput;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class DeliveryCommand implements Command {

    @Override
    public TransactionType transactionType() {
        return TransactionType.DELIVERY;
    }

    @Override
    public TPCCCommand createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        final int orderCarrierId = random.nextInt(1, 10);

        final DeliveryInput input =
                new DeliveryInput().withWarehouseId(terminalData.getWarehouseId()).withOrderCarrierId(orderCarrierId);

        return new TPCCCommand(transactionType(), input);
    }

}
