package bftsmart.microbenchmark.tpcc.server.transaction.delivery.request;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.annotation.JsonProperty;

import bftsmart.microbenchmark.tpcc.server.transaction.TransactionRequest;

public class DeliveryRequest implements TransactionRequest {

    private String commandId;
    private int transactionType;
    @JsonProperty("w_id")
    private int warehouseId;
    @JsonProperty("o_carrier_id")
    private int orderCarrierId;

    @Override
    public String getCommandId() {
        return commandId;
    }

    @Override
    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public DeliveryRequest withCommandId(String commandId) {
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

    public DeliveryRequest withTransactionType(int transactionType) {
        setTransactionType(transactionType);
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public DeliveryRequest withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public int getOrderCarrierId() {
        return orderCarrierId;
    }

    public void setOrderCarrierId(int orderCarrierId) {
        this.orderCarrierId = orderCarrierId;
    }

    public DeliveryRequest withOrderCarrierId(int orderCarrierId) {
        setOrderCarrierId(orderCarrierId);
        return this;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeAscii(commandId);
        output.writeVarInt(transactionType, true);
        output.writeVarInt(warehouseId, true);
        output.writeVarInt(orderCarrierId, true);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setCommandId(input.readString());
        setTransactionType(input.readVarInt(true));
        setWarehouseId(input.readVarInt(true));
        setOrderCarrierId(input.readVarInt(true));
    }

}
