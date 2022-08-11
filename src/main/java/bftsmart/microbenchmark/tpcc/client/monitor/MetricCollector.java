package bftsmart.microbenchmark.tpcc.client.monitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.config.BFTParams;
import bftsmart.microbenchmark.tpcc.spreadsheet.ResultSpreadsheet;
import bftsmart.microbenchmark.tpcc.spreadsheet.Spreadsheet;
import bftsmart.microbenchmark.tpcc.util.InetAddresses;

@Singleton
public class MetricCollector {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricCollector.class);

    private final Set<ResultSpreadsheet> spreadsheets;
    private final BFTParams bftParams;

    @Inject
    public MetricCollector(final Set<ResultSpreadsheet> spreadsheets, final BFTParams bftParams) {
        this.spreadsheets = spreadsheets;
        this.bftParams = bftParams;
    }

    public void writeResults(List<RawResult> results) {
        LOGGER.info("Saving all metrics files");

        Stopwatch stopwatch = Stopwatch.createStarted();
        File file = createResultFile(bftParams.getParallelDescription() + "_" + bftParams.getNumOfThreads());
        try (FileOutputStream fos = new FileOutputStream(file); Workbook workbook = new SXSSFWorkbook()) {
            spreadsheets.forEach(spreadsheet -> spreadsheet.write(workbook, results));
            workbook.write(fos);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }

        LOGGER.info("Metrics saved, {}ms elapsed time", stopwatch.stop().elapsed().toMillis());
    }
    
    private File createResultFile(String fileName) {
        String name = StringUtils.appendIfMissing(fileName, Spreadsheet.EXTENSION);
        return FileSystems.getDefault().getPath(InetAddresses.getHostName() + '_' + name).toFile();
    }

}
