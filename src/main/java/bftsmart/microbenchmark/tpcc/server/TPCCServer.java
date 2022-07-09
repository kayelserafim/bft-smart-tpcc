package bftsmart.microbenchmark.tpcc.server;

import static parallelism.late.COSType.lockFreeGraph;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.config.BFTParams;
import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.server.conflict.TPCCConflictDefinition;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionFactory;
import bftsmart.tom.MessageContext;
import bftsmart.tom.server.SingleExecutable;
import parallelism.SequentialServiceReplica;
import parallelism.late.CBASEServiceReplica;

public class TPCCServer implements SingleExecutable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TPCCServer.class);

    private final TransactionFactory transactionFactory;
    private final TPCCConflictDefinition conflictDefinition;

    @Inject
    TPCCServer(TransactionFactory transactionFactory, TPCCConflictDefinition conflictDefinition,
            WorkloadConfig workloadConfig, BFTParams bftParams) {
        this.transactionFactory = transactionFactory;
        this.conflictDefinition = conflictDefinition;
        Integer replicaId = bftParams.getId();
        Integer numOfThreads = bftParams.getNumOfThreads();
        if (BooleanUtils.isTrue(bftParams.getParallel()) && numOfThreads > 1) {
            LOGGER.info("Starting CBASEServiceReplica.");
            new CBASEServiceReplica(replicaId, this, null, numOfThreads, conflictDefinition, lockFreeGraph);
        } else {
            LOGGER.info("Starting SequentialServiceReplica.");
            new SequentialServiceReplica(replicaId, this, null);
        }
    }

    @Override
    public byte[] executeOrdered(byte[] theCommand, MessageContext theContext) {
        TPCCCommand aRequest = TPCCCommand.deserialize(theCommand);
        LOGGER.debug("Processing an ordered request of type [{}]", aRequest.getTransactionType());

        byte[] reply = execute(aRequest);

        LOGGER.debug("Ordered reply received");
        return reply;
    }

    @Override
    public byte[] executeUnordered(byte[] theCommand, MessageContext theContext) {
        TPCCCommand aRequest = TPCCCommand.deserialize(theCommand);
        LOGGER.debug("Processing an unordered request of type [{}]", aRequest.getTransactionType());

        byte[] reply = execute(aRequest);

        LOGGER.debug("Unordered reply received");
        return reply;
    }

    private byte[] execute(TPCCCommand aRequest) {
        TPCCCommand reply = transactionFactory.getFactory(aRequest.getTransactionType()).process(aRequest);
        return TPCCCommand.from(reply)
                .conflict(conflictDefinition.isDependent(reply.getCommandId()))
                .build()
                .serialize();
    }

}
