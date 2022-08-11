package bftsmart.microbenchmark.tpcc.server.module;

import com.google.inject.AbstractModule;

import bftsmart.microbenchmark.tpcc.config.module.JacksonModule;
import bftsmart.microbenchmark.tpcc.server.conflict.TPCCConflictDefinition;
import bftsmart.microbenchmark.tpcc.server.hazelcast.module.TransactionModule;
import bftsmart.microbenchmark.tpcc.workload.module.WorkloadModule;
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