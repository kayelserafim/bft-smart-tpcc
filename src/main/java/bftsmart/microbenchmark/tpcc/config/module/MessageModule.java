package bftsmart.microbenchmark.tpcc.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import bftsmart.microbenchmark.tpcc.client.command.Command;
import bftsmart.microbenchmark.tpcc.client.command.DeliveryCommand;
import bftsmart.microbenchmark.tpcc.client.command.NewOrderCommand;
import bftsmart.microbenchmark.tpcc.client.command.OrderStatusCommand;
import bftsmart.microbenchmark.tpcc.client.command.PaymentCommand;
import bftsmart.microbenchmark.tpcc.client.command.StockLevelCommand;

public final class MessageModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Command> multibinder = Multibinder.newSetBinder(binder(), Command.class);
        multibinder.addBinding().to(DeliveryCommand.class);
        multibinder.addBinding().to(NewOrderCommand.class);
        multibinder.addBinding().to(OrderStatusCommand.class);
        multibinder.addBinding().to(PaymentCommand.class);
        multibinder.addBinding().to(StockLevelCommand.class);
    }

}