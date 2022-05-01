package bftsmart.microbenchmark.tpcc.client.monitor;

import static java.time.Duration.ZERO;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.client.monitor.spreadsheet.BenchResultSpreadsheetBuilder;
import bftsmart.microbenchmark.tpcc.client.monitor.spreadsheet.RawResultSpreadsheetBuilder;
import bftsmart.microbenchmark.tpcc.config.ParamConfig;
import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;
import bftsmart.microbenchmark.tpcc.probject.TransactionType;
import bftsmart.microbenchmark.tpcc.util.InetAddresses;

@Singleton
public class MetricCollector {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricCollector.class);

    private final WorkloadConfig workload;
    private final ParamConfig paramConfig;
    private final String hostname;

    @Inject
    public MetricCollector(WorkloadConfig workload, ParamConfig paramConfig) {
        this.workload = workload;
        this.paramConfig = paramConfig;
        this.hostname = InetAddresses.getHostName();
    }

    public CompletableFuture<Void> writeAllResults(List<RawResult> results) {
        LOGGER.info("Saving all metrics files");

        Stopwatch stopwatch = Stopwatch.createStarted();
        CompletableFuture<Void> rawResults = write(results);
        CompletableFuture<Void> resultsByTransaction = writeByTransaction(results);

        return CompletableFuture.allOf(rawResults, resultsByTransaction)
                .thenRun(() -> LOGGER.info("Metrics saved, {}ms elapsed time", stopwatch.stop().elapsed().toMillis()));
    }

    public CompletableFuture<Void> write(List<RawResult> results) {
        return CompletableFuture.runAsync(() -> {
            LOGGER.info("Writing Raw Result list");

            RawResultSpreadsheetBuilder.builder(getSheetName("Metric_Raw_Terminal_Number"))
                    .addLine("Warehouses", workload.getWarehouses())
                    .addLine("Terminals", paramConfig.getNumOfThreads())
                    .addLine("New Order Weight", workload.getNewOrderWeight())
                    .addLine("Payment Weight", workload.getPaymentWeight())
                    .addLine("Order Status Weight", workload.getOrderStatusWeight())
                    .addLine("Delivery Weight", workload.getDeliveryWeight())
                    .addLine("Stock Level Weight", workload.getStockLevelWeight())
                    .addLine("Total results", results.size())
                    .addLine("Total conflicts", results.stream().filter(result -> isTrue(result.getConflict())).count())
                    .addBlankLine()
                    .addResult(results)
                    .write(TPCCConfig.DATA_FOLDER);

            LOGGER.info("Raw Result list size: {}", results.size());
        });
    }

    public CompletableFuture<Void> writeByTransaction(List<RawResult> results) {
        return CompletableFuture.runAsync(() -> {
            LOGGER.info("Writing results per transaction");

            List<BenchResult> benchResult = results.stream()
                    .collect(Collectors.groupingBy(RawResult::getTransactionType))
                    .entrySet()
                    .parallelStream()
                    .map(entry -> {
                        TransactionType key = entry.getKey();
                        List<RawResult> data = entry.getValue();
                        Duration elapsed = data.stream().map(RawResult::getElapsed).reduce(ZERO, Duration::plus);
                        long errors = data.stream().map(RawResult::getStatus).filter(status -> status < 0).count();
                        return new BenchResult(key, elapsed, data.size(), errors);
                    })
                    .collect(Collectors.toList());

            BenchResultSpreadsheetBuilder.builder(getSheetName("Metric_Terminal_Number"))
                    .addRow("Warehouses", workload.getWarehouses())
                    .addRow("Terminals", paramConfig.getNumOfThreads())
                    .addRow("New Order Weight", workload.getNewOrderWeight())
                    .addRow("Payment Weight", workload.getPaymentWeight())
                    .addRow("Order Status Weight", workload.getOrderStatusWeight())
                    .addRow("Delivery Weight", workload.getDeliveryWeight())
                    .addRow("Stock Level Weight", workload.getStockLevelWeight())
                    .addBlankRow()
                    .addResult(benchResult)
                    .write(TPCCConfig.DATA_FOLDER);

            LOGGER.info("Result list size per transaction: {}", benchResult.size());
        });
    }

    private String getSheetName(String prefix) {
        String executionType = BooleanUtils.isTrue(paramConfig.getParallelSmr()) ? "Parallel" : "Sequential";
        return prefix + "_" + executionType + "_" + paramConfig.getNumOfThreads() + "_" + hostname;
    }

}
