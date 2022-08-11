package bftsmart.microbenchmark.tpcc.server.transaction;

import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.domain.TransactionType;
import bftsmart.microbenchmark.tpcc.exception.ConfigurationException;

@Singleton
public class TransactionFactory {

    @Inject
    private Set<Transaction> transactions;

    public Transaction getFactory(TransactionType transactionType) {
        return transactions.stream()
                .filter(transaction -> transaction.transactionType().equals(transactionType))
                .findFirst()
                .orElseThrow(() -> new ConfigurationException("There is no transaction for " + transactionType));
    }

    public Transaction getFactory(int transactionType) {
        return getFactory(TransactionType.fromValue(transactionType));
    }

}
