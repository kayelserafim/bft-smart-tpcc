package bftsmart.microbenchmark.tpcc.monitor.spreadsheet;

import java.util.Collection;

import bftsmart.microbenchmark.tpcc.monitor.BenchResult;

public class BenchResultSpreadsheetBuilder {

    private final Spreadsheet spreadsheet;

    private static final String[] HEADER =
            { "Transaction", "Number of requests", "Number of errors", "Latency (ms)", "Throughput (op/ms)" };

    private BenchResultSpreadsheetBuilder(String sheetName) {
        this.spreadsheet = new Spreadsheet(sheetName);
    }

    public static BenchResultSpreadsheetBuilder builder(final String sheetName) {
        return new BenchResultSpreadsheetBuilder(sheetName);
    }

    public BenchResultSpreadsheetBuilder addRow(String label, Number value) {
        spreadsheet.addRow(label, value);
        return this;
    }

    public BenchResultSpreadsheetBuilder addBlankRow() {
        spreadsheet.addBlankRow();
        return this;
    }

    public BenchResultSpreadsheetBuilder addResult(Collection<BenchResult> results) {
        spreadsheet.addHeader(HEADER);
        for (BenchResult result : results) {
            int position = 0;
            spreadsheet.addColumn(position++, result.getTransactionType().name());
            spreadsheet.addColumn(position++, result.getSize());
            spreadsheet.addColumn(position++, result.getTotalErrors());
            spreadsheet.addColumn(position++, result.getElapsed().toMillis());
            spreadsheet.addColumn(position, result.getThroughput());
        }
        return this;
    }

    /**
     * Save spreadsheet to defined directory.
     * 
     * @param location
     *            The folder location
     */
    public void write(String location) {
        spreadsheet.write(location);
    }

}
