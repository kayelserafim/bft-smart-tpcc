package bftsmart.microbenchmark.tpcc.client.command;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionType;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public interface TPCCCommand {

    TransactionType transactionType();

    TransactionRequest createCommand(TPCCTerminalData terminalData, TPCCRandom random);

}
