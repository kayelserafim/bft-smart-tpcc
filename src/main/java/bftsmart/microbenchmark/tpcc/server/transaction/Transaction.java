package bftsmart.microbenchmark.tpcc.server.transaction;

import bftsmart.microbenchmark.tpcc.domain.CommandRequest;
import bftsmart.microbenchmark.tpcc.domain.CommandResponse;
import bftsmart.microbenchmark.tpcc.domain.TransactionType;

public interface Transaction {

    TransactionType transactionType();

    CommandResponse process(final CommandRequest aRequest);

}
