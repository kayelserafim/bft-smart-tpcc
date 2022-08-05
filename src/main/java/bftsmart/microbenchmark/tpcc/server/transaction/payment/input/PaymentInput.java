package bftsmart.microbenchmark.tpcc.server.transaction.payment.input;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentInput implements KryoSerializable {

    @JsonProperty("w_id")
    private int warehouseId;
    @JsonProperty("c_w_id")
    private int customerWarehouseId;
    @JsonProperty("d_id")
    private int districtId;
    @JsonProperty("c_d_id")
    private int customerDistrictId;
    @JsonProperty("c_id")
    private int customerId;
    @JsonProperty("c_name")
    private String customerLastName;
    @JsonProperty("c_by_name")
    private boolean customerByName;
    @JsonProperty("payment_amount")
    private double paymentAmount;

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public PaymentInput withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public int getCustomerWarehouseId() {
        return customerWarehouseId;
    }

    public void setCustomerWarehouseId(int customerWarehouseId) {
        this.customerWarehouseId = customerWarehouseId;
    }

    public PaymentInput withCustomerWarehouseId(int customerWarehouseId) {
        setCustomerWarehouseId(customerWarehouseId);
        return this;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public PaymentInput withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public int getCustomerDistrictId() {
        return customerDistrictId;
    }

    public void setCustomerDistrictId(int customerDistrictId) {
        this.customerDistrictId = customerDistrictId;
    }

    public PaymentInput withCustomerDistrictId(int customerDistrictId) {
        setCustomerDistrictId(customerDistrictId);
        return this;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public PaymentInput withCustomerId(int customerId) {
        setCustomerId(customerId);
        return this;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public PaymentInput withCustomerLastName(String customerLastName) {
        setCustomerLastName(customerLastName);
        return this;
    }

    public boolean getCustomerByName() {
        return customerByName;
    }

    public void setCustomerByName(boolean customerByName) {
        this.customerByName = customerByName;
    }

    public PaymentInput withCustomerByName(boolean customerByName) {
        setCustomerByName(customerByName);
        return this;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public PaymentInput withPaymentAmount(BigDecimal paymentAmount) {
        setPaymentAmount(paymentAmount.setScale(2, RoundingMode.HALF_UP).doubleValue());
        return this;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeVarInt(warehouseId, true);
        output.writeVarInt(customerWarehouseId, true);
        output.writeVarInt(districtId, true);
        output.writeVarInt(customerDistrictId, true);
        output.writeVarInt(customerId, true);
        output.writeAscii(customerLastName);
        output.writeBoolean(customerByName);
        output.writeVarDouble(paymentAmount, 2, true);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setWarehouseId(input.readVarInt(true));
        setCustomerWarehouseId(input.readVarInt(true));
        setDistrictId(input.readVarInt(true));
        setCustomerDistrictId(input.readVarInt(true));
        setCustomerId(input.readVarInt(true));
        setCustomerLastName(input.readString());
        setCustomerByName(input.readBoolean());
        setPaymentAmount(input.readVarDouble(2, true));
    }
}
