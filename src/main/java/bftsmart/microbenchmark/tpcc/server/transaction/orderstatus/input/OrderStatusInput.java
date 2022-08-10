package bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.input;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.annotation.JsonProperty;

import bftsmart.microbenchmark.tpcc.domain.CommandRequest;

public class OrderStatusInput implements CommandRequest {

    private String commandId;
    private int transactionType;
    @JsonProperty("w_id")
    private int warehouseId;
    @JsonProperty("d_id")
    private int districtId;
    @JsonProperty("c_id")
    private int customerId;
    @JsonProperty("c_by_name")
    private boolean customerByName;
    @JsonProperty("c_name")
    private String customerLastName;

    @Override
    public String getCommandId() {
        return commandId;
    }

    @Override
    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public OrderStatusInput withCommandId(String commandId) {
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

    public OrderStatusInput withTransactionType(int transactionType) {
        setTransactionType(transactionType);
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public OrderStatusInput withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public OrderStatusInput withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public OrderStatusInput withCustomerId(int customerId) {
        setCustomerId(customerId);
        return this;
    }

    public boolean getCustomerByName() {
        return customerByName;
    }

    public void setCustomerByName(boolean customerByName) {
        this.customerByName = customerByName;
    }

    public OrderStatusInput withCustomerByName(boolean customerByName) {
        setCustomerByName(customerByName);
        return this;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public OrderStatusInput withCustomerLastName(String customerLastName) {
        setCustomerLastName(customerLastName);
        return this;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeAscii(commandId);
        output.writeVarInt(transactionType, true);
        output.writeVarInt(warehouseId, true);
        output.writeVarInt(districtId, true);
        output.writeVarInt(customerId, true);
        output.writeBoolean(customerByName);
        output.writeAscii(customerLastName);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setCommandId(input.readString());
        setTransactionType(input.readVarInt(true));
        setWarehouseId(input.readVarInt(true));
        setDistrictId(input.readVarInt(true));
        setCustomerId(input.readVarInt(true));
        setCustomerByName(input.readBoolean());
        setCustomerLastName(input.readString());
    }
}
