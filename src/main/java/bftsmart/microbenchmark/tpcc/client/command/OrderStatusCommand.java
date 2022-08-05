package bftsmart.microbenchmark.tpcc.client.command;

import java.util.UUID;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TransactionType;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.input.OrderStatusInput;
import bftsmart.microbenchmark.tpcc.util.KryoHelper;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class OrderStatusCommand implements Command {

    @Override
    public TransactionType transactionType() {
        return TransactionType.ORDER_STATUS;
    }

    @Override
    public TPCCCommand createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        final int y = random.nextInt(1, 100);
        final int districtId = random.nextInt(1, TPCCConfig.DIST_PER_WHSE);
        int customerId = -1;
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

        final OrderStatusInput input = new OrderStatusInput().withWarehouseId(terminalData.getWarehouseId())
                .withDistrictId(districtId)
                .withCustomerId(customerId)
                .withCustomerByName(customerByName)
                .withCustomerLastName(customerLastName);

        return new TPCCCommand().withCommandId(UUID.randomUUID().toString())
                .withTransactionType(transactionType().getClassId())
                .withRequest(KryoHelper.getInstance().toBytes(input));
    }

}
