package bftsmart.microbenchmark.tpcc.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public final class ServerModule extends AbstractModule {

    private final String replicaId;

    public ServerModule(String replicaId) {
        this.replicaId = replicaId;
    }

    @Override
    protected void configure() {
        bindConstant().annotatedWith(Names.named("replicaId")).to(replicaId);
        install(new JacksonModule());
        install(new TransactionModule());
        install(new DataFileModule());
    }

}