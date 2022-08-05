package bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.input;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderStatusInput implements KryoSerializable {

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
        output.writeVarInt(warehouseId, true);
        output.writeVarInt(districtId, true);
        output.writeVarInt(customerId, true);
        output.writeBoolean(customerByName);
        output.writeAscii(customerLastName);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setWarehouseId(input.readVarInt(true));
        setDistrictId(input.readVarInt(true));
        setCustomerId(input.readVarInt(true));
        setCustomerByName(input.readBoolean());
        setCustomerLastName(input.readString());
    }
}
