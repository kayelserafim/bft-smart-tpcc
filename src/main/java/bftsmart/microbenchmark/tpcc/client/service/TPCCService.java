package bftsmart.microbenchmark.tpcc.client.service;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.client.command.CommandFactory;
import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TransactionType;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;
import bftsmart.tom.ParallelServiceProxy;
import parallelism.ParallelMapping;

@Singleton
public class TPCCService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TPCCService.class);

    @Inject
    private BFTServiceProxy bftServiceProxy;
    @Inject
    private CommandFactory commandFactory;

    public TPCCCommand process(TPCCTerminalData terminalData, TPCCRandom random) {
        TransactionType transactionType = terminalData.getTransactionType(random.nextInt(1, 100));
        TPCCCommand command = commandFactory.getFactory(transactionType).createCommand(terminalData, random);
        ParallelServiceProxy serviceProxy = bftServiceProxy.getInstance(terminalData.getTerminalId());

        try {
            byte[] response;
            if (BooleanUtils.isTrue(terminalData.getParallelExecution())) {
                response = serviceProxy.invokeParallel(command.getBytes(), ParallelMapping.SYNC_ALL);
            } else {
                response = serviceProxy.invokeOrdered(command.getBytes());
            }
            return getResponse(command, response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return TPCCCommand.newErrorMessage(command, e.getMessage());
        }
    }

    private TPCCCommand getResponse(TPCCCommand tpccCommand, byte[] response) {
        TPCCCommand command;
        if (response != null) {
            command = TPCCCommand.getObject(response);
        } else {
            String errorMessage = String.format("Server replied null value for [%s]", tpccCommand);
            command = TPCCCommand.newErrorMessage(tpccCommand, errorMessage);
        }
        return command;
    }

}
