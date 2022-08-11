package bftsmart.microbenchmark.tpcc.client.command;

import java.util.UUID;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.config.TPCCConstants;
import bftsmart.microbenchmark.tpcc.domain.CommandRequest;
import bftsmart.microbenchmark.tpcc.domain.TransactionType;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.input.OrderStatusInput;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class OrderStatusCommand implements TPCCCommand {

    @Override
    public TransactionType transactionType() {
        return TransactionType.ORDER_STATUS;
    }

    @Override
    public CommandRequest createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        final int y = random.nextInt(1, 100);
        final int districtId = random.nextInt(1, TPCCConstants.DIST_PER_WHSE);
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

        return new OrderStatusInput().withCommandId(UUID.randomUUID().toString())
                .withTransactionType(transactionType().getClassId())
                .withWarehouseId(terminalData.getWarehouseId())
                .withDistrictId(districtId)
                .withCustomerId(customerId)
                .withCustomerByName(customerByName)
                .withCustomerLastName(customerLastName);
    }

}
