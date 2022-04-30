package bftsmart.microbenchmark.tpcc.client.monitor.spreadsheet;

import java.util.Collection;

import bftsmart.microbenchmark.tpcc.client.monitor.RawResult;
import bftsmart.microbenchmark.tpcc.spreadsheet.Spreadsheet;

public class RawResultSpreadsheetBuilder {

    private final Spreadsheet spreadsheet;

    private static final String[] HEADER =
            { "Transaction Type", "Terminal Id", "Terminal Name", "Latency (ms)", "Status", "Message" };

    private RawResultSpreadsheetBuilder(String sheetName) {
        this.spreadsheet = new Spreadsheet(sheetName);
    }

    public static RawResultSpreadsheetBuilder builder(final String sheetName) {
        return new RawResultSpreadsheetBuilder(sheetName);
    }

    public RawResultSpreadsheetBuilder addLine(String label, Number value) {
        spreadsheet.addRow(label, value);
        return this;
    }

    public RawResultSpreadsheetBuilder addBlankLine() {
        spreadsheet.addBlankRow();
        return this;
    }

    public RawResultSpreadsheetBuilder addResult(Collection<RawResult> results) {
        spreadsheet.addHeader(HEADER);
        for (RawResult result : results) {
            int position = 0;
            spreadsheet.addColumn(position++, String.valueOf(result.getTransactionType()));
            spreadsheet.addColumn(position++, result.getTerminalId());
            spreadsheet.addColumn(position++, result.getTerminalName());
            spreadsheet.addColumn(position++, result.getElapsed().toMillis());
            spreadsheet.addColumn(position++, result.getStatus());
            spreadsheet.addColumn(position, result.getMessage());
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
