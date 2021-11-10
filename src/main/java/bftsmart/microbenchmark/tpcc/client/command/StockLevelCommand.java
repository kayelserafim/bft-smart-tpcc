package bftsmart.microbenchmark.tpcc.client.command;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;
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

        Map<String, Serializable> params = new HashMap<>();
        params.put("w_id", terminalData.getWarehouseId());
        params.put("d_id", terminalData.getDistrictId());
        params.put("threshold", threshold);

        return new TPCCCommand(commandType, params);
    }

}
