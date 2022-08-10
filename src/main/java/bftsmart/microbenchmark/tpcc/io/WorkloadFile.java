package bftsmart.microbenchmark.tpcc.io;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.config.TPCCConfig;

@Singleton
public class WorkloadFile {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkloadFile.class);

    public static final String CONFIG_FOLDER = "config";
    public static final String DATA_FOLDER = "data";

    public static final String WORKLOAD_FILE = "workload.properties";

    public static final String JSON_EXTENSION = ".json";

    private final ObjectMapper mapper;
    private final String fileName;

    @Inject
    WorkloadFile(TPCCConfig tpccConfig, ObjectMapper mapper) {
        this.mapper = mapper;
        this.fileName = tpccConfig.getFileName();
    }

    public void writeWorkloadToFile(Workload workload) {
        LOGGER.info("Saving to Json file {}", fileName);
        File file = defaultWorkloadData();
        try {
            mapper.writeValue(file, workload);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
        LOGGER.info("Json Data saved to file {}", fileName);
    }

    public File defaultWorkloadData() {
        File file = getFile(DATA_FOLDER, fileName + JSON_EXTENSION);

        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdir();
        }
        return file;
    }

    public static File defaultWorkload() {
        return getFile(CONFIG_FOLDER, WORKLOAD_FILE);
    }

    public static File getFile(String resourceBasename, String fileName) {
        return FileSystems.getDefault().getPath(resourceBasename + File.separator + fileName).toFile();
    }

}
