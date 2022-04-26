package bftsmart.microbenchmark.tpcc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import bftsmart.microbenchmark.tpcc.client.TPCCClient;
import bftsmart.microbenchmark.tpcc.config.module.ClientModule;

public class TPCCClientApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(TPCCClientApplication.class);

    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            LOGGER.info("Usage: java ... TPCCClientApplication <client id>");
            return;
        }
        Injector injector = Guice.createInjector(new ClientModule(Integer.valueOf(args[0])));
        injector.getInstance(TPCCClient.class).start();
    }

}
