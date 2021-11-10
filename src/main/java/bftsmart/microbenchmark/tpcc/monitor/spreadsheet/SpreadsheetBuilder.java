package bftsmart.microbenchmark.tpcc.monitor.spreadsheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bftsmart.microbenchmark.tpcc.util.Numbers;

public class SpreadsheetBuilder {
    
    public static final Logger LOGGER = LoggerFactory.getLogger(SpreadsheetBuilder.class);

    private static final String XLSX_SUFFIX = ".xlsx";
    private final String sheetName;
    private final Workbook workbook;
    private final Sheet sheet;
    private final Font font;
    private final CellStyle cellStyle;
    private int currentRow = 0;
    private int headersLength;

    private SpreadsheetBuilder(String sheetName) {
        this.sheetName = sheetName;
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet(sheetName);

        font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);

        cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
    }

    public static SpreadsheetBuilder builder(final String sheetName) {
        return new SpreadsheetBuilder(sheetName);
    }

    public SpreadsheetBuilder addHeader(String[] headers) {
        this.headersLength = headers.length;
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        Row row = sheet.createRow(currentRow++);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(cellStyle);
        }
        return this;
    }

    public SpreadsheetBuilder addRow(String label, String value) {
        Row row = sheet.createRow(currentRow++);
        Cell cell = row.createCell(0);
        cell.setCellValue(label);
        cell.setCellStyle(cellStyle);
        row.createCell(1).setCellValue(value);
        return this;
    }

    public SpreadsheetBuilder addRow(String label, Number value) {
        Row row = sheet.createRow(currentRow++);
        Cell cell = row.createCell(0);
        cell.setCellValue(label);
        row.createCell(1).setCellValue(Numbers.toDouble(value));
        return this;
    }

    public SpreadsheetBuilder addBlankRow() {
        sheet.createRow(currentRow++);
        return this;
    }

    public SpreadsheetBuilder addColumn(Integer column, String value) {
        Row row = sheet.getRow(currentRow - 1);
        if (column == 0 || row == null) {
            row = sheet.createRow(currentRow++);
        }
        row.createCell(column).setCellValue(value);
        return this;
    }

    public SpreadsheetBuilder addColumn(Integer column, Number value) {
        Row row = sheet.getRow(currentRow - 1);
        if (column == 0) {
            row = sheet.createRow(currentRow++);
        }
        row.createCell(column).setCellValue(Numbers.toDouble(value));
        return this;
    }

    public void autoSizeColumns() {
        for (int i = 0; i < headersLength; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    /**
     * Save spreadsheet to defined directory.
     * 
     * @param location
     *            The folder location
     * @throws IOException
     */
    public void write(String location) {
        File file = new File(location + File.separator + sheetName + XLSX_SUFFIX);
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
            IOUtils.closeQuietly(workbook);
        } catch (IOException ioe) {
            LOGGER.error(ioe.getMessage(), ioe);
        }
    }

}
