package bftsmart.microbenchmark.tpcc.monitor;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;
import bftsmart.microbenchmark.tpcc.monitor.spreadsheet.BenchResultSpreadsheetBuilder;
import bftsmart.microbenchmark.tpcc.monitor.spreadsheet.RawResultSpreadsheetBuilder;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;

@Singleton
public class MetricCollector {

    public static final Logger LOGGER = LoggerFactory.getLogger(MetricCollector.class);

    @Inject
    private WorkloadConfig workload;

    public void writeResults(List<RawResult> results) {
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

    public void writeResultsByTransaction(List<RawResult> results) {
        List<BenchResult> benchResult = results.stream()
                .collect(Collectors.groupingBy(RawResult::getCommandType))
                .entrySet()
                .stream()
                .map(entry -> {
                    TPCCCommandType key = entry.getKey();
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

        LOGGER.info("List of results: {}", benchResult);
        LOGGER.info("Result list size: {}", results.size());
    }

}
