package bftsmart.microbenchmark.tpcc.io;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;

@Singleton
public class TPCCJsonWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TPCCJsonWriter.class);

    private final ObjectMapper mapper;
    private final String fileName;

    @Inject
    TPCCJsonWriter(WorkloadConfig workloadConfig, ObjectMapper mapper) {
        this.mapper = mapper;
        this.fileName = workloadConfig.getFileName();
    }

    public void writeToJsonFile(TPCCData tpccData) {
        LOGGER.info("Saving to Json file {}", fileName);
        File file = TPCCConfig.getTPCCDataFile(fileName);
        try {
            mapper.writeValue(file, tpccData);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
        LOGGER.info("Json Data saved to file {}", fileName);
    }

}
