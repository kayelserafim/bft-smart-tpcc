package bftsmart.microbenchmark.tpcc.client.monitor.spreadsheet;

import static org.apache.commons.lang3.BooleanUtils.isTrue;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.poi.ss.usermodel.Workbook;

import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.client.monitor.RawResult;
import bftsmart.microbenchmark.tpcc.config.BFTParams;
import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.spreadsheet.Spreadsheet;
import bftsmart.microbenchmark.tpcc.util.Numbers;

public class RawResultSpreadsheet implements ResultSpreadsheet {

    private static final String SHEET_NAME = "Raw_Result";

    private static final String[] HEADER = { "ID", "Transaction Type", "Terminal Id", "Terminal Name", "Has Conflict",
            "Total Latency (ms)", "Status", "Message" };

    private final TPCCConfig workload;
    private final BFTParams bftParams;

    @Inject
    public RawResultSpreadsheet(final TPCCConfig workload, final BFTParams bftParams) {
        this.workload = workload;
        this.bftParams = bftParams;
    }

    @Override
    public void write(final Workbook workbook, final List<RawResult> results) {
        final Spreadsheet spreadsheet = new Spreadsheet(workbook, SHEET_NAME);
        addInfo(spreadsheet, results);
        spreadsheet.addHeader(HEADER);
        for (RawResult result : results) {
            int position = 0;
            spreadsheet.addColumn(position++, result.getCommandId());
            spreadsheet.addColumn(position++, String.valueOf(result.getTransactionType()));
            spreadsheet.addColumn(position++, result.getTerminalId());
            spreadsheet.addColumn(position++, result.getTerminalName());
            spreadsheet.addColumn(position++, Objects.toString(result.getConflict(), ""));
            spreadsheet.addColumn(position++, result.getElapsed().toMillis());
            spreadsheet.addColumn(position++, result.getStatus());
            spreadsheet.addColumn(position, result.getMessage());
        }
    }

    private void addInfo(final Spreadsheet spreadsheet, final List<RawResult> results) {
        spreadsheet.addRow("Warehouses", workload.getWarehouses());
        spreadsheet.addRow("Terminals", bftParams.getNumOfThreads());
        spreadsheet.addRow("New Order Weight", workload.getNewOrderWeight());
        spreadsheet.addRow("Payment Weight", workload.getPaymentWeight());
        spreadsheet.addRow("Order Status Weight", workload.getOrderStatusWeight());
        spreadsheet.addRow("Delivery Weight", workload.getDeliveryWeight());
        spreadsheet.addRow("Stock Level Weight", workload.getStockLevelWeight());
        spreadsheet.addRow("Total results", results.size());
        addDescriptiveStatistics(spreadsheet, results);
        addConflicts(spreadsheet, results);
        spreadsheet.addBlankRow();
    }

    private void addDescriptiveStatistics(final Spreadsheet spreadsheet, final List<RawResult> results) {
        final double[] times = results.stream()
                .map(RawResult::getElapsed)
                .map(Duration::toMillis)
                .mapToDouble(Long::doubleValue)
                .toArray();

        final DescriptiveStatistics stats = new DescriptiveStatistics(times);
        spreadsheet.addRow("Average Latency (ms)", stats.getMean());
        spreadsheet.addRow("Throughput", Numbers.divide(results.size(), workload.getRunMins().getSeconds()));
        spreadsheet.addRow("Standard Deviation", stats.getStandardDeviation());
    }

    private void addConflicts(final Spreadsheet spreadsheet, final List<RawResult> results) {
        final long count = results.stream().filter(result -> isTrue(result.getConflict())).count();
        spreadsheet.addRow("Percentage of conflicts", Numbers.divide(count, results.size()) * 100);
    }

}
