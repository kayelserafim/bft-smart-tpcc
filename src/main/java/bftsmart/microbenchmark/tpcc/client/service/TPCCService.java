package bftsmart.microbenchmark.tpcc.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.client.command.CommandFactory;
import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;
import bftsmart.tom.ServiceProxy;

@Singleton
public class TPCCService {

    public static final Logger LOGGER = LoggerFactory.getLogger(TPCCService.class);

    @Inject
    private BFTServiceProxy bftServiceProxy;
    @Inject
    private CommandFactory commandFactory;
    @Inject
    private WorkloadConfig config;

    public TPCCCommand process(TPCCTerminalData terminalData, TPCCRandom random) {
        TPCCCommandType commandType = terminalData.getTPCCCommandType(random.nextInt(1, 100));
        TPCCCommand command = commandFactory.getFactory(commandType).createCommand(terminalData, random);
        ServiceProxy serviceProxy = bftServiceProxy.getInstance(terminalData.getTerminalId());

        try {
            byte[] response;
            if (config.getReadTransactions().contains(command.getCommandType())) {
                response = serviceProxy.invokeUnordered(command.getBytes());
            } else {
                response = serviceProxy.invokeOrdered(command.getBytes());
            }
            return getResponse(command, response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return TPCCCommand.newErrorMessage(command, e.getMessage());
        }
    }

    private TPCCCommand getResponse(TPCCCommand message, byte[] response) {
        TPCCCommand command;
        if (response != null) {
            command = TPCCCommand.getObject(response);
        } else {
            String errorMessage = "Server replied null value for [%s]";
            command = TPCCCommand.newErrorMessage(message, errorMessage, message.toString());
        }
        return command;
    }

}
