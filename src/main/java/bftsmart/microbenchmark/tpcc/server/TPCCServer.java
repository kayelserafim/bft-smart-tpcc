package bftsmart.microbenchmark.tpcc.server;

import static java.lang.Runtime.getRuntime;
import static parallelism.late.COSType.lockFreeGraph;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionFactory;
import bftsmart.tom.MessageContext;
import bftsmart.tom.ServiceReplica;
import bftsmart.tom.server.SingleExecutable;
import parallelism.late.CBASEServiceReplica;
import parallelism.late.ConflictDefinition;

public class TPCCServer implements SingleExecutable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TPCCServer.class);

    private final TransactionFactory transactionFactory;

    @Inject
    public TPCCServer(TransactionFactory transactionFactory, ConflictDefinition definition,
            WorkloadConfig workloadConfig, @Named("replicaId") Integer replicaId) {
        this.transactionFactory = transactionFactory;
        if (BooleanUtils.isTrue(workloadConfig.getParallelExecution())) {
            LOGGER.info("Starting CBASEServiceReplica. Parallel execution? {}", workloadConfig.getParallelExecution());
            int availableProcessors = getRuntime().availableProcessors();
            new CBASEServiceReplica(replicaId, this, null, availableProcessors, definition, lockFreeGraph);
        } else {
            LOGGER.info("Starting ServiceReplica. Parallel execution? {}", workloadConfig.getParallelExecution());
            new ServiceReplica(replicaId, this, null);
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
        return transactionFactory.getFactory(aRequest.getTransactionType()).process(aRequest).serialize();
    }

}
