package bftsmart.microbenchmark.tpcc.config;

import java.io.File;
import java.nio.file.FileSystems;

import org.apache.commons.lang3.StringUtils;

public class TPCCConfig {

    public static final String CONFIG_FOLDER = "config";
    public static final String DATA_FOLDER = "data";

    public static final String JSON_EXTENSION = ".json";

    /**
     * <p>
     * For each WAREHOUSE there are 10 DISTRICT rows.
     * </p>
     * <code>tpc-c std = 10</code>
     */
    public static final int DIST_PER_WHSE = 10;

    public static final int LIMIT_ORDER = 2101;
    /**
     * <p>
     * Within each DISTRICT there are 3,000 CUSTOMERs.
     * </p>
     * <code>tpc-c std = 3,000</code>
     */
    public static final int CUST_PER_DIST = 3000;
    public static final int NB_MAX_ITEM = 100_000;

    public static final int C_LAST = 255;
    public static final int C_ID = 1023;
    public static final int OL_I_ID = 8191;
    public static final int MAX_C_LAST = 999;
    public static final int NB_MAX_CUSTOMER = 3000;

    private TPCCConfig() {
        super();
    }

    public static File getWorkloadConfigFile() {
        return FileSystems.getDefault()
                .getPath(CONFIG_FOLDER + File.separator + WorkloadConfig.RESOURCE_PATH)
                .toFile();
    }

    public static File getTPCCDataFile(String fileName) {
        String pathName = DATA_FOLDER + File.separator + StringUtils.appendIfMissing(fileName, JSON_EXTENSION);
        File file = FileSystems.getDefault().getPath(pathName).toFile();

        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdir();
        }
        return file;
    }

}
