package bftsmart.microbenchmark.tpcc.server.transaction.delivery.input;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeliveryInput implements KryoSerializable {

    @JsonProperty("w_id")
    private int warehouseId;
    @JsonProperty("o_carrier_id")
    private int orderCarrierId;

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public DeliveryInput withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public int getOrderCarrierId() {
        return orderCarrierId;
    }

    public void setOrderCarrierId(int orderCarrierId) {
        this.orderCarrierId = orderCarrierId;
    }

    public DeliveryInput withOrderCarrierId(int orderCarrierId) {
        setOrderCarrierId(orderCarrierId);
        return this;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeVarInt(warehouseId, true);
        output.writeVarInt(orderCarrierId, true);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setWarehouseId(input.readVarInt(true));
        setOrderCarrierId(input.readVarInt(true));
    }
}
