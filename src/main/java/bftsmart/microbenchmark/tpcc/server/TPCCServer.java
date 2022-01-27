package bftsmart.microbenchmark.tpcc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionFactory;
import bftsmart.tom.MessageContext;
import bftsmart.tom.ServiceReplica;
import bftsmart.tom.server.SingleExecutable;

public class TPCCServer implements SingleExecutable {

    public static final Logger LOGGER = LoggerFactory.getLogger(TPCCServer.class);

    private final WorkloadConfig workloadConfig;
    private final TransactionFactory transactionFactory;

    @Inject
    public TPCCServer(TransactionFactory transactionFactory, WorkloadConfig workloadConfig,
            @Named("replicaId") String replicaId) {
        this.transactionFactory = transactionFactory;
        this.workloadConfig = workloadConfig;
        new ServiceReplica(Integer.valueOf(replicaId), this, null);
    }

    @Override
    public byte[] executeOrdered(byte[] theCommand, MessageContext theContext) {
        TPCCCommand aRequest = TPCCCommand.getObject(theCommand);
        TPCCCommand reply = TPCCCommand.from(aRequest);
        if (aRequest == null) {
            return reply.getBytes();
        }
        LOGGER.debug("[INFO] Processing an ordered request of type [{}]", reply.getCommandType());

        TPCCCommandType commandType = aRequest.getCommandType();
        reply = transactionFactory.getFactory(commandType).process(aRequest);
        if (!workloadConfig.getWriteTransactions().contains(commandType)) {
            String description = String.format("The transaction [%s] should be unordered", commandType);
            reply = TPCCCommand.from(reply, description);
        }

        LOGGER.debug("[INFO] Sending reply");
        return reply.getBytes();
    }

    @Override
    public byte[] executeUnordered(byte[] theCommand, MessageContext theContext) {
        TPCCCommand aRequest = TPCCCommand.getObject(theCommand);
        TPCCCommand reply = TPCCCommand.from(aRequest);
        if (aRequest == null) {
            return reply.getBytes();
        }
        LOGGER.debug("[INFO] Processing an unordered request");

        TPCCCommandType commandType = aRequest.getCommandType();
        reply = transactionFactory.getFactory(commandType).process(aRequest);
        if (!workloadConfig.getReadTransactions().contains(commandType)) {
            String description = String.format("The transaction [%s] should be ordered", commandType);
            reply = TPCCCommand.from(reply, description);
        }

        LOGGER.debug("[INFO] Sending reply");
        return reply.getBytes();
    }

}