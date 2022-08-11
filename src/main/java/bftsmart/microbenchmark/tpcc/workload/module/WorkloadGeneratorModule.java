package bftsmart.microbenchmark.tpcc.workload.module;

import com.google.inject.AbstractModule;

import bftsmart.microbenchmark.tpcc.config.module.JacksonModule;

public final class WorkloadGeneratorModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new JacksonModule());
        install(new WorkloadModule());
    }

}