package bftsmart.microbenchmark.tpcc.client.command;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TransactionType;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.input.StockLevelInput;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class StockLevelCommand implements Command {

    @Override
    public TransactionType transactionType() {
        return TransactionType.STOCK_LEVEL;
    }

    @Override
    public TPCCCommand createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        final int threshold = random.nextInt(10, 20);

        final StockLevelInput input = new StockLevelInput().withWarehouseId(terminalData.getWarehouseId())
                .withDistrictId(terminalData.getDistrictId())
                .withThreshold(threshold);

        return TPCCCommand.builder().transactionType(transactionType()).request(input).build();
    }

}
