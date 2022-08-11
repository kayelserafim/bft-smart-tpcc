package bftsmart.microbenchmark.tpcc;

import com.google.inject.Guice;
import com.google.inject.Injector;

import bftsmart.microbenchmark.tpcc.workload.WorkloadGenerator;
import bftsmart.microbenchmark.tpcc.workload.module.WorkloadGeneratorModule;

public class TPCCGeneratorApplication {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new WorkloadGeneratorModule());
        injector.getInstance(WorkloadGenerator.class).start();
    }

}
