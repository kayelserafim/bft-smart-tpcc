package bftsmart.microbenchmark.tpcc.client.command;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class NewOrderCommand implements Command {

    private final TPCCCommandType commandType;

    public NewOrderCommand() {
        this.commandType = TPCCCommandType.NEW_ORDER;
    }

    @Override
    public TPCCCommandType commandType() {
        return commandType;
    }

    @Override
    public TPCCCommand createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        int customerID = random.getCustomerID();
        int districtId = random.nextInt(1, TPCCConfig.DIST_PER_WHSE);

        // o_ol_cnt
        int numItems = random.nextInt(5, 15);
        int[] itemIDs = new int[numItems];
        int[] supplierWarehouses = new int[numItems];
        int[] orderQuantities = new int[numItems];
        Set<Integer> itemObjIds = new HashSet<>();
        // see clause 2.4.2.2 (dot 6)
        int allLocal = 1;
        // clause 2.4.1.5
        for (int i = 0; i < numItems; i++) {
            itemIDs[i] = random.getItemID();
            if (random.nextInt(1, 100) > 1) {
                supplierWarehouses[i] = terminalData.getWarehouseId();
            } else {
                // see clause 2.4.1.5 (dot 2)
                do {
                    supplierWarehouses[i] = random.nextInt(1, terminalData.getWarehouseCount());
                } while (supplierWarehouses[i] == terminalData.getWarehouseId()
                        && terminalData.getWarehouseCount() > 1);
                // see clause 2.4.2.2 (dot 6)
                allLocal = 0;
            }
            orderQuantities[i] = random.nextInt(1, 10);
        }

        // we need to cause 1% of the new orders to be rolled back.
        // clause 2.4.1.5 (dot 1)
        if (random.nextInt(1, 100) == 1) {
            itemIDs[numItems - 1] = -12345;
            itemObjIds.add(itemIDs[numItems - 1]);
        }

        Map<String, Serializable> params = new HashMap<>();
        params.put("w_id", terminalData.getWarehouseId());
        params.put("d_id", districtId);
        params.put("c_id", customerID);
        params.put("ol_o_cnt", numItems);
        params.put("o_all_local", allLocal);
        params.put("itemIds", itemIDs);
        params.put("supplierWarehouseIDs", supplierWarehouses);
        params.put("orderQuantities", orderQuantities);

        return new TPCCCommand(commandType, params);
    }

}