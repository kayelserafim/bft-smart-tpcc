package bftsmart.microbenchmark.tpcc.server.transaction.neworder.response;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class NewOrderLineResponse implements KryoSerializable {

    private int supplierWarehouseId;
    private int itemId;
    private String itemName;
    private int orderQuantities;
    private int stockQuantities;
    private char brandGeneric;
    private double itemPrice;
    private double orderLineAmounts;

    public int getSupplierWarehouseId() {
        return supplierWarehouseId;
    }

    public void setSupplierWarehouseId(int supplierWarehouseId) {
        this.supplierWarehouseId = supplierWarehouseId;
    }

    public NewOrderLineResponse withSupplierWarehouseId(int supplierWarehouseId) {
        setSupplierWarehouseId(supplierWarehouseId);
        return this;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public NewOrderLineResponse withItemId(int itemId) {
        setItemId(itemId);
        return this;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public NewOrderLineResponse withItemName(String itemName) {
        setItemName(itemName);
        return this;
    }

    public int getOrderQuantities() {
        return orderQuantities;
    }

    public void setOrderQuantities(int orderQuantities) {
        this.orderQuantities = orderQuantities;
    }

    public NewOrderLineResponse withOrderQuantities(int orderQuantities) {
        setOrderQuantities(orderQuantities);
        return this;
    }

    public int getStockQuantities() {
        return stockQuantities;
    }

    public void setStockQuantities(int stockQuantities) {
        this.stockQuantities = stockQuantities;
    }

    public NewOrderLineResponse withStockQuantities(int stockQuantities) {
        setStockQuantities(stockQuantities);
        return this;
    }

    public char getBrandGeneric() {
        return brandGeneric;
    }

    public void setBrandGeneric(char brandGeneric) {
        this.brandGeneric = brandGeneric;
    }

    public NewOrderLineResponse withBrandGeneric(char brandGeneric) {
        setBrandGeneric(brandGeneric);
        return this;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public NewOrderLineResponse withItemPrice(double itemPrice) {
        setItemPrice(itemPrice);
        return this;
    }

    public NewOrderLineResponse withItemPrice(BigDecimal itemPrice) {
        return withItemPrice(itemPrice.setScale(2, RoundingMode.HALF_UP).doubleValue());
    }

    public double getOrderLineAmounts() {
        return orderLineAmounts;
    }

    public void setOrderLineAmounts(double orderLineAmounts) {
        this.orderLineAmounts = orderLineAmounts;
    }

    public NewOrderLineResponse withOrderLineAmounts(double orderLineAmounts) {
        setOrderLineAmounts(orderLineAmounts);
        return this;
    }

    public NewOrderLineResponse withOrderLineAmounts(BigDecimal orderLineAmounts) {
        return withOrderLineAmounts(orderLineAmounts.setScale(2, RoundingMode.HALF_UP).doubleValue());
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeVarInt(supplierWarehouseId, true);
        output.writeVarInt(itemId, true);
        output.writeAscii(itemName);
        output.writeVarInt(orderQuantities, true);
        output.writeVarInt(stockQuantities, true);
        output.writeChar(brandGeneric);
        output.writeVarDouble(itemPrice, 2, true);
        output.writeVarDouble(orderLineAmounts, 2, true);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setSupplierWarehouseId(input.readVarInt(true));
        setItemId(input.readVarInt(true));
        setItemName(input.readString());
        setOrderQuantities(input.readVarInt(true));
        setStockQuantities(input.readVarInt(true));
        setBrandGeneric(input.readChar());
        setItemPrice(input.readVarDouble(2, true));
        setOrderLineAmounts(input.readVarDouble(2, true));
    }

}
