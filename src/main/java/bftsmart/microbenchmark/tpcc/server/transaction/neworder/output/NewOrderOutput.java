package bftsmart.microbenchmark.tpcc.server.transaction.neworder.output;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewOrderOutput {

    private LocalDateTime dateTime;
    private Integer warehouseId;
    private BigDecimal warehouseTax;
    private Integer districtId;
    private BigDecimal districtTax;
    private Integer orderId;
    private Integer customerId;
    private String customerLast;
    private String customerCredit;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private List<OrderLineOutput> orderLines;

    public NewOrderOutput(Builder builder) {
        this.dateTime = builder.dateTime;
        this.warehouseId = builder.warehouseId;
        this.warehouseTax = builder.warehouseTax.setScale(4, RoundingMode.HALF_UP);
        this.districtId = builder.districtId;
        this.districtTax = builder.districtTax.setScale(4, RoundingMode.HALF_UP);
        this.orderId = builder.orderId;
        this.customerId = builder.customerId;
        this.customerLast = builder.customerLast;
        this.customerCredit = builder.customerCredit;
        this.discount = builder.discount.setScale(4, RoundingMode.HALF_UP);
        this.totalAmount = builder.totalAmount.setScale(2, RoundingMode.HALF_UP);
        this.orderLines = builder.orderLines;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public BigDecimal getWarehouseTax() {
        return warehouseTax;
    }

    public void setWarehouseTax(BigDecimal warehouseTax) {
        this.warehouseTax = warehouseTax;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public BigDecimal getDistrictTax() {
        return districtTax;
    }

    public void setDistrictTax(BigDecimal districtTax) {
        this.districtTax = districtTax;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerLast() {
        return customerLast;
    }

    public void setCustomerLast(String customerLast) {
        this.customerLast = customerLast;
    }

    public String getCustomerCredit() {
        return customerCredit;
    }

    public void setCustomerCredit(String customerCredit) {
        this.customerCredit = customerCredit;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderLineOutput> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineOutput> orderLines) {
        this.orderLines = orderLines;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private LocalDateTime dateTime;
        private Integer warehouseId;
        private BigDecimal warehouseTax;
        private Integer districtId;
        private BigDecimal districtTax;
        private Integer orderId;
        private Integer customerId;
        private String customerLast;
        private String customerCredit;
        private BigDecimal discount;
        private BigDecimal totalAmount;
        private List<OrderLineOutput> orderLines;

        public Builder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder warehouseId(Integer warehouseId) {
            this.warehouseId = warehouseId;
            return this;
        }

        public Builder warehouseTax(BigDecimal warehouseTax) {
            this.warehouseTax = warehouseTax;
            return this;
        }

        public Builder districtId(Integer districtId) {
            this.districtId = districtId;
            return this;
        }

        public Builder districtTax(BigDecimal districtTax) {
            this.districtTax = districtTax;
            return this;
        }

        public Builder orderId(Integer orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder customerId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder customerLast(String customerLast) {
            this.customerLast = customerLast;
            return this;
        }

        public Builder customerCredit(String customerCredit) {
            this.customerCredit = customerCredit;
            return this;
        }

        public Builder discount(BigDecimal discount) {
            this.discount = discount;
            return this;
        }

        public Builder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder orderLines(OrderLineOutput orderLine) {
            if (orderLines == null) {
                orderLines = new ArrayList<>();
            }
            if (orderLine != null) {
                orderLines.add(orderLine);
            }
            return this;
        }

        public Builder orderLines(List<OrderLineOutput> orderLines) {
            this.orderLines = orderLines;
            return this;
        }

        public NewOrderOutput build() {
            return new NewOrderOutput(this);
        }

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NewOrderOutput [dateTime=")
                .append(dateTime)
                .append(", warehouseId=")
                .append(warehouseId)
                .append(", warehouseTax=")
                .append(warehouseTax)
                .append(", districtId=")
                .append(districtId)
                .append(", districtTax=")
                .append(districtTax)
                .append(", orderId=")
                .append(orderId)
                .append(", customerId=")
                .append(customerId)
                .append(", customerLast=")
                .append(customerLast)
                .append(", customerCredit=")
                .append(customerCredit)
                .append(", discount=")
                .append(discount)
                .append(", totalAmount=")
                .append(totalAmount)
                .append(", orderLines=")
                .append(orderLines)
                .append("]");
        return builder.toString();
    }

}
