package bftsmart.microbenchmark.tpcc.server;

import static parallelism.late.COSType.lockFreeGraph;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import bftsmart.microbenchmark.tpcc.server.transaction.TransactionFactory;
import parallelism.late.CBASEServiceReplica;
import parallelism.late.ConflictDefinition;

public class TPCCParallelServer extends TPCCServer {

    private final int numWorkers = Runtime.getRuntime().availableProcessors();

    @Inject
    TPCCParallelServer(TransactionFactory transactionFactory, ConflictDefinition conflictDefinition,
            @Named("replicaId") String replicaId) {
        super(transactionFactory);
        new CBASEServiceReplica(Integer.parseInt(replicaId), this, null, numWorkers, conflictDefinition, lockFreeGraph);
    }

}