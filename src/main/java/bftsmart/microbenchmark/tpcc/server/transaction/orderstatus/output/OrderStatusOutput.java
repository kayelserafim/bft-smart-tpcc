package bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.output;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusOutput {

    private final LocalDateTime dateTime;
    private final Integer warehouseId;
    private final Integer districtId;
    private final Integer customerId;
    private final String customerFirst;
    private final String customerMiddle;
    private final String customerLast;
    private final BigDecimal customerBalance;
    private final Integer orderId;
    private final Long entryDate;
    private final Integer carrierId;
    private final List<OrderLineOutput> orderLines;

    public OrderStatusOutput(Builder builder) {
        this.dateTime = builder.dateTime;
        this.warehouseId = builder.warehouseId;
        this.districtId = builder.districtId;
        this.customerId = builder.customerId;
        this.customerFirst = builder.customerFirst;
        this.customerMiddle = builder.customerMiddle;
        this.customerLast = builder.customerLast;
        this.customerBalance = builder.customerBalance.setScale(2, RoundingMode.HALF_UP);
        this.orderId = builder.orderId;
        this.entryDate = builder.entryDate;
        this.carrierId = builder.carrierId;
        this.orderLines = builder.orderLines;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getCustomerFirst() {
        return customerFirst;
    }

    public String getCustomerMiddle() {
        return customerMiddle;
    }

    public String getCustomerLast() {
        return customerLast;
    }

    public BigDecimal getCustomerBalance() {
        return customerBalance;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Long getEntryDate() {
        return entryDate;
    }

    public Integer getCarrierId() {
        return carrierId;
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
        private Integer districtId;
        private Integer customerId;
        private String customerFirst;
        private String customerMiddle;
        private String customerLast;
        private BigDecimal customerBalance;
        private Integer orderId;
        private Long entryDate;
        private Integer carrierId;
        private List<OrderLineOutput> orderLines;

        public Builder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder warehouseId(Integer warehouseId) {
            this.warehouseId = warehouseId;
            return this;
        }

        public Builder districtId(Integer districtId) {
            this.districtId = districtId;
            return this;
        }

        public Builder customerId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder customerFirst(String customerFirst) {
            this.customerFirst = customerFirst;
            return this;
        }

        public Builder customerMiddle(String customerMiddle) {
            this.customerMiddle = customerMiddle;
            return this;
        }

        public Builder customerLast(String customerLast) {
            this.customerLast = customerLast;
            return this;
        }

        public Builder customerBalance(BigDecimal customerBalance) {
            this.customerBalance = customerBalance;
            return this;
        }

        public Builder orderId(Integer orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder entryDate(Long entryDate) {
            this.entryDate = entryDate;
            return this;
        }

        public Builder carrierId(Integer carrierId) {
            this.carrierId = carrierId;
            return this;
        }

        public Builder orderLine(OrderLineOutput orderLine) {
            if (orderLines == null) {
                orderLines = new ArrayList<>();
            }
            if (orderLine != null) {
                orderLines.add(orderLine);
            }
            return this;
        }

        public OrderStatusOutput build() {
            return new OrderStatusOutput(this);
        }

    }

}
