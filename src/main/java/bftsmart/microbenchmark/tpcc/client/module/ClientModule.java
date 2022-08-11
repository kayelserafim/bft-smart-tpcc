package bftsmart.microbenchmark.tpcc.client.module;

import com.google.inject.AbstractModule;

import bftsmart.microbenchmark.tpcc.config.module.JacksonModule;
import bftsmart.microbenchmark.tpcc.spreadsheet.module.SpreadsheetModule;
import bftsmart.microbenchmark.tpcc.workload.module.WorkloadModule;

public final class ClientModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new JacksonModule());
        install(new CommandModule());
        install(new WorkloadModule());
        install(new SpreadsheetModule());
    }

}