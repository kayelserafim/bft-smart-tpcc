package bftsmart.microbenchmark.tpcc.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import bftsmart.microbenchmark.tpcc.server.transaction.Transaction;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.DeliveryTransaction;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.NewOrderTransaction;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.OrderStatusTransaction;
import bftsmart.microbenchmark.tpcc.server.transaction.payment.PaymentTransaction;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.StockLevelTransaction;

public final class TransactionModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Transaction> multibinder = Multibinder.newSetBinder(binder(), Transaction.class);
        multibinder.addBinding().to(DeliveryTransaction.class);
        multibinder.addBinding().to(NewOrderTransaction.class);
        multibinder.addBinding().to(OrderStatusTransaction.class);
        multibinder.addBinding().to(PaymentTransaction.class);
        multibinder.addBinding().to(StockLevelTransaction.class);
    }

}