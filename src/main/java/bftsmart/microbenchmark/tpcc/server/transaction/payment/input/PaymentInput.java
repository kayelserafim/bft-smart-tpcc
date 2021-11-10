package bftsmart.microbenchmark.tpcc.server.transaction.payment.input;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentInput {

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
    private String customerName;
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

    public Integer getCustomerWarehouseId() {
        return customerWarehouseId;
    }

    public void setCustomerWarehouseId(Integer customerWarehouseId) {
        this.customerWarehouseId = customerWarehouseId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getCustomerDistrictId() {
        return customerDistrictId;
    }

    public void setCustomerDistrictId(Integer customerDistrictId) {
        this.customerDistrictId = customerDistrictId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Boolean getCustomerByName() {
        return customerByName;
    }

    public void setCustomerByName(Boolean customerByName) {
        this.customerByName = customerByName;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
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
                .append(", customerName=")
                .append(customerName)
                .append(", customerByName=")
                .append(customerByName)
                .append(", paymentAmount=")
                .append(paymentAmount)
                .append(']');
        return builder.toString();
    }

}
