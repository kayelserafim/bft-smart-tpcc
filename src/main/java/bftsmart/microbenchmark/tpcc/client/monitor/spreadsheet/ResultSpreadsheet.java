package bftsmart.microbenchmark.tpcc.client.monitor.spreadsheet;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import bftsmart.microbenchmark.tpcc.client.monitor.RawResult;

public interface ResultSpreadsheet {

    void write(Workbook workbook, List<RawResult> results);

}
