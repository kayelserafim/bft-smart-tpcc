package bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.response;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class OrderLineResponse implements KryoSerializable {

    private int supplierWarehouseId;
    private int itemId;
    private int orderQuantities;
    private double amount;
    private long deliveryDateTime;

    public int getSupplierWarehouseId() {
        return supplierWarehouseId;
    }

    public void setSupplierWarehouseId(int supplierWarehouseId) {
        this.supplierWarehouseId = supplierWarehouseId;
    }

    public OrderLineResponse withSupplierWarehouseId(int supplierWarehouseId) {
        setSupplierWarehouseId(supplierWarehouseId);
        return this;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public OrderLineResponse withItemId(int itemId) {
        setItemId(itemId);
        return this;
    }

    public int getOrderQuantities() {
        return orderQuantities;
    }

    public void setOrderQuantities(int orderQuantities) {
        this.orderQuantities = orderQuantities;
    }

    public OrderLineResponse withOrderQuantities(int orderQuantities) {
        setOrderQuantities(orderQuantities);
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public OrderLineResponse withAmount(double amount) {
        setAmount(amount);
        return this;
    }

    public OrderLineResponse withAmount(BigDecimal amount) {
        setAmount(amount.setScale(2, RoundingMode.HALF_UP).doubleValue());
        return this;
    }

    public long getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(long deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public OrderLineResponse withDeliveryDateTime(Long deliveryDateTime) {
        setDeliveryDateTime(deliveryDateTime);
        return this;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeVarInt(supplierWarehouseId, true);
        output.writeVarInt(itemId, true);
        output.writeVarInt(orderQuantities, true);
        output.writeVarDouble(amount, 2, true);
        output.writeVarLong(deliveryDateTime, true);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setSupplierWarehouseId(input.readVarInt(true));
        setItemId(input.readVarInt(true));
        setOrderQuantities(input.readVarInt(true));
        setAmount(input.readVarDouble(2, true));
        setDeliveryDateTime(input.readVarLong(true));
    }

}
