package bftsmart.microbenchmark.tpcc.server.transaction.delivery.input;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeliveryInput {

    @JsonProperty("w_id")
    private Integer warehouseId;
    @JsonProperty("o_carrier_id")
    private Integer orderCarrierId;

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getOrderCarrierId() {
        return orderCarrierId;
    }

    public void setOrderCarrierId(Integer orderCarrierId) {
        this.orderCarrierId = orderCarrierId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DeliveryInput [warehouseId=")
                .append(warehouseId)
                .append(", orderCarrierId=")
                .append(orderCarrierId)
                .append(']');
        return builder.toString();
    }

}
