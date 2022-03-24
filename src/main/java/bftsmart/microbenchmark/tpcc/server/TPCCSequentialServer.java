package bftsmart.microbenchmark.tpcc.server;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import bftsmart.microbenchmark.tpcc.server.transaction.TransactionFactory;
import bftsmart.tom.ServiceReplica;

public class TPCCSequentialServer extends TPCCServer {

    @Inject
    TPCCSequentialServer(TransactionFactory transactionFactory, @Named("replicaId") String replicaId) {
        super(transactionFactory);
        new ServiceReplica(Integer.parseInt(replicaId), this, null);
    }

}