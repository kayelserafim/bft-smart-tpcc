package bftsmart.microbenchmark.tpcc.client.command;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TransactionType;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public interface Command {

    TransactionType transactionType();

    TPCCCommand createCommand(TPCCTerminalData terminalData, TPCCRandom random);

}
