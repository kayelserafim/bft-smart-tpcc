package bftsmart.microbenchmark.tpcc.table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.javatuples.Tuple;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.ImmutableSet;

import bftsmart.microbenchmark.tpcc.probject.ModelType;
import bftsmart.microbenchmark.tpcc.probject.PRObject;

/**
 * <ul>
 * <li>Primary Key: (C_W_ID, C_D_ID, C_ID)</li>
 * <li>(C_W_ID, C_D_ID) Foreign Key, references (D_W_ID, D_ID)</li>
 * </ul>
 */
@JsonDeserialize(builder = Customer.Builder.class)
public class Customer implements PRObject {

    private static final long serialVersionUID = -3840013081887501896L;

    private static final AtomicInteger PAYMENT_CNT = new AtomicInteger();
    private static final AtomicInteger DELIVERY_CNT = new AtomicInteger();

    public static final ModelType MODEL_TYPE = ModelType.CUSTOMER;

    private final Tuple key;
    private final Set<Tuple> secondaryKeys;

    /**
     * c_id - 96,000 unique IDs. 3,000 are populated per district
     */
    @JsonProperty("c_id")
    private final Integer customerId;
    /**
     * c_d_id - 20 unique IDs
     */
    @JsonProperty("c_d_id")
    private final Integer districtId;
    /**
     * c_w_id - 2*W unique IDs
     */
    @JsonProperty("c_w_id")
    private final Integer warehouseId;
    /**
     * c_first - variable text, size 16
     */
    @JsonProperty("c_first")
    private final String first;
    /**
     * c_middle - fixed text, size 2
     */
    @JsonProperty("c_middle")
    private final String middle;
    /**
     * c_last - variable text, size 16
     */
    @JsonProperty("c_last")
    private final String last;
    /**
     * c_street_1 - variable text, size 20
     */
    @JsonProperty("c_street_1")
    private final String street1;
    /**
     * c_street_2 - variable text, size 20
     */
    @JsonProperty("c_street_2")
    private final String street2;
    /**
     * c_city - variable text, size 20
     */
    @JsonProperty("c_city")
    private final String city;
    /**
     * c_state - fixed text, size 2
     */
    @JsonProperty("c_state")
    private final String state;
    /**
     * c_zip - fixed text, size 9
     */
    @JsonProperty("c_zip")
    private final String zip;
    /**
     * c_phone - fixed text, size 16
     */
    @JsonProperty("c_phone")
    private final String phone;
    /**
     * c_since - date and time
     */
    @JsonProperty("c_since")
    private final Long since;
    /**
     * c_credit - fixed text, size 2 - "GC"=good, "BC"=bad
     */
    @JsonProperty("c_credit")
    private final String credit;
    /**
     * c_credit_lim - signed numeric(12, 2)
     */
    @JsonProperty("c_credit_lim")
    private final BigDecimal creditLimit;
    /**
     * c_discount - signed numeric(4, 4)
     */
    @JsonProperty("c_discount")
    private final BigDecimal discount;
    /**
     * c_balance - signed numeric(12, 2)
     */
    @JsonProperty("c_balance")
    private final BigDecimal balance;
    /**
     * c_ytd_payment - signed numeric(12, 2)
     */
    @JsonProperty("c_ytd_payment")
    private final BigDecimal yearToDateBalancePayment;
    /**
     * c_payment_cnt
     */
    @JsonProperty("c_payment_cnt")
    private final Integer paymentCnt;
    /**
     * c_delivery_cnt
     */
    @JsonProperty("c_delivery_cnt")
    private final Integer deliveryCnt;
    /**
     * c_data - variable text, size 500 - Miscellaneous information
     */
    @JsonProperty("c_data")
    private final String data;

