package bftsmart.microbenchmark.tpcc.server.transaction.delivery.response;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class DeliveryOutput implements KryoSerializable {

    private long dateTime;
    private int warehouseId;
    private int orderCarrierId;
    private OrderResponse[] orderIds;

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public DeliveryOutput withDateTime(long dateTime) {
        setDateTime(dateTime);
        return this;
    }

    public DeliveryOutput withDateTime(LocalDateTime dateTime) {
        return withDateTime(dateTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli());
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public DeliveryOutput withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public int getOrderCarrierId() {
        return orderCarrierId;
    }

    public void setOrderCarrierId(int orderCarrierId) {
        this.orderCarrierId = orderCarrierId;
    }

    public DeliveryOutput withOrderCarrierId(int orderCarrierId) {
        setOrderCarrierId(orderCarrierId);
        return this;
    }

    public OrderResponse[] getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(OrderResponse[] orderIds) {
        this.orderIds = orderIds;
    }

    public DeliveryOutput withOrderIds(OrderResponse[] orderIds) {
        setOrderIds(orderIds);
        return this;
    }

    public DeliveryOutput withOrderIds(List<OrderResponse> orderIds) {
        setOrderIds(orderIds.toArray(OrderResponse[]::new));
        return this;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeVarLong(dateTime, true);
        output.writeVarInt(warehouseId, true);
        output.writeVarInt(orderCarrierId, true);
        kryo.writeObject(output, orderIds);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setDateTime(input.readVarLong(true));
        setWarehouseId(input.readVarInt(true));
        setOrderCarrierId(input.readVarInt(true));
        setOrderIds(kryo.readObject(input, OrderResponse[].class));
    }

}
