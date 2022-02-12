package bftsmart.microbenchmark.tpcc.client.command;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.exception.ConfigurationException;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;

@Singleton
public class CommandFactory {

    public static final Logger LOGGER = LoggerFactory.getLogger(CommandFactory.class);

    @Inject
    private Set<Command> commands;

    public Command getFactory(TPCCCommandType commandType) {
        LOGGER.debug("Starting txn: {}, thread name: {}", commandType, Thread.currentThread().getName());
        return commands.stream()
                .filter(message -> message.commandType().equals(commandType))
                .findFirst()
                .orElseThrow(() -> new ConfigurationException("There is no command for " + commandType));
    }

}
