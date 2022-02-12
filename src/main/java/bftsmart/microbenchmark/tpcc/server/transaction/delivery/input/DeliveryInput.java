package bftsmart.microbenchmark.tpcc.server.transaction.delivery.input;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeliveryInput implements Serializable {

    private static final long serialVersionUID = -2409304413457285547L;

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

    public DeliveryInput withWarehouseId(Integer warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public Integer getOrderCarrierId() {
        return orderCarrierId;
    }

    public void setOrderCarrierId(Integer orderCarrierId) {
        this.orderCarrierId = orderCarrierId;
    }

    public DeliveryInput withOrderCarrierId(Integer orderCarrierId) {
        setOrderCarrierId(orderCarrierId);
        return this;
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
