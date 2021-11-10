package bftsmart.microbenchmark.tpcc.server.transaction;

import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;

public interface Transaction {

    TPCCCommandType commandType();

    TPCCCommand process(final TPCCCommand aRequest);

}
