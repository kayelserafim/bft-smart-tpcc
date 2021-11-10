package bftsmart.microbenchmark.tpcc.server.transaction.payment.output;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.Warehouse;

public class PaymentOutput {

    private final LocalDateTime dateTime;
    private final Integer warehouseId;
    private final String warehouseStreet1;
    private final String warehouseStreet2;
    private final String warehouseCity;
    private final String warehouseState;
    private final String warehouseZip;
    private final Integer districtId;
    private final String districtStreet1;
    private final String districtStreet2;
    private final String districtCity;
    private final String districtState;
    private final String districtZip;
    private final Integer customerId;
    private final String customerFirst;
    private final String customerMiddle;
    private final String customerLast;
    private final String customerStreet1;
    private final String customerStreet2;
    private final String customerCity;
    private final String customerState;
    private final String customerZip;
    private final Long customerSince;
    private final String customerCredit;
    private final BigDecimal customerDiscount;
    private final String customerPhone;
    private final BigDecimal amountPaid;
    private final BigDecimal customerCreditLimit;
    private final BigDecimal customerBalance;
    private final String customerData;

    public PaymentOutput(Builder builder) {
        this.dateTime = builder.dateTime;
        this.warehouseId = builder.warehouseId;
        this.warehouseStreet1 = builder.warehouseStreet1;
        this.warehouseStreet2 = builder.warehouseStreet2;
        this.warehouseCity = builder.warehouseCity;
        this.warehouseState = builder.warehouseState;
        this.warehouseZip = builder.warehouseZip;
        this.districtId = builder.districtId;
        this.districtStreet1 = builder.districtStreet1;
        this.districtStreet2 = builder.districtStreet2;
        this.districtCity = builder.districtCity;
        this.districtState = builder.districtState;
        this.districtZip = builder.districtZip;
        this.customerId = builder.customerId;
        this.customerFirst = builder.customerFirst;
        this.customerMiddle = builder.customerMiddle;
        this.customerLast = builder.customerLast;
        this.customerStreet1 = builder.customerStreet1;
        this.customerStreet2 = builder.customerStreet2;
        this.customerCity = builder.customerCity;
        this.customerState = builder.customerState;
        this.customerZip = builder.customerZip;
        this.customerSince = builder.customerSince;
        this.customerCredit = builder.customerCredit;
        this.customerDiscount = builder.customerDiscount.setScale(4, RoundingMode.HALF_UP);
        this.customerPhone = builder.customerPhone;
        this.amountPaid = builder.amountPaid.setScale(2, RoundingMode.HALF_UP);
        this.customerCreditLimit = builder.customerCreditLimit.setScale(2, RoundingMode.HALF_UP);
        this.customerBalance = builder.customerBalance.setScale(2, RoundingMode.HALF_UP);
        this.customerData = builder.customerData;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public String getWarehouseStreet1() {
        return warehouseStreet1;
    }

    public String getWarehouseStreet2() {
        return warehouseStreet2;
    }

    public String getWarehouseCity() {
        return warehouseCity;
    }

    public String getWarehouseState() {
        return warehouseState;
    }

    public String getWarehouseZip() {
        return warehouseZip;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public String getDistrictStreet1() {
        return districtStreet1;
    }

    public String getDistrictStreet2() {
        return districtStreet2;
    }

    public String getDistrictCity() {
        return districtCity;
    }

    public String getDistrictState() {
        return districtState;
    }

    public String getDistrictZip() {
        return districtZip;
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

    public String getCustomerStreet1() {
        return customerStreet1;
    }

    public String getCustomerStreet2() {
        return customerStreet2;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public String getCustomerState() {
        return customerState;
    }

    public String getCustomerZip() {
        return customerZip;
    }

    public Long getCustomerSince() {
        return customerSince;
    }

    public String getCustomerCredit() {
        return customerCredit;
    }

    public BigDecimal getCustomerDiscount() {
        return customerDiscount;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public BigDecimal getCustomerCreditLimit() {
        return customerCreditLimit;
    }

    public BigDecimal getCustomerBalance() {
        return customerBalance;
    }

    public String getCustomerData() {
        return customerData;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private LocalDateTime dateTime;
        private Integer warehouseId;
        private String warehouseStreet1;
        private String warehouseStreet2;
        private String warehouseCity;
        private String warehouseState;
        private String warehouseZip;
        private Integer districtId;
        private String districtStreet1;
        private String districtStreet2;
        private String districtCity;
        private String districtState;
        private String districtZip;
        private Integer customerId;
        private String customerFirst;
        private String customerMiddle;
        private String customerLast;
        private String customerStreet1;
        private String customerStreet2;
        private String customerCity;
        private String customerState;
        private String customerZip;
        private Long customerSince;
        private String customerCredit;
        private BigDecimal customerDiscount;
        private String customerPhone;
        private BigDecimal amountPaid;
        private BigDecimal customerCreditLimit;
        private BigDecimal customerBalance;
        private String customerData;

        public Builder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder warehouse(Warehouse warehouse) {
            warehouseId(warehouse.getWarehouseId());
            warehouseStreet1(warehouse.getStreet1());
            warehouseStreet2(warehouse.getStreet2());
            warehouseCity(warehouse.getCity());
            warehouseState(warehouse.getState());
            warehouseZip(warehouse.getZip());
            return this;
        }

        public Builder district(District district) {
            districtId(district.getDistrictId());
            districtStreet1(district.getStreet1());
            districtStreet2(district.getStreet2());
            districtCity(district.getCity());
            districtState(district.getState());
            districtZip(district.getZip());
            return this;
        }

        public Builder warehouseId(Integer warehouseId) {
            this.warehouseId = warehouseId;
            return this;
        }

        public Builder warehouseStreet1(String warehouseStreet1) {
            this.warehouseStreet1 = warehouseStreet1;
            return this;
        }

        public Builder warehouseStreet2(String warehouseStreet2) {
            this.warehouseStreet2 = warehouseStreet2;
            return this;
        }

        public Builder warehouseCity(String warehouseCity) {
            this.warehouseCity = warehouseCity;
            return this;
        }

        public Builder warehouseState(String warehouseState) {
            this.warehouseState = warehouseState;
            return this;
        }

        public Builder warehouseZip(String warehouseZip) {
            this.warehouseZip = warehouseZip;
            return this;
        }

        public Builder districtId(Integer districtId) {
            this.districtId = districtId;
            return this;
        }

        public Builder districtStreet1(String districtStreet1) {
            this.districtStreet1 = districtStreet1;
            return this;
        }

        public Builder districtStreet2(String districtStreet2) {
            this.districtStreet2 = districtStreet2;
            return this;
        }

        public Builder districtCity(String districtCity) {
            this.districtCity = districtCity;
            return this;
        }

        public Builder districtState(String districtState) {
            this.districtState = districtState;
            return this;
        }

        public Builder districtZip(String districtZip) {
            this.districtZip = districtZip;
            return this;
        }

        public Builder customer(Customer customer) {
            customerId(customer.getCustomerId());
            customerFirst(customer.getFirst());
            customerMiddle(customer.getMiddle());
            customerLast(customer.getLast());
            customerStreet1(customer.getStreet1());
            customerStreet2(customer.getStreet2());
            customerCity(customer.getCity());
            customerState(customer.getState());
            customerZip(customer.getState());
            customerSince(customer.getSince());
            customerCredit(customer.getCredit());
            customerDiscount(customer.getDiscount());
            customerPhone(customer.getPhone());
            customerCreditLimit(customer.getCreditLimit());
            customerBalance(customer.getBalance());
            customerData(customer.getData());
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

        public Builder customerStreet1(String customerStreet1) {
            this.customerStreet1 = customerStreet1;
            return this;
        }

        public Builder customerStreet2(String customerStreet2) {
            this.customerStreet2 = customerStreet2;
            return this;
        }

        public Builder customerCity(String customerCity) {
            this.customerCity = customerCity;
            return this;
        }

        public Builder customerState(String customerState) {
            this.customerState = customerState;
            return this;
        }

        public Builder customerZip(String customerZip) {
            this.customerZip = customerZip;
            return this;
        }

        public Builder customerSince(Long customerSince) {
            this.customerSince = customerSince;
            return this;
        }

        public Builder customerCredit(String customerCredit) {
            this.customerCredit = customerCredit;
            return this;
        }

        public Builder customerDiscount(BigDecimal customerDiscount) {
            this.customerDiscount = customerDiscount;
            return this;
        }

        public Builder customerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
            return this;
        }

        public Builder amountPaid(BigDecimal amountPaid) {
            this.amountPaid = amountPaid;
            return this;
        }

        public Builder customerCreditLimit(BigDecimal customerCreditLimit) {
            this.customerCreditLimit = customerCreditLimit;
            return this;
        }

        public Builder customerBalance(BigDecimal customerBalance) {
            this.customerBalance = customerBalance;
            return this;
        }

        public Builder customerData(String customerData) {
            this.customerData = customerData;
            return this;
        }

        public PaymentOutput build() {
            return new PaymentOutput(this);
        }
    }

}
