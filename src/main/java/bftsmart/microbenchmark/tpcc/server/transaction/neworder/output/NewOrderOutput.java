package bftsmart.microbenchmark.tpcc.server.transaction.neworder.output;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class NewOrderOutput {

    private final LocalDateTime dateTime;
    private final Integer warehouseId;
    private final BigDecimal warehouseTax;
    private final Integer districtId;
    private final BigDecimal districtTax;
    private final Integer orderId;
    private final Integer customerId;
    private final String customerLast;
    private final String customerCredit;
    private final BigDecimal discount;
    private final BigDecimal totalAmount;
    private final List<OrderLineOutput> orderLines;

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

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public BigDecimal getWarehouseTax() {
        return warehouseTax;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public BigDecimal getDistrictTax() {
        return districtTax;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getCustomerLast() {
        return customerLast;
    }

    public String getCustomerCredit() {
        return customerCredit;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public List<OrderLineOutput> getOrderLines() {
        return orderLines;
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

        public NewOrderOutput build() {
            return new NewOrderOutput(this);
        }

    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NewOrderOutput.class.getSimpleName() + "[", "]")
                .add("dateTime=" + dateTime)
                .add("warehouseId=" + warehouseId)
                .add("warehouseTax=" + warehouseTax)
                .add("districtId=" + districtId)
                .add("districtTax=" + districtTax)
                .add("orderId=" + orderId)
                .add("customerId=" + customerId)
                .add("customerLast='" + customerLast + "'")
                .add("customerCredit='" + customerCredit + "'")
                .add("discount=" + discount)
                .add("totalAmount=" + totalAmount)
                .add("orderLines=" + orderLines)
                .toString();
    }
}
