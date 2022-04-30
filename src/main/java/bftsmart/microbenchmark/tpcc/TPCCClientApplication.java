package bftsmart.microbenchmark.tpcc;

import com.google.inject.Guice;
import com.google.inject.Injector;

import bftsmart.microbenchmark.tpcc.client.TPCCClient;
import bftsmart.microbenchmark.tpcc.config.module.ClientModule;

public class TPCCClientApplication {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ClientModule());
        injector.getInstance(TPCCClient.class).start();
    }

}
