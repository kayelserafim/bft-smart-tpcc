package bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.input;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderStatusInput implements Serializable {

    private static final long serialVersionUID = -236960185789250291L;

    @JsonProperty("w_id")
    private Integer warehouseId;
    @JsonProperty("d_id")
    private Integer districtId;
    @JsonProperty("c_id")
    private Integer customerId;
    @JsonProperty("c_by_name")
    private Boolean customerByName;
    @JsonProperty("c_name")
    private String customerLastName;

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public OrderStatusInput withWarehouseId(Integer warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public OrderStatusInput withDistrictId(Integer districtId) {
        setDistrictId(districtId);
        return this;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public OrderStatusInput withCustomerId(Integer customerId) {
        setCustomerId(customerId);
        return this;
    }

    public Boolean getCustomerByName() {
        return customerByName;
    }

    public void setCustomerByName(Boolean customerByName) {
        this.customerByName = customerByName;
    }

    public OrderStatusInput withCustomerByName(Boolean customerByName) {
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
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrderStatusInput [warehouseId=")
                .append(warehouseId)
                .append(", districtId=")
                .append(districtId)
                .append(", customerId=")
                .append(customerId)
                .append(", customerByName=")
                .append(customerByName)
                .append(", customerLastName=")
                .append(customerLastName)
                .append(']');
        return builder.toString();
    }

}
