package bftsmart.microbenchmark.tpcc.client.command;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class OrderStatusCommand implements Command {

    private final TPCCCommandType commandType;

    public OrderStatusCommand() {
        this.commandType = TPCCCommandType.ORDER_STATUS;
    }

    @Override
    public TPCCCommandType commandType() {
        return commandType;
    }

    @Override
    public TPCCCommand createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        int y = random.nextInt(1, 100);
        int customerId = -1;
        int districtId = random.nextInt(1, TPCCConfig.DIST_PER_WHSE);
        boolean customerByName;
        String customerLastName = null;
        if (y <= 60) {
            // 60% lookups by last name
            customerByName = true;
            customerLastName = random.getCLast();
        } else {
            // 40% lookups by customer ID
            customerByName = false;
            customerId = random.getCustomerID();
        }

        Map<String, Serializable> params = new HashMap<>();
        params.put("w_id", terminalData.getWarehouseId());
        params.put("d_id", districtId);
        params.put("c_id", customerId);
        params.put("c_by_name", customerByName);
        params.put("c_name", customerLastName);

        return new TPCCCommand(commandType, params);
    }

}
