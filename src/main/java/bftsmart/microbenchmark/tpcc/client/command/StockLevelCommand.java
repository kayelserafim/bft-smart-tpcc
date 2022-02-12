package bftsmart.microbenchmark.tpcc.client.command;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.input.StockLevelInput;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class StockLevelCommand implements Command {

    private final TPCCCommandType commandType;

    public StockLevelCommand() {
        this.commandType = TPCCCommandType.STOCK_LEVEL;
    }

    @Override
    public TPCCCommandType commandType() {
        return commandType;
    }

    @Override
    public TPCCCommand createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        int threshold = random.nextInt(10, 20);

        StockLevelInput input = new StockLevelInput().withWarehouseId(terminalData.getWarehouseId())
                .withDistrictId(terminalData.getDistrictId())
                .withThreshold(threshold);

        return new TPCCCommand(commandType, input);
    }

}
