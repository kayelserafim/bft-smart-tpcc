package bftsmart.microbenchmark.tpcc.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public final class ClientModule extends AbstractModule {

    private final Integer clientId;

    public ClientModule(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    protected void configure() {
        bindConstant().annotatedWith(Names.named("clientId")).to(clientId);
        install(new JacksonModule());
        install(new CommandModule());
        install(new DataFileModule());
    }

}