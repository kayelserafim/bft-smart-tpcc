package bftsmart.microbenchmark.tpcc.config.module;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.google.common.base.Stopwatch;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;
import bftsmart.microbenchmark.tpcc.io.TPCCData;
import io.vavr.control.Try;

public class DataFileModule extends AbstractModule {

    public static final Logger LOGGER = LoggerFactory.getLogger(DataFileModule.class);

    @Provides
    @Singleton
    public WorkloadConfig workloadConfig(JavaPropsMapper propsMapper) {
        try {
            File file = TPCCConfig.getWorkloadConfigFile();
            return propsMapper.readValue(file, WorkloadConfig.class);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
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
