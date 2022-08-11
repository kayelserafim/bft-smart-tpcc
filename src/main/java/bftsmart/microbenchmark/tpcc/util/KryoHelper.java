package bftsmart.microbenchmark.tpcc.util;

import java.io.ByteArrayOutputStream;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import com.esotericsoftware.kryo.util.Pool;

import bftsmart.microbenchmark.tpcc.server.transaction.TransactionResponse;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.request.DeliveryRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.response.DeliveryOutput;
import bftsmart.microbenchmark.tpcc.server.transaction.delivery.response.OrderResponse;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.request.NewOrderRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.response.NewOrderLineResponse;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.response.NewOrderResponse;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.request.OrderStatusRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.response.OrderLineResponse;
import bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.response.OrderStatusResponse;
import bftsmart.microbenchmark.tpcc.server.transaction.payment.request.PaymentRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.payment.response.PaymentResponse;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.request.StockLevelRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.response.StockLevelResponse;

public final class KryoHelper {

    private static final KryoHelper INSTANCE = new KryoHelper();

    private final Pool<Kryo> kryoPool;

    private KryoHelper() {
        kryoPool = new Pool<Kryo>(true, true) {
            protected Kryo create() {
                Kryo kryo = new Kryo();
                kryo.setReferences(false);
                kryo.setOptimizedGenerics(false);
                kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
                kryo.register(int[].class);
                kryo.register(DeliveryRequest.class);
                kryo.register(DeliveryOutput.class);
                kryo.register(OrderResponse.class);
                kryo.register(NewOrderRequest.class);
                kryo.register(NewOrderResponse.class);
                kryo.register(NewOrderLineResponse.class);
                kryo.register(OrderStatusRequest.class);
                kryo.register(OrderLineResponse.class);
                kryo.register(OrderStatusResponse.class);
                kryo.register(PaymentRequest.class);
                kryo.register(PaymentResponse.class);
                kryo.register(StockLevelRequest.class);
                kryo.register(StockLevelResponse.class);
                kryo.register(TransactionResponse.class);
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