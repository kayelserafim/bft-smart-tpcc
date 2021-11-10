package bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.output;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderLineOutput {

    private final Integer supplierWarehouseId;
    private final Integer itemId;
    private final Integer orderQuantities;
    private final BigDecimal amount;
    private final Long deliveryDateTime;

    public OrderLineOutput(Builder builder) {
        this.supplierWarehouseId = builder.supplierWarehouseId;
        this.itemId = builder.itemId;
        this.orderQuantities = builder.orderQuantities;
        this.amount = builder.amount.setScale(2, RoundingMode.HALF_UP);
        this.deliveryDateTime = builder.deliveryDateTime;
    }

    public Integer getSupplierWarehouseId() {
        return supplierWarehouseId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getOrderQuantities() {
        return orderQuantities;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Integer supplierWarehouseId;
        private Integer itemId;
        private Integer orderQuantities;
        private BigDecimal amount;
        private Long deliveryDateTime;

        public Builder supplierWarehouseId(Integer supplierWarehouseId) {
            this.supplierWarehouseId = supplierWarehouseId;
            return this;
        }

        public Builder itemId(Integer itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder orderQuantities(Integer orderQuantities) {
            this.orderQuantities = orderQuantities;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder deliveryDateTime(Long deliveryDateTime) {
            this.deliveryDateTime = deliveryDateTime;
            return this;
        }

        public OrderLineOutput build() {
            return new OrderLineOutput(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrderLineOutput [supplierWarehouseId=")
                .append(supplierWarehouseId)
                .append(", itemId=")
                .append(itemId)
                .append(", orderQuantities=")
                .append(orderQuantities)
                .append(", amount=")
                .append(amount)
                .append(", deliveryDateTime=")
                .append(deliveryDateTime)
                .append("]");
        return builder.toString();
    }

}
