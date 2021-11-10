package bftsmart.microbenchmark.tpcc.monitor.spreadsheet;

import java.io.IOException;
import java.util.Collection;

import bftsmart.microbenchmark.tpcc.monitor.BenchResult;

public class BenchResultSpreadsheetBuilder {

    private final SpreadsheetBuilder spreadsheetBuilder;

    private static final String[] HEADER =
            { "Transaction", "Number of requests", "Number of errors", "Elapsed time (ms)", "Throughput" };

    private BenchResultSpreadsheetBuilder(String sheetName) {
        this.spreadsheetBuilder = SpreadsheetBuilder.builder(sheetName);
    }

    public static BenchResultSpreadsheetBuilder builder(final String sheetName) {
        return new BenchResultSpreadsheetBuilder(sheetName);
    }

    public BenchResultSpreadsheetBuilder addRow(String label, String value) {
        spreadsheetBuilder.addRow(label, value);
        return this;
    }

    public BenchResultSpreadsheetBuilder addRow(String label, Number value) {
        spreadsheetBuilder.addRow(label, value);
        return this;
    }

    public BenchResultSpreadsheetBuilder addBlankRow() {
        spreadsheetBuilder.addBlankRow();
        return this;
    }

    public BenchResultSpreadsheetBuilder addResult(Collection<BenchResult> results) {
        spreadsheetBuilder.addHeader(HEADER);
        for (BenchResult result : results) {
            int position = 0;
            spreadsheetBuilder.addColumn(position++, result.getCommandType().name());
            spreadsheetBuilder.addColumn(position++, result.getSize());
            spreadsheetBuilder.addColumn(position++, result.getTotalErrors());
            spreadsheetBuilder.addColumn(position++, result.getElapsed().toMillis());
            spreadsheetBuilder.addColumn(position, result.getThroughput());
        }
        return this;
    }

    /**
     * Save spreadsheet to defined directory.
     * 
     * @param location
     *            The folder location
     * @throws IOException
     */
    public void write(String location) {
        spreadsheetBuilder.autoSizeColumns();
        spreadsheetBuilder.write(location);
    }

}