    private Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.districtId = builder.districtId;
        this.warehouseId = builder.warehouseId;
        this.first = builder.first;
        this.middle = builder.middle;
        this.last = builder.last;
        this.street1 = builder.street1;
        this.street2 = builder.street2;
        this.city = builder.city;
        this.state = builder.state;
        this.zip = builder.zip;
        this.phone = builder.phone;
        this.since = builder.since;
        this.credit = builder.credit;
        this.creditLimit = builder.creditLimit.setScale(2, RoundingMode.HALF_UP);
        this.discount = builder.discount.setScale(4, RoundingMode.HALF_UP);
        this.balance = builder.balance.setScale(2, RoundingMode.HALF_UP);
        this.yearToDateBalancePayment = builder.yearToDateBalancePayment.setScale(2, RoundingMode.HALF_UP);
        this.paymentCnt = builder.paymentCnt;
        this.deliveryCnt = builder.deliveryCnt;
        this.data = builder.data;
        this.key = key(warehouseId, districtId, customerId);
        this.secondaryKeys = ImmutableSet.of(districtKey(warehouseId, districtId));
    }

    @Override
    public Tuple getKey() {
        return key;
    }

    @Override
    public Set<Tuple> getSecondaryKeys() {
        return secondaryKeys;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public String getFirst() {
        return first;
    }

    public String getMiddle() {
        return middle;
    }

    public String getLast() {
        return last;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    public Long getSince() {
        return since;
    }

    public String getCredit() {
        return credit;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getYearToDateBalancePayment() {
        return yearToDateBalancePayment;
    }

    public Integer getPaymentCnt() {
        return paymentCnt;
    }

    public Integer getDeliveryCnt() {
        return deliveryCnt;
    }

    public String getData() {
        return data;
    }

    public static Tuple key(Integer warehouseId, Integer districtId, Integer customerId) {
        return Quartet.with(MODEL_TYPE, warehouseId, districtId, customerId);
    }

    public static Tuple districtKey(Integer warehouseId, Integer districtId) {
        return Triplet.with(MODEL_TYPE, warehouseId, districtId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder from(Customer customer) {
        return new Builder(customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        return Objects.equals(key, other.key);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Customer.class.getSimpleName() + "[", "]").add("key=" + key)
                .add("secondaryKeys=" + secondaryKeys)
                .add("customerId=" + customerId)
                .add("districtId=" + districtId)
                .add("warehouseId=" + warehouseId)
                .add("first='" + first + "'")
                .add("middle='" + middle + "'")
                .add("last='" + last + "'")
                .add("street1='" + street1 + "'")
                .add("street2='" + street2 + "'")
                .add("city='" + city + "'")
                .add("state='" + state + "'")
                .add("zip='" + zip + "'")
                .add("phone='" + phone + "'")
                .add("since=" + since)
                .add("credit='" + credit + "'")
                .add("creditLimit=" + creditLimit)
                .add("discount=" + discount)
                .add("balance=" + balance)
                .add("yearToDateBalancePayment=" + yearToDateBalancePayment)
                .add("paymentCnt=" + paymentCnt)
                .add("deliveryCnt=" + deliveryCnt)
                .add("data='" + data + "'")
                .toString();
    }

    @JsonPOJOBuilder
    public static class Builder {
        @JsonProperty("c_id")
        private Integer customerId;
        @JsonProperty("c_d_id")
        private Integer districtId;
        @JsonProperty("c_w_id")
        private Integer warehouseId;
        @JsonProperty("c_first")
        private String first;
        @JsonProperty("c_middle")
        private String middle;
        @JsonProperty("c_last")
        private String last;
        @JsonProperty("c_street_1")
        private String street1;
        @JsonProperty("c_street_2")
        private String street2;
        @JsonProperty("c_city")
        private String city;
        @JsonProperty("c_state")
        private String state;
        @JsonProperty("c_zip")
        private String zip;
        @JsonProperty("c_phone")
        private String phone;
        @JsonProperty("c_since")
        private Long since;
        @JsonProperty("c_credit")
        private String credit;
        @JsonProperty("c_credit_lim")
        private BigDecimal creditLimit;
        @JsonProperty("c_discount")
        private BigDecimal discount;
        @JsonProperty("c_balance")
        private BigDecimal balance;
        @JsonProperty("c_ytd_payment")
        private BigDecimal yearToDateBalancePayment;
        @JsonProperty("c_payment_cnt")
        private Integer paymentCnt;
        @JsonProperty("c_delivery_cnt")
        private Integer deliveryCnt;
        @JsonProperty("c_data")
        private String data;

        public Builder() {
            super();
        }

        public Builder(Customer customer) {
            this.customerId = customer.customerId;
            this.districtId = customer.districtId;
            this.warehouseId = customer.warehouseId;
            this.first = customer.first;
            this.middle = customer.middle;
            this.last = customer.last;
            this.street1 = customer.street1;
            this.street2 = customer.street2;
            this.city = customer.city;
            this.state = customer.state;
            this.zip = customer.zip;
            this.phone = customer.phone;
            this.since = customer.since;
            this.credit = customer.credit;
            this.creditLimit = customer.creditLimit;
            this.discount = customer.discount;
            this.balance = customer.balance;
            this.yearToDateBalancePayment = customer.yearToDateBalancePayment;
            this.paymentCnt = customer.paymentCnt;
            this.deliveryCnt = customer.deliveryCnt;
            this.data = customer.data;
        }

        public Builder customerId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder districtId(Integer districtId) {
            this.districtId = districtId;
            return this;
        }

        public Builder warehouseId(Integer warehouseId) {
            this.warehouseId = warehouseId;
            return this;
        }

        public Builder first(String first) {
            this.first = first;
            return this;
        }

        public Builder middle(String middle) {
            this.middle = middle;
            return this;
        }

        public Builder last(String last) {
            this.last = last;
            return this;
        }

        public Builder street1(String street1) {
            this.street1 = street1;
            return this;
        }

        public Builder street2(String street2) {
            this.street2 = street2;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder zip(String zip) {
            this.zip = zip;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder since(Long since) {
            this.since = since;
            return this;
        }

        public Builder credit(String credit) {
            this.credit = credit;
            return this;
        }

        public Builder creditLimit(BigDecimal creditLimit) {
            this.creditLimit = creditLimit;
            return this;
        }

        public Builder discount(BigDecimal discount) {
            this.discount = discount;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder addBalance(BigDecimal amount) {
            this.balance = Optional.ofNullable(balance).orElse(BigDecimal.ZERO).add(amount);
            return this;
        }

        public Builder subtractBalance(BigDecimal amount) {
            this.balance = Optional.ofNullable(balance).orElse(BigDecimal.ZERO).subtract(amount);
            return this;
        }

        public Builder yearToDateBalancePayment(BigDecimal yearToDateBalancePayment) {
            this.yearToDateBalancePayment = yearToDateBalancePayment;
            return this;
        }

        public Builder paymentCnt(Integer paymentCnt) {
            this.paymentCnt = paymentCnt;
            return this;
        }

        public Builder paymentCntIncrement() {
            this.paymentCnt = PAYMENT_CNT.incrementAndGet();
            return this;
        }

        public Builder deliveryCnt(Integer deliveryCnt) {
            this.deliveryCnt = deliveryCnt;
            return this;
        }

        public Builder deliveryCntIncrement() {
            this.deliveryCnt = DELIVERY_CNT.incrementAndGet();
            return this;
        }

        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }

    }

}
