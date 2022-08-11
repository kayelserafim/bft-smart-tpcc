package bftsmart.microbenchmark.tpcc.server.transaction.neworder.request;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.annotation.JsonProperty;

import bftsmart.microbenchmark.tpcc.server.transaction.TransactionRequest;

public class NewOrderRequest implements TransactionRequest {

    private String commandId;
    private int transactionType;
    @JsonProperty("w_id")
    private int warehouseId;
    @JsonProperty("d_id")
    private int districtId;
    @JsonProperty("c_id")
    private int customerId;
    @JsonProperty("ol_o_cnt")
    private int orderLineCnt;
    @JsonProperty("o_all_local")
    private int orderAllLocal;
    @JsonProperty("itemIds")
    private int[] itemIds;
    @JsonProperty("supplierWarehouseIDs")
    private int[] supplierWarehouseIds;
    @JsonProperty("orderQuantities")
    private int[] orderQuantities;

    @Override
    public String getCommandId() {
        return commandId;
    }

    @Override
    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public NewOrderRequest withCommandId(String commandId) {
        setCommandId(commandId);
        return this;
    }

    @Override
    public int getTransactionType() {
        return transactionType;
    }

    @Override
    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public NewOrderRequest withTransactionType(int transactionType) {
        setTransactionType(transactionType);
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public NewOrderRequest withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public NewOrderRequest withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public NewOrderRequest withCustomerId(int customerId) {
        setCustomerId(customerId);
        return this;
    }

    public int getOrderLineCnt() {
        return orderLineCnt;
    }

    public void setOrderLineCnt(int orderLineCnt) {
        this.orderLineCnt = orderLineCnt;
    }

    public NewOrderRequest withOrderLineCnt(int orderLineCnt) {
        setOrderLineCnt(orderLineCnt);
        return this;
    }

    public int getOrderAllLocal() {
        return orderAllLocal;
    }

    public void setOrderAllLocal(int orderAllLocal) {
        this.orderAllLocal = orderAllLocal;
    }

    public NewOrderRequest withOrderAllLocal(int orderAllLocal) {
        setOrderAllLocal(orderAllLocal);
        return this;
    }

    public int[] getItemIds() {
        return itemIds;
    }

    public void setItemIds(int[] itemIds) {
        this.itemIds = itemIds;
    }

    public NewOrderRequest withItemIds(int[] itemIds) {
        setItemIds(itemIds);
        return this;
    }

    public int[] getSupplierWarehouseIds() {
        return supplierWarehouseIds;
    }

    public void setSupplierWarehouseIds(int[] supplierWarehouseIds) {
        this.supplierWarehouseIds = supplierWarehouseIds;
    }

    public NewOrderRequest withSupplierWarehouseIds(int[] supplierWarehouseIds) {
        setSupplierWarehouseIds(supplierWarehouseIds);
        return this;
    }

    public int[] getOrderQuantities() {
        return orderQuantities;
    }

    public void setOrderQuantities(int[] orderQuantities) {
        this.orderQuantities = orderQuantities;
    }

    public NewOrderRequest withOrderQuantities(int[] orderQuantities) {
        setOrderQuantities(orderQuantities);
        return this;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeAscii(commandId);
        output.writeVarInt(transactionType, true);
        output.writeVarInt(warehouseId, true);
        output.writeVarInt(districtId, true);
        output.writeVarInt(customerId, true);
        output.writeVarInt(orderLineCnt, true);
        output.writeVarInt(orderAllLocal, true);
        kryo.writeObjectOrNull(output, itemIds, int[].class);
        kryo.writeObjectOrNull(output, supplierWarehouseIds, int[].class);
        kryo.writeObjectOrNull(output, orderQuantities, int[].class);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setCommandId(input.readString());
        setTransactionType(input.readVarInt(true));
        setWarehouseId(input.readVarInt(true));
        setDistrictId(input.readVarInt(true));
        setCustomerId(input.readVarInt(true));
        setOrderLineCnt(input.readVarInt(true));
        setOrderAllLocal(input.readVarInt(true));
        setItemIds(kryo.readObjectOrNull(input, int[].class));
        setSupplierWarehouseIds(kryo.readObjectOrNull(input, int[].class));
        setOrderQuantities(kryo.readObjectOrNull(input, int[].class));
    }

}
