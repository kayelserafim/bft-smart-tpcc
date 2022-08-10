package bftsmart.microbenchmark.tpcc.client.command;

import java.util.UUID;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.domain.CommandRequest;
import bftsmart.microbenchmark.tpcc.domain.TransactionType;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.input.StockLevelInput;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class StockLevelCommand implements TPCCCommand {

    @Override
    public TransactionType transactionType() {
        return TransactionType.STOCK_LEVEL;
    }

    @Override
    public CommandRequest createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        final int threshold = random.nextInt(10, 20);

        return new StockLevelInput().withCommandId(UUID.randomUUID().toString())
                .withTransactionType(transactionType().getClassId())
                .withWarehouseId(terminalData.getWarehouseId())
                .withDistrictId(terminalData.getDistrictId())
                .withThreshold(threshold);
    }

}
