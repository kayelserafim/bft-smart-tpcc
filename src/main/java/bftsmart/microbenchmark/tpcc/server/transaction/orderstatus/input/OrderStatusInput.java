package bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.input;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderStatusInput {

    @JsonProperty("w_id")
    private Integer warehouseId;
    @JsonProperty("d_id")
    private Integer districtId;
    @JsonProperty("c_id")
    private Integer customerId;
    @JsonProperty("c_by_name")
    private Boolean customerByName;
    @JsonProperty("c_name")
    private String customerName;

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Boolean getCustomerByName() {
        return customerByName;
    }

    public void setCustomerByName(Boolean customerByName) {
        this.customerByName = customerByName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
                .append(", customerName=")
                .append(customerName)
                .append(']');
        return builder.toString();
    }

}
