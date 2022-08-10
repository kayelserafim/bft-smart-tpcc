package bftsmart.microbenchmark.tpcc.client.command;

import java.util.UUID;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.config.TPCCConstants;
import bftsmart.microbenchmark.tpcc.domain.CommandRequest;
import bftsmart.microbenchmark.tpcc.domain.TransactionType;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.input.NewOrderInput;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class NewOrderCommand implements TPCCCommand {

    @Override
    public TransactionType transactionType() {
        return TransactionType.NEW_ORDER;
    }

    @Override
    public CommandRequest createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        final int customerID = random.getCustomerID();
        final int districtId = random.nextInt(1, TPCCConstants.DIST_PER_WHSE);

        // o_ol_cnt
        final int numItems = random.nextInt(5, 15);
        final int[] itemIDs = new int[numItems];
        final int[] supplierWarehouses = new int[numItems];
        final int[] orderQuantities = new int[numItems];
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
        }

        return new NewOrderInput().withCommandId(UUID.randomUUID().toString())
                .withTransactionType(transactionType().getClassId())
                .withWarehouseId(terminalData.getWarehouseId())
                .withDistrictId(districtId)
                .withCustomerId(customerID)
                .withOrderLineCnt(numItems)
                .withOrderAllLocal(allLocal)
                .withItemIds(itemIDs)
                .withSupplierWarehouseIds(supplierWarehouses)
                .withOrderQuantities(orderQuantities);
    }

}
