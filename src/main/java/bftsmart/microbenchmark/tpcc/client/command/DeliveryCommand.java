package bftsmart.microbenchmark.tpcc.client.command;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.input.DeliveryInput;
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

        DeliveryInput input =
                new DeliveryInput().withWarehouseId(terminalData.getWarehouseId()).withOrderCarrierId(orderCarrierId);

        return new TPCCCommand(commandType, input);
    }

}
