package bftsmart.microbenchmark.tpcc.monitor;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;
import bftsmart.microbenchmark.tpcc.monitor.spreadsheet.BenchResultSpreadsheetBuilder;
import bftsmart.microbenchmark.tpcc.monitor.spreadsheet.RawResultSpreadsheetBuilder;
import bftsmart.microbenchmark.tpcc.probject.TransactionType;

@Singleton
public class MetricCollector {

    public static final Logger LOGGER = LoggerFactory.getLogger(MetricCollector.class);

    @Inject
    private WorkloadConfig workload;

    public void writeAllResults(List<RawResult> results) {
        LOGGER.info("Saving all metrics files");

        Stopwatch stopwatch = Stopwatch.createStarted();
        CompletableFuture<Void> rawResults = CompletableFuture.runAsync(() -> write(results));
        CompletableFuture<Void> resultsByTransaction = CompletableFuture.runAsync(() -> writeByTransaction(results));
        CompletableFuture.allOf(rawResults, resultsByTransaction).join();

        LOGGER.info("{}ms elapsed time to save metrics", stopwatch.stop().elapsed().toMillis());
    }

    public void write(List<RawResult> results) {
        LOGGER.info("Writing Raw Result list");

        RawResultSpreadsheetBuilder.builder("Metric_Raw_Terminal_Number_" + workload.getTerminals())
                .addLine("Warehouses", workload.getWarehouses())
                .addLine("Terminals", workload.getTerminals())
                .addLine("New Order Weight", workload.getNewOrderWeight())
                .addLine("Payment Weight", workload.getPaymentWeight())
                .addLine("Order Status Weight", workload.getOrderStatusWeight())
                .addLine("Delivery Weight", workload.getDeliveryWeight())
                .addLine("Stock Level Weight", workload.getStockLevelWeight())
                .addBlankLine()
                .addResult(results)
                .write(TPCCConfig.DATA_FOLDER);

        LOGGER.info("Raw Result list size: {}", results.size());
    }

    public void writeByTransaction(List<RawResult> results) {
        LOGGER.info("Writing results per transaction");

        List<BenchResult> benchResult = results.stream()
                .collect(Collectors.groupingBy(RawResult::getTransactionType))
                .entrySet()
                .parallelStream()
                .map(entry -> {
                    TransactionType key = entry.getKey();
                    List<RawResult> value = entry.getValue();
                    Duration elapsed = value.stream().map(RawResult::getElapsed).reduce(Duration.ZERO, Duration::plus);
                    long totalErrors = value.stream().map(RawResult::getStatus).filter(result -> result < 0).count();
                    return new BenchResult(key, elapsed, value.size(), totalErrors);
                })
                .collect(Collectors.toList());

        BenchResultSpreadsheetBuilder.builder("Metric_Terminal_Number_" + workload.getTerminals())
                .addRow("Warehouses", workload.getWarehouses())
                .addRow("Terminals", workload.getTerminals())
                .addRow("New Order Weight", workload.getNewOrderWeight())
                .addRow("Payment Weight", workload.getPaymentWeight())
                .addRow("Order Status Weight", workload.getOrderStatusWeight())
                .addRow("Delivery Weight", workload.getDeliveryWeight())
                .addRow("Stock Level Weight", workload.getStockLevelWeight())
                .addBlankRow()
                .addResult(benchResult)
                .write(TPCCConfig.DATA_FOLDER);

        LOGGER.info("Result list size per transaction: {}", benchResult.size());
    }

}
