package bftsmart.microbenchmark.tpcc.server.transaction.delivery.response;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class OrderResponse implements KryoSerializable {

    private int districtId;
    private int orderId;

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public OrderResponse withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public OrderResponse withOrderId(int orderId) {
        setOrderId(orderId);
        return this;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeVarInt(districtId, true);
        output.writeVarInt(orderId, true);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setDistrictId(input.readVarInt(true));
        setOrderId(input.readVarInt(true));
    }

}
