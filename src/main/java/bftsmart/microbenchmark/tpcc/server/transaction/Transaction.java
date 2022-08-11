package bftsmart.microbenchmark.tpcc.server.transaction;

public interface Transaction {

    TransactionType transactionType();

    TransactionResponse process(final TransactionRequest aRequest);

}
