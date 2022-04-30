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

import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;
import bftsmart.microbenchmark.tpcc.io.TPCCData;
import io.vavr.control.Try;

public class DataModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataModule.class);

    @Override
    protected void configure() {
        Properties properties = new Properties(System.getProperties());
        try (InputStream inStream = new FileInputStream(TPCCConfig.getWorkloadConfigFile())) {
            properties.load(inStream);
        } catch (IOException e) {
            LOGGER.error("Could not load WorkloadConfig.", e);
        }
        Names.bindProperties(binder(), properties);
    }

    @Provides
    @Singleton
    public TPCCData tpccData(ObjectMapper objectMapper, WorkloadConfig workloadConfig) {
        File file = TPCCConfig.getTPCCDataFile(workloadConfig.getFileName());
        LOGGER.info("Reading TPCCData from Json file {}", file.getName());

        Stopwatch stopwatch = Stopwatch.createStarted();
        TPCCData tpccData = Try.of(() -> objectMapper.readValue(file, TPCCData.class))
                .onFailure(e -> LOGGER.error("Error reading file [{}].", file.getName(), e))
                .getOrElse(new TPCCData());

        stopwatch.stop();
        LOGGER.info("Data read in {} seconds.", stopwatch.elapsed().getSeconds());
        return tpccData;
    }

}
