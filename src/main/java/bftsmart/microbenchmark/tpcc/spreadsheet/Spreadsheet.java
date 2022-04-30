package bftsmart.microbenchmark.tpcc.spreadsheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import bftsmart.microbenchmark.tpcc.util.Numbers;

public class Spreadsheet {

    private static final String XLSX_SUFFIX = ".xlsx";
    private final String sheetName;
    private final Workbook workbook;
    private final Sheet sheet;
    private final Font font;
    private final CellStyle cellStyle;
    private int currentRow = 0;

    public Spreadsheet(String sheetName) {
        this.sheetName = sheetName;
        this.workbook = new SXSSFWorkbook();
        this.sheet = workbook.createSheet(sheetName);

        font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);

        cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
    }

    public void addHeader(String[] headers) {
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        Row row = sheet.createRow(currentRow++);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(cellStyle);
        }
    }

    public void addRow(String label, Number value) {
        Row row = sheet.createRow(currentRow++);
        Cell cell = row.createCell(0);
        cell.setCellValue(label);
        row.createCell(1).setCellValue(Numbers.toDouble(value));
    }

    public void addBlankRow() {
        sheet.createRow(currentRow++);
    }

    public void addColumn(Integer column, String value) {
        Row row = sheet.getRow(currentRow - 1);
        if (column == 0 || row == null) {
            row = sheet.createRow(currentRow++);
        }
        row.createCell(column).setCellValue(value);
    }

    public void addColumn(Integer column, Number value) {
        Row row = sheet.getRow(currentRow - 1);
        if (column == 0) {
            row = sheet.createRow(currentRow++);
        }
        row.createCell(column).setCellValue(Numbers.toDouble(value));
    }

    /**
     * Save spreadsheet to defined directory.
     * 
     * @param location
     *            The folder location
     */
    public void write(String location) {
        File file = new File(location + File.separator + sheetName + XLSX_SUFFIX);
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
            IOUtils.closeQuietly(workbook);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

}
