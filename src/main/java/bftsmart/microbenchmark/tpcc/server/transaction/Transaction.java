package bftsmart.microbenchmark.tpcc.server.transaction;

import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TransactionType;

public interface Transaction {

    TransactionType transactionType();

    TPCCCommand process(final TPCCCommand aRequest);

}
