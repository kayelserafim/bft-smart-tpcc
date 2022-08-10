package bftsmart.microbenchmark.tpcc.config.module;

import com.google.inject.AbstractModule;

import bftsmart.microbenchmark.tpcc.server.conflict.TPCCConflictDefinition;
import parallelism.late.ConflictDefinition;

public final class ServerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ConflictDefinition.class).to(TPCCConflictDefinition.class);
        install(new JacksonModule());
        install(new TransactionModule());
        install(new WorkloadModule());
    }

}