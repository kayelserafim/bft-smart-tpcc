package bftsmart.microbenchmark.tpcc.client.command;

import java.util.UUID;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.domain.Command;
import bftsmart.microbenchmark.tpcc.domain.TransactionType;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.input.StockLevelInput;
import bftsmart.microbenchmark.tpcc.util.KryoHelper;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class StockLevelCommand implements TPCCCommand {

    @Override
    public TransactionType transactionType() {
        return TransactionType.STOCK_LEVEL;
    }

    @Override
    public Command createCommand(TPCCTerminalData terminalData, TPCCRandom random) {
        final int threshold = random.nextInt(10, 20);

        final StockLevelInput input = new StockLevelInput().withWarehouseId(terminalData.getWarehouseId())
                .withDistrictId(terminalData.getDistrictId())
                .withThreshold(threshold);

        return new Command().withCommandId(UUID.randomUUID().toString())
                .withTransactionType(transactionType().getClassId())
                .withRequest(KryoHelper.getInstance().toBytes(input));
    }

}
