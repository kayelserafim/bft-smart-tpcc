package bftsmart.microbenchmark.tpcc.server;

import static parallelism.late.COSType.lockFreeGraph;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import bftsmart.microbenchmark.tpcc.server.conflict.TPCCConflictDefinition;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionFactory;
import parallelism.late.CBASEServiceReplica;
import parallelism.late.ConflictDefinition;

public class TPCCParallelServer extends TPCCServer {

    private final int numWorkers = Runtime.getRuntime().availableProcessors();
    private final ConflictDefinition conflictDefinition = new TPCCConflictDefinition();

    @Inject
    TPCCParallelServer(TransactionFactory transactionFactory, @Named("replicaId") String replicaId) {
        super(transactionFactory);
        new CBASEServiceReplica(Integer.parseInt(replicaId), this, null, numWorkers, conflictDefinition, lockFreeGraph);
    }

}