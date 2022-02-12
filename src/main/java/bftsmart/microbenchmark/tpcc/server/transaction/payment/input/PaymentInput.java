package bftsmart.microbenchmark.tpcc.server.transaction.payment.input;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentInput implements Serializable {

    private static final long serialVersionUID = -4655170455081665379L;

    @JsonProperty("w_id")
    private Integer warehouseId;
    @JsonProperty("c_w_id")
    private Integer customerWarehouseId;
    @JsonProperty("d_id")
    private Integer districtId;
    @JsonProperty("c_d_id")
    private Integer customerDistrictId;
    @JsonProperty("c_id")
    private Integer customerId;
    @JsonProperty("c_name")
    private String customerLastName;
    @JsonProperty("c_by_name")
    private Boolean customerByName;
    @JsonProperty("payment_amount")
    private BigDecimal paymentAmount;

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public PaymentInput withWarehouseId(Integer warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public Integer getCustomerWarehouseId() {
        return customerWarehouseId;
    }

    public void setCustomerWarehouseId(Integer customerWarehouseId) {
        this.customerWarehouseId = customerWarehouseId;
    }

    public PaymentInput withCustomerWarehouseId(Integer customerWarehouseId) {
        setCustomerWarehouseId(customerWarehouseId);
        return this;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public PaymentInput withDistrictId(Integer districtId) {
        setDistrictId(districtId);
        return this;
    }

    public Integer getCustomerDistrictId() {
        return customerDistrictId;
    }

    public void setCustomerDistrictId(Integer customerDistrictId) {
        this.customerDistrictId = customerDistrictId;
    }

    public PaymentInput withCustomerDistrictId(Integer customerDistrictId) {
        setCustomerDistrictId(customerDistrictId);
        return this;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public PaymentInput withCustomerId(Integer customerId) {
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

    public Boolean getCustomerByName() {
        return customerByName;
    }

    public void setCustomerByName(Boolean customerByName) {
        this.customerByName = customerByName;
    }

    public PaymentInput withCustomerByName(Boolean customerByName) {
        setCustomerByName(customerByName);
        return this;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public PaymentInput withPaymentAmount(BigDecimal paymentAmount) {
        setPaymentAmount(paymentAmount);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PaymentInput [warehouseId=")
                .append(warehouseId)
                .append(", customerWarehouseId=")
                .append(customerWarehouseId)
                .append(", districtId=")
                .append(districtId)
                .append(", customerDistrictId=")
                .append(customerDistrictId)
                .append(", customerId=")
                .append(customerId)
                .append(", customerLastName=")
                .append(customerLastName)
                .append(", customerByName=")
                .append(customerByName)
                .append(", paymentAmount=")
                .append(paymentAmount)
                .append(']');
        return builder.toString();
    }

}
