package bftsmart.microbenchmark.tpcc;

import com.google.inject.Guice;
import com.google.inject.Injector;

import bftsmart.microbenchmark.tpcc.server.TPCCServer;
import bftsmart.microbenchmark.tpcc.server.module.ServerModule;

public class TPCCServerApplication {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ServerModule());
        injector.getInstance(TPCCServer.class);
    }

}
