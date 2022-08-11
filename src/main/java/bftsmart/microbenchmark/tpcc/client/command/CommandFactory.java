package bftsmart.microbenchmark.tpcc.client.command;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.domain.TransactionType;
import bftsmart.microbenchmark.tpcc.exception.ConfigurationException;

@Singleton
public class CommandFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandFactory.class);

    private final Set<TPCCCommand> commands;

    @Inject
    CommandFactory(Set<TPCCCommand> commands) {
        this.commands = commands;
    }

    public TPCCCommand getFactory(TransactionType transactionType) {
        LOGGER.debug("Starting txn: {}, thread name: {}", transactionType, Thread.currentThread().getName());
        return commands.stream()
                .filter(message -> message.transactionType() == transactionType)
                .findFirst()
                .orElseThrow(() -> new ConfigurationException("There is no command for " + transactionType));
    }

}
