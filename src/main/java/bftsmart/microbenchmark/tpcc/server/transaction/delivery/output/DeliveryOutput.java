package bftsmart.microbenchmark.tpcc.server.transaction.delivery.output;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeliveryOutput {

    private final LocalDateTime dateTime;
    private final Integer warehouseId;
    private final Integer orderCarrierId;
    private final List<OrderOutput> orderIds;

    private DeliveryOutput(Builder builder) {
        this.dateTime = builder.dateTime;
        this.warehouseId = builder.warehouseId;
        this.orderCarrierId = builder.orderCarrierId;
        this.orderIds = builder.orderIds;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public Integer getOrderCarrierId() {
        return orderCarrierId;
    }

    public List<OrderOutput> getOrderIds() {
        return orderIds;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private LocalDateTime dateTime;
        private Integer warehouseId;
        private Integer orderCarrierId;
        private List<OrderOutput> orderIds;

        public Builder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder warehouseId(Integer warehouseId) {
            this.warehouseId = warehouseId;
            return this;
        }

        public Builder orderCarrierId(Integer orderCarrierId) {
            this.orderCarrierId = orderCarrierId;
            return this;
        }

        public Builder orderId(OrderOutput orderId) {
            if (orderIds == null) {
                orderIds = new ArrayList<>();
            }
            orderIds.add(orderId);
            return this;
        }

        public Builder orderIds(List<OrderOutput> orderIds) {
            this.orderIds = orderIds;
            return this;
        }

        public DeliveryOutput build() {
            return new DeliveryOutput(this);
        }

    }

}
