package bftsmart.microbenchmark.tpcc;

import com.google.inject.Guice;
import com.google.inject.Injector;

import bftsmart.microbenchmark.tpcc.config.module.DataGeneratorModule;
import bftsmart.microbenchmark.tpcc.io.TPCCDataGenerator;

public class TPCCGeneratorApplication {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new DataGeneratorModule());
        injector.getInstance(TPCCDataGenerator.class).start();
    }

}
