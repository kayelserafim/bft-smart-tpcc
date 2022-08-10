package bftsmart.microbenchmark.tpcc.server.transaction;

import bftsmart.microbenchmark.tpcc.domain.Command;
import bftsmart.microbenchmark.tpcc.domain.TransactionType;

public interface Transaction {

    TransactionType transactionType();

    Command process(final Command aRequest);

}
