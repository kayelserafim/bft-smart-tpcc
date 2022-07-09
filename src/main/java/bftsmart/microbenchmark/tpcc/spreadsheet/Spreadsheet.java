package bftsmart.microbenchmark.tpcc.spreadsheet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import bftsmart.microbenchmark.tpcc.util.Numbers;

public class Spreadsheet {

    public static final String EXTENSION = ".xlsx";

    private final Font font;
    private final CellStyle cellStyle;
    private Sheet sheet;
    private int currentRow = 0;

    public Spreadsheet(Workbook workbook, String sheetName) {
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

}
