package bftsmart.microbenchmark.tpcc.server;

import static java.lang.Runtime.getRuntime;
import static parallelism.late.COSType.lockFreeGraph;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import bftsmart.microbenchmark.tpcc.server.transaction.TransactionFactory;
import parallelism.late.CBASEServiceReplica;
import parallelism.late.ConflictDefinition;

public class TPCCParallelServer extends TPCCServer {

    @Inject
    TPCCParallelServer(TransactionFactory transactionFactory, ConflictDefinition definition,
            @Named("replicaId") Integer replicaId) {
        super(transactionFactory);
        new CBASEServiceReplica(replicaId, this, null, getRuntime().availableProcessors(), definition, lockFreeGraph);
    }

}