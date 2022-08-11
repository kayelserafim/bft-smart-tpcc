package bftsmart.microbenchmark.tpcc.client.command;

import java.util.UUID;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionType;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.request.StockLevelRequest;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class StockLevelCommand implements TPCCCommand {

    @Override
    public TransactionType transactionType() {
        return TransactionType.STOCK_LEVEL;
    }

    @Override
    public TransactionRequest createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        final int threshold = random.nextInt(10, 20);

        return new StockLevelRequest().withCommandId(UUID.randomUUID().toString())
                .withTransactionType(transactionType().getClassId())
                .withWarehouseId(terminalData.getWarehouseId())
                .withDistrictId(terminalData.getDistrictId())
                .withThreshold(threshold);
    }

}
