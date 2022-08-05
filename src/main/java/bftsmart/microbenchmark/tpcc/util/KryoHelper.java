package bftsmart.microbenchmark.tpcc.util;

import java.io.ByteArrayOutputStream;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import com.esotericsoftware.kryo.util.Pool;

import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.input.DeliveryInput;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.output.DeliveryOutput;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.output.OrderOutput;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.input.NewOrderInput;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.output.NewOrderLineOutput;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.output.NewOrderOutput;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.input.OrderStatusInput;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.output.OrderLineOutput;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.output.OrderStatusOutput;
import bftsmart.microbenchmark.tpcc.server.transaction.payment.input.PaymentInput;
import bftsmart.microbenchmark.tpcc.server.transaction.payment.output.PaymentOutput;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.input.StockLevelInput;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.output.StockLevelOutput;

public final class KryoHelper {

    private static final KryoHelper INSTANCE = new KryoHelper();

    private final Pool<Kryo> kryoPool;

    private KryoHelper() {
        kryoPool = new Pool<Kryo>(true, false) {
            protected Kryo create() {
                Kryo kryo = new Kryo();
                kryo.setReferences(false);
                kryo.setOptimizedGenerics(false);
                kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
                kryo.register(int[].class);
                kryo.register(TPCCCommand.class);
                kryo.register(DeliveryInput.class);
                kryo.register(DeliveryOutput.class);
                kryo.register(OrderOutput.class);
                kryo.register(NewOrderInput.class);
                kryo.register(NewOrderOutput.class);
                kryo.register(NewOrderLineOutput.class);
                kryo.register(OrderStatusInput.class);
                kryo.register(OrderLineOutput.class);
                kryo.register(OrderStatusOutput.class);
                kryo.register(PaymentInput.class);
                kryo.register(PaymentOutput.class);
                kryo.register(StockLevelInput.class);
                kryo.register(StockLevelOutput.class);
                return kryo;
            }
        };
    }

    public static KryoHelper getInstance() {
        return INSTANCE;
    }

    public byte[] toBytes(Object obj) {
        Kryo kryo = kryoPool.obtain();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (Output output = new Output(baos)) {
            kryo.writeClassAndObject(output, obj);
            output.flush();
        }
        kryoPool.free(kryo);
        return baos.toByteArray();
    }

    public Object fromBytes(byte[] bytes) {
        Kryo kryo = kryoPool.obtain();
        Object retrievedObject;
        try (Input input = new Input(bytes)) {
            retrievedObject = kryo.readClassAndObject(input);
        }
        kryoPool.free(kryo);
        return retrievedObject;
    }

}