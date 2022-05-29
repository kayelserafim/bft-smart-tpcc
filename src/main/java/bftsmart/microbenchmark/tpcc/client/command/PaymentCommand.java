package bftsmart.microbenchmark.tpcc.client.command;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TransactionType;
import bftsmart.microbenchmark.tpcc.server.transaction.payment.input.PaymentInput;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class PaymentCommand implements Command {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    @Override
    public TransactionType transactionType() {
        return TransactionType.PAYMENT;
    }

    @Override
    public TPCCCommand createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        final int x = random.nextInt(1, 100);
        final int districtId = random.nextInt(1, TPCCConfig.DIST_PER_WHSE);
        int customerDistrictID;
        int customerWarehouseID;
        if (x <= 85) {
            customerWarehouseID = terminalData.getWarehouseId();
            customerDistrictID = districtId;
        } else {
            customerDistrictID = random.nextInt(1, TPCCConfig.DIST_PER_WHSE);
            do {
                customerWarehouseID = random.nextInt(1, terminalData.getWarehouseCount());
            } while (customerWarehouseID == terminalData.getWarehouseId() && terminalData.getWarehouseCount() > 1);
        }

        final int y = random.nextInt(1, 100);
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

        final BigDecimal paymentAmount =
                random.nextBigDecimal(100, 500000).divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);

        final PaymentInput input = new PaymentInput().withWarehouseId(terminalData.getWarehouseId())
                .withDistrictId(districtId)
                .withCustomerWarehouseId(customerWarehouseID)
                .withCustomerDistrictId(customerDistrictID)
                .withCustomerId(customerId)
                .withCustomerLastName(customerLastName)
                .withCustomerByName(customerByName)
                .withPaymentAmount(paymentAmount);

        return TPCCCommand.builder()
                .commandId(UUID.randomUUID().toString())
                .transactionType(transactionType())
                .request(input)
                .build();
    }

}
