package bftsmart.microbenchmark.tpcc.server.transaction.neworder.output;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderLineOutput {

    private Integer supplierWarehouseId;
    private Integer itemId;
    private String itemName;
    private Integer orderQuantities;
    private Integer stockQuantities;
    private Character brandGeneric;
    private BigDecimal itemPrice;
    private BigDecimal orderLineAmounts;

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

    public void setSupplierWarehouseId(Integer supplierWarehouseId) {
        this.supplierWarehouseId = supplierWarehouseId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getOrderQuantities() {
        return orderQuantities;
    }

    public void setOrderQuantities(Integer orderQuantities) {
        this.orderQuantities = orderQuantities;
    }

    public Integer getStockQuantities() {
        return stockQuantities;
    }

    public void setStockQuantities(Integer stockQuantities) {
        this.stockQuantities = stockQuantities;
    }

    public Character getBrandGeneric() {
        return brandGeneric;
    }

    public void setBrandGeneric(Character brandGeneric) {
        this.brandGeneric = brandGeneric;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public BigDecimal getOrderLineAmounts() {
        return orderLineAmounts;
    }

    public void setOrderLineAmounts(BigDecimal orderLineAmounts) {
        this.orderLineAmounts = orderLineAmounts;
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
        StringBuilder builder = new StringBuilder();
        builder.append("OrderLineOutput [supplierWarehouseId=")
                .append(supplierWarehouseId)
                .append(", itemId=")
                .append(itemId)
                .append(", itemName=")
                .append(itemName)
                .append(", orderQuantities=")
                .append(orderQuantities)
                .append(", stockQuantities=")
                .append(stockQuantities)
                .append(", brandGeneric=")
                .append(brandGeneric)
                .append(", itemPrice=")
                .append(itemPrice)
                .append(", orderLineAmounts=")
                .append(orderLineAmounts)
                .append("]");
        return builder.toString();
    }

}
