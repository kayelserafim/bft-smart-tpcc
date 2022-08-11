package bftsmart.microbenchmark.tpcc.client.module;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import bftsmart.microbenchmark.tpcc.client.command.TPCCCommand;
import bftsmart.microbenchmark.tpcc.client.command.DeliveryCommand;
import bftsmart.microbenchmark.tpcc.client.command.NewOrderCommand;
import bftsmart.microbenchmark.tpcc.client.command.OrderStatusCommand;
import bftsmart.microbenchmark.tpcc.client.command.PaymentCommand;
import bftsmart.microbenchmark.tpcc.client.command.StockLevelCommand;

public final class CommandModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<TPCCCommand> multibinder = Multibinder.newSetBinder(binder(), TPCCCommand.class);
        multibinder.addBinding().to(DeliveryCommand.class);
        multibinder.addBinding().to(NewOrderCommand.class);
        multibinder.addBinding().to(OrderStatusCommand.class);
        multibinder.addBinding().to(PaymentCommand.class);
        multibinder.addBinding().to(StockLevelCommand.class);
    }

}