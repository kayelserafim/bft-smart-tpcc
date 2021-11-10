package bftsmart.microbenchmark.tpcc.client.command;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class PaymentCommand implements Command {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    private final TPCCCommandType commandType;

    public PaymentCommand() {
        this.commandType = TPCCCommandType.PAYMENT;
    }

    @Override
    public TPCCCommandType commandType() {
        return commandType;
    }

    @Override
    public TPCCCommand createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        int x = random.nextInt(1, 100);
        int districtId = random.nextInt(1, TPCCConfig.DIST_PER_WHSE);
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

        int y = random.nextInt(1, 100);
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

        BigDecimal paymentAmount = random.nextBigDecimal(100, 500000).divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);

        Map<String, Serializable> params = new HashMap<>();
        params.put("w_id", terminalData.getWarehouseId());
        params.put("d_id", districtId);
        params.put("c_w_id", customerWarehouseID);
        params.put("c_d_id", customerDistrictID);
        params.put("c_id", customerId);
        params.put("c_name", customerLastName);
        params.put("c_by_name", customerByName);
        params.put("payment_amount", paymentAmount);

        return new TPCCCommand(commandType, params);
    }

}
