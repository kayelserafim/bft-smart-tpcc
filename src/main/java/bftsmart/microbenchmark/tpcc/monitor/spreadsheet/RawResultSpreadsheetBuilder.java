package bftsmart.microbenchmark.tpcc.monitor.spreadsheet;

import java.io.IOException;
import java.util.Collection;

import bftsmart.microbenchmark.tpcc.monitor.RawResult;

public class RawResultSpreadsheetBuilder {

    private final SpreadsheetBuilder spreadsheetBuilder;

    private static final String[] HEADER =
            { "Command Type", "Terminal Id", "Terminal Name", "Latency (ms)", "Status", "Message" };

    private RawResultSpreadsheetBuilder(String sheetName) {
        this.spreadsheetBuilder = SpreadsheetBuilder.builder(sheetName);
    }

    public static RawResultSpreadsheetBuilder builder(final String sheetName) {
        return new RawResultSpreadsheetBuilder(sheetName);
    }

    public RawResultSpreadsheetBuilder addLine(String label, String value) {
        spreadsheetBuilder.addRow(label, value);
        return this;
    }

    public RawResultSpreadsheetBuilder addLine(String label, Number value) {
        spreadsheetBuilder.addRow(label, value);
        return this;
    }

    public RawResultSpreadsheetBuilder addBlankLine() {
        spreadsheetBuilder.addBlankRow();
        return this;
    }

    public RawResultSpreadsheetBuilder addResult(Collection<RawResult> results) {
        spreadsheetBuilder.addHeader(HEADER);
        for (RawResult result : results) {
            int position = 0;
            spreadsheetBuilder.addColumn(position++, String.valueOf(result.getCommandType()));
            spreadsheetBuilder.addColumn(position++, result.getTerminalId());
            spreadsheetBuilder.addColumn(position++, result.getTerminalName());
            spreadsheetBuilder.addColumn(position++, result.getElapsed().toMillis());
            spreadsheetBuilder.addColumn(position++, result.getStatus());
            spreadsheetBuilder.addColumn(position, result.getMessage());
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
