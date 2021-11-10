package bftsmart.microbenchmark.tpcc.server.transaction;

import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.exception.ConfigurationException;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;

@Singleton
public class TransactionFactory {

    @Inject
    private Set<Transaction> transactions;

    public Transaction getFactory(TPCCCommandType commandType) {
        return transactions.stream()
                .filter(transaction -> transaction.commandType().equals(commandType))
                .findFirst()
                .orElseThrow(() -> new ConfigurationException("There is no transaction for " + commandType));
    }

}
