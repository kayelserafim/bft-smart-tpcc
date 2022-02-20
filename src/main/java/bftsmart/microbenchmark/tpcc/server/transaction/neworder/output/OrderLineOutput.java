package bftsmart.microbenchmark.tpcc.server.transaction.neworder.output;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.StringJoiner;

public class OrderLineOutput {

    private final Integer supplierWarehouseId;
    private final Integer itemId;
    private final String itemName;
    private final Integer orderQuantities;
    private final Integer stockQuantities;
    private final Character brandGeneric;
    private final BigDecimal itemPrice;
    private final BigDecimal orderLineAmounts;

    public OrderLineOutput(Builder builder) {
        this.supplierWarehouseId = builder.supplierWarehouseId;
        this.itemId = builder.itemId;
        this.itemName = builder.itemName;
        this.orderQuantities = builder.orderQuantities;
        this.stockQuantities = builder.stockQuantities;
        this.brandGeneric = builder.brandGeneric;
        this.itemPrice = builder.itemPrice.setScale(2, RoundingMode.HALF_UP);
        this.orderLineAmounts = builder.orderLineAmounts.setScale(2, RoundingMode.HALF_UP);
    }

    public Integer getSupplierWarehouseId() {
        return supplierWarehouseId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getOrderQuantities() {
        return orderQuantities;
    }

    public Integer getStockQuantities() {
        return stockQuantities;
    }

    public Character getBrandGeneric() {
        return brandGeneric;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public BigDecimal getOrderLineAmounts() {
        return orderLineAmounts;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Integer supplierWarehouseId;
        private Integer itemId;
        private String itemName;
        private Integer orderQuantities;
        private Integer stockQuantities;
        private Character brandGeneric;
        private BigDecimal itemPrice;
        private BigDecimal orderLineAmounts;

        public Builder supplierWarehouseId(Integer supplierWarehouseId) {
            this.supplierWarehouseId = supplierWarehouseId;
            return this;
        }

        public Builder itemId(Integer itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public Builder orderQuantities(Integer orderQuantities) {
            this.orderQuantities = orderQuantities;
            return this;
        }

        public Builder stockQuantities(Integer stockQuantities) {
            this.stockQuantities = stockQuantities;
            return this;
        }

        public Builder brandGeneric(Character brandGeneric) {
            this.brandGeneric = brandGeneric;
            return this;
        }

        public Builder itemPrice(BigDecimal itemPrice) {
            this.itemPrice = itemPrice;
            return this;
        }

        public Builder orderLineAmounts(BigDecimal orderLineAmounts) {
            this.orderLineAmounts = orderLineAmounts;
            return this;
        }

        public OrderLineOutput build() {
            return new OrderLineOutput(this);
        }

    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrderLineOutput.class.getSimpleName() + "[", "]")
                .add("supplierWarehouseId=" + supplierWarehouseId)
                .add("itemId=" + itemId)
                .add("itemName='" + itemName + "'")
                .add("orderQuantities=" + orderQuantities)
                .add("stockQuantities=" + stockQuantities)
                .add("brandGeneric=" + brandGeneric)
                .add("itemPrice=" + itemPrice)
                .add("orderLineAmounts=" + orderLineAmounts)
                .toString();
    }
}
