package bftsmart.microbenchmark.tpcc.client.service;

import static parallelism.ParallelMapping.SYNC_ALL;

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
import bftsmart.util.MultiOperationRequest;
import bftsmart.util.MultiOperationResponse;

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
            if (BooleanUtils.isTrue(terminalData.getParallelExecution())) {
                byte[] response = serviceProxy.invokeParallel(command.serialize(new MultiOperationRequest(1)), SYNC_ALL);
                return TPCCCommand.deserialize(new MultiOperationResponse(response));
            } else {
                byte[] response = serviceProxy.invokeOrdered(command.serialize());
                return TPCCCommand.deserialize(response);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return TPCCCommand.newErrorMessage(command, e.getMessage());
        }
    }

}
