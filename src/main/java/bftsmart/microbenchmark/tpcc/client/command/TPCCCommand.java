package bftsmart.microbenchmark.tpcc.client.command;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.domain.Command;
import bftsmart.microbenchmark.tpcc.domain.TransactionType;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public interface TPCCCommand {

    TransactionType transactionType();

    Command createCommand(TPCCTerminalData terminalData, TPCCRandom random);

}
