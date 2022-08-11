package bftsmart.microbenchmark.tpcc.server.hazelcast.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.transaction.TransactionOptions;

import bftsmart.microbenchmark.tpcc.server.hazelcast.transaction.DeliveryTransaction;
import bftsmart.microbenchmark.tpcc.server.hazelcast.transaction.NewOrderTransaction;
import bftsmart.microbenchmark.tpcc.server.hazelcast.transaction.OrderStatusTransaction;
import bftsmart.microbenchmark.tpcc.server.hazelcast.transaction.PaymentTransaction;
import bftsmart.microbenchmark.tpcc.server.hazelcast.transaction.StockLevelTransaction;
import bftsmart.microbenchmark.tpcc.server.transaction.Transaction;

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
    
    @Provides
    @Singleton
    public TransactionOptions transactionOptions() {
        return new TransactionOptions().setTransactionType(TransactionOptions.TransactionType.ONE_PHASE);
    }

    @Provides
    @Singleton
    public HazelcastInstance hazelcastInstance() {
        return Hazelcast.newHazelcastInstance();
    }

}