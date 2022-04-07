package bftsmart.microbenchmark.tpcc.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import bftsmart.microbenchmark.tpcc.server.conflict.TPCCConflictDefinition;
import parallelism.late.ConflictDefinition;

public final class ServerModule extends AbstractModule {

    private final String replicaId;

    public ServerModule(String replicaId) {
        this.replicaId = replicaId;
    }

    @Override
    protected void configure() {
        bindConstant().annotatedWith(Names.named("replicaId")).to(replicaId);
        bind(ConflictDefinition.class).to(TPCCConflictDefinition.class);
        install(new JacksonModule());
        install(new TransactionModule());
        install(new DataFileModule());
    }

}