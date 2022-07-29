package bftsmart.microbenchmark.tpcc.client.monitor.spreadsheet;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Workbook;

import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.client.monitor.RawResult;
import bftsmart.microbenchmark.tpcc.client.monitor.TransactionResult;
import bftsmart.microbenchmark.tpcc.config.BFTParams;
import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;
import bftsmart.microbenchmark.tpcc.probject.TransactionType;
import bftsmart.microbenchmark.tpcc.spreadsheet.Spreadsheet;

public class TransactionResultSpreadsheet implements ResultSpreadsheet {

    private static final String SHEET_NAME = "Transaction_Result";

    private static final String[] HEADER =
            { "Transaction", "Number of requests", "Number of errors", "Average Latency (ms)", "Throughput" };

    private final WorkloadConfig workload;
    private final BFTParams bftParams;

    @Inject
    public TransactionResultSpreadsheet(WorkloadConfig workload, BFTParams bftParams) {
        this.workload = workload;
        this.bftParams = bftParams;
    }

    @Override
    public void write(Workbook workbook, List<RawResult> results) {
        final Spreadsheet spreadsheet = new Spreadsheet(workbook, SHEET_NAME);
        final List<TransactionResult> transactionResults = groupByTransactionType(results);
        addInfo(spreadsheet);
        spreadsheet.addHeader(HEADER);
        for (TransactionResult result : transactionResults) {
            int position = 0;
            spreadsheet.addColumn(position++, result.getType().name());
            spreadsheet.addColumn(position++, result.getSize());
            spreadsheet.addColumn(position++, result.getTotalErrors());
            spreadsheet.addColumn(position++, result.getAverageLatency());
            spreadsheet.addColumn(position, result.getThroughput());
        }
    }

    private List<TransactionResult> groupByTransactionType(List<RawResult> results) {
        return results.stream()
                .collect(Collectors.groupingBy(RawResult::getTransactionType))
                .entrySet()
                .parallelStream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private void addInfo(final Spreadsheet spreadsheet) {
        spreadsheet.addRow("Warehouses", workload.getWarehouses());
        spreadsheet.addRow("Terminals", bftParams.getNumOfThreads());
        spreadsheet.addRow("New Order Weight", workload.getNewOrderWeight());
        spreadsheet.addRow("Payment Weight", workload.getPaymentWeight());
        spreadsheet.addRow("Order Status Weight", workload.getOrderStatusWeight());
        spreadsheet.addRow("Delivery Weight", workload.getDeliveryWeight());
        spreadsheet.addRow("Stock Level Weight", workload.getStockLevelWeight());
        spreadsheet.addBlankRow();
    }

    private TransactionResult convert(final Entry<TransactionType, List<RawResult>> entry) {
        final TransactionType key = entry.getKey();
        final List<RawResult> data = entry.getValue();
        final long errors = data.stream().map(RawResult::getStatus).filter(status -> status < 0).count();
        return new TransactionResult(key, workload.getRunMins(), data.size(), errors);
    }

}
