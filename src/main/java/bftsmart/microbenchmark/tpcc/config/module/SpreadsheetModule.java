package bftsmart.microbenchmark.tpcc.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import bftsmart.microbenchmark.tpcc.client.monitor.spreadsheet.RawResultSpreadsheet;
import bftsmart.microbenchmark.tpcc.client.monitor.spreadsheet.ResultSpreadsheet;
import bftsmart.microbenchmark.tpcc.client.monitor.spreadsheet.TransactionResultSpreadsheet;

public final class SpreadsheetModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ResultSpreadsheet> multibinder = Multibinder.newSetBinder(binder(), ResultSpreadsheet.class);
        multibinder.addBinding().to(RawResultSpreadsheet.class);
        multibinder.addBinding().to(TransactionResultSpreadsheet.class);
    }

}