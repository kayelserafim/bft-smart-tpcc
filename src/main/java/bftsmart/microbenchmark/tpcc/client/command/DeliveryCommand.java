package bftsmart.microbenchmark.tpcc.client.command;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class DeliveryCommand implements Command {

    private final TPCCCommandType commandType;

    public DeliveryCommand() {
        this.commandType = TPCCCommandType.DELIVERY;
    }

    @Override
    public TPCCCommandType commandType() {
        return commandType;
    }

    @Override
    public TPCCCommand createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        int orderCarrierId = random.nextInt(1, 10);

        Map<String, Serializable> params = new HashMap<>();
        params.put("w_id", terminalData.getWarehouseId());
        params.put("o_carrier_id", orderCarrierId);

        return new TPCCCommand(commandType, params);
    }

}
