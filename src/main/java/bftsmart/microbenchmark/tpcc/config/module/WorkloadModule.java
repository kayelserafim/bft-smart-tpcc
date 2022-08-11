package bftsmart.microbenchmark.tpcc.config.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import bftsmart.microbenchmark.tpcc.io.Workload;
import bftsmart.microbenchmark.tpcc.io.WorkloadFile;
import io.vavr.control.Try;

public class WorkloadModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkloadModule.class);

    @Override
    protected void configure() {
        Properties properties = new Properties(System.getProperties());
        try (InputStream inStream = new FileInputStream(WorkloadFile.defaultWorkload())) {
            properties.load(inStream);
        } catch (IOException e) {
            LOGGER.error("Could not load WorkloadConfig.", e);
        }
        Names.bindProperties(binder(), properties);
    }

    @Provides
    @Singleton
    public Workload workload(WorkloadFile workloadFile, ObjectMapper objectMapper) {
        File file = workloadFile.defaultWorkloadData();
        LOGGER.info("Reading TPCCData from Json file {}", file.getName());

        Stopwatch stopwatch = Stopwatch.createStarted();
        Workload workload = Try.of(() -> objectMapper.readValue(file, Workload.class))
                .onFailure(e -> LOGGER.error("Error reading file [{}].", file.getName(), e))
                .getOrElse(new Workload());

        stopwatch.stop();
        LOGGER.info("Data read in {} seconds.", stopwatch.elapsed().getSeconds());
        return workload;
    }

}
