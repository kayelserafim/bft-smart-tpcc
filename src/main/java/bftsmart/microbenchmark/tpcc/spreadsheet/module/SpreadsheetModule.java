package bftsmart.microbenchmark.tpcc.spreadsheet.module;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import bftsmart.microbenchmark.tpcc.spreadsheet.RawResultSpreadsheet;
import bftsmart.microbenchmark.tpcc.spreadsheet.ResultSpreadsheet;
import bftsmart.microbenchmark.tpcc.spreadsheet.TransactionResultSpreadsheet;

public final class SpreadsheetModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ResultSpreadsheet> multibinder = Multibinder.newSetBinder(binder(), ResultSpreadsheet.class);
        multibinder.addBinding().to(RawResultSpreadsheet.class);
        multibinder.addBinding().to(TransactionResultSpreadsheet.class);
    }

}