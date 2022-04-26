package bftsmart.microbenchmark.tpcc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import bftsmart.microbenchmark.tpcc.config.module.ServerModule;
import bftsmart.microbenchmark.tpcc.server.TPCCParallelServer;

public class TPCCParallelServerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(TPCCParallelServerApplication.class);

    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            LOGGER.info("Usage: java ... TPCCParallelServer <replica id>");
            return;
        }
        Injector injector = Guice.createInjector(new ServerModule(Integer.valueOf(args[0])));
        injector.getInstance(TPCCParallelServer.class);
    }

}
