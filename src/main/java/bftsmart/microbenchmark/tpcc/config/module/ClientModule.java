package bftsmart.microbenchmark.tpcc.config.module;

import com.google.inject.AbstractModule;

public final class ClientModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new JacksonModule());
        install(new MessageModule());
        install(new DataFileModule());
    }

}