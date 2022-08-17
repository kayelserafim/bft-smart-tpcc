package bftsmart.microbenchmark.tpcc.table;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import bftsmart.microbenchmark.tpcc.table.key.CustomerKey;

/**
 * <ul>
 * <li>Primary Key: (C_W_ID, C_D_ID, C_ID)</li>
 * <li>(C_W_ID, C_D_ID) Foreign Key, references (D_W_ID, D_ID)</li>
 * </ul>
 */
public class Customer implements Externalizable {

    private static final AtomicInteger PAYMENT_CNT = new AtomicInteger();
    private static final AtomicInteger DELIVERY_CNT = new AtomicInteger();

    public static final TableType TABLE_TYPE = TableType.CUSTOMER;

    /**
     * c_id - 96,000 unique IDs. 3,000 are populated per district
     */
    @JsonProperty("c_id")
    private int customerId;
    /**
     * c_d_id - 20 unique IDs
     */
    @JsonProperty("c_d_id")
    private int districtId;
    /**
     * c_w_id - 2*W unique IDs
     */
    @JsonProperty("c_w_id")
    private int warehouseId;
    /**
     * c_first - variable text, size 16
     */
    @JsonProperty("c_first")
    private String first;
    /**
     * c_middle - fixed text, size 2
     */
    @JsonProperty("c_middle")
    private String middle;
    /**
     * c_last - variable text, size 16
     */
    @JsonProperty("c_last")
    private String last;
    /**
     * c_street_1 - variable text, size 20
     */
    @JsonProperty("c_street_1")
    private String street1;
    /**
     * c_street_2 - variable text, size 20
     */
    @JsonProperty("c_street_2")
    private String street2;
    /**
     * c_city - variable text, size 20
     */
    @JsonProperty("c_city")
    private String city;
    /**
     * c_state - fixed text, size 2
     */
    @JsonProperty("c_state")
    private String state;
    /**
     * c_zip - fixed text, size 9
     */
    @JsonProperty("c_zip")
    private String zip;
    /**
     * c_phone - fixed text, size 16
     */
    @JsonProperty("c_phone")
    private String phone;
    /**
     * c_since - date and time
     */
    @JsonProperty("c_since")
    private long since;
    /**
     * c_credit - fixed text, size 2 - "GC"=good, "BC"=bad
     */
    @JsonProperty("c_credit")
    private String credit;
    /**
     * c_credit_lim - signed numeric(12, 2)
     */
    @JsonProperty("c_credit_lim")
    private BigDecimal creditLimit;
    /**
     * c_discount - signed numeric(4, 4)
     */
    @JsonProperty("c_discount")
    private BigDecimal discount;
    /**
     * c_balance - signed numeric(12, 2)
     */
    @JsonProperty("c_balance")
    private BigDecimal balance;
    /**
     * c_ytd_payment - signed numeric(12, 2)
     */
    @JsonProperty("c_ytd_payment")
    private BigDecimal yearToDateBalancePayment;
    /**
     * c_payment_cnt
     */
    @JsonProperty("c_payment_cnt")
    private int paymentCnt;
    /**
     * c_delivery_cnt
     */
    @JsonProperty("c_delivery_cnt")
    private int deliveryCnt;
    /**
     * c_data - variable text, size 500 - Miscellaneous information
     */
    @JsonProperty("c_data")
    private String data;

    public CustomerKey createKey() {
        return new CustomerKey(warehouseId, districtId, customerId);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Customer withCustomerId(int customerId) {
        setCustomerId(customerId);
        return this;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public Customer withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Customer withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public Customer withFirst(String first) {
        setFirst(first);
        return this;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public Customer withMiddle(String middle) {
        setMiddle(middle);
        return this;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Customer withLast(String last) {
        setLast(last);
        return this;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public Customer withStreet1(String street1) {
        setStreet1(street1);
        return this;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public Customer withStreet2(String street2) {
        setStreet2(street2);
        return this;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Customer withCity(String city) {
        setCity(city);
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Customer withState(String state) {
        setState(state);
        return this;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Customer withZip(String zip) {
        setZip(zip);
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Customer withPhone(String phone) {
        setPhone(phone);
        return this;
    }

    public long getSince() {
        return since;
    }

    public void setSince(long since) {
        this.since = since;
    }

    public Customer withSince(long since) {
        setSince(since);
        return this;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public Customer withCredit(String credit) {
        setCredit(credit);
        return this;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Customer withCreditLimit(BigDecimal creditLimit) {
        setCreditLimit(creditLimit);
        return this;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Customer withDiscount(BigDecimal discount) {
        setDiscount(discount);
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Customer withBalance(BigDecimal balance) {
        setBalance(balance);
        return this;
    }

    public BigDecimal getYearToDateBalancePayment() {
        return yearToDateBalancePayment;
    }

    public void setYearToDateBalancePayment(BigDecimal yearToDateBalancePayment) {
        this.yearToDateBalancePayment = yearToDateBalancePayment;
    }

    public Customer withYearToDateBalancePayment(BigDecimal yearToDateBalancePayment) {
        setYearToDateBalancePayment(yearToDateBalancePayment);
        return this;
    }

    public int getPaymentCnt() {
        return paymentCnt;
    }

    public void setPaymentCnt(int paymentCnt) {
        this.paymentCnt = paymentCnt;
    }

    public Customer withPaymentCnt(int paymentCnt) {
        setPaymentCnt(paymentCnt);
        return this;
    }

    public int getDeliveryCnt() {
        return deliveryCnt;
    }

    public void setDeliveryCnt(int deliveryCnt) {
        this.deliveryCnt = deliveryCnt;
    }

    public Customer withDeliveryCnt(int deliveryCnt) {
        setDeliveryCnt(deliveryCnt);
        return this;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Customer withData(String data) {
        setData(data);
        return this;
    }

    public void addBalance(BigDecimal amount) {
        this.balance = Optional.ofNullable(balance).orElse(BigDecimal.ZERO).add(amount);
    }

    public Customer paymentCntIncrement() {
        this.paymentCnt = PAYMENT_CNT.incrementAndGet();
        return this;
    }

    public Customer deliveryCntIncrement() {
        this.deliveryCnt = DELIVERY_CNT.incrementAndGet();
        return this;
    }

    public Customer subtractBalance(BigDecimal amount) {
        this.balance = Optional.ofNullable(balance).orElse(BigDecimal.ZERO).subtract(amount);
        return this;
    }

    @Override
    public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException {
        this.customerId = in.readInt();
        this.districtId = in.readInt();
        this.warehouseId = in.readInt();
        this.first = in.readUTF();
        this.middle = in.readUTF();
        this.last = in.readUTF();
        this.street1 = in.readUTF();
        this.street2 = in.readUTF();
        this.city = in.readUTF();
        this.state = in.readUTF();
        this.zip = in.readUTF();
        this.phone = in.readUTF();
        this.since = in.readLong();
        this.credit = in.readUTF();
        this.creditLimit = BigDecimal.valueOf(in.readDouble());
        this.discount = BigDecimal.valueOf(in.readDouble());
        this.balance = BigDecimal.valueOf(in.readDouble());
        this.yearToDateBalancePayment = BigDecimal.valueOf(in.readDouble());
        this.paymentCnt = in.readInt();
        this.deliveryCnt = in.readInt();
        this.data = in.readUTF();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(customerId);
        out.writeInt(districtId);
        out.writeInt(warehouseId);
        out.writeUTF(first);
        out.writeUTF(middle);
        out.writeUTF(last);
        out.writeUTF(street1);
        out.writeUTF(street2);
        out.writeUTF(city);
        out.writeUTF(state);
        out.writeUTF(zip);
        out.writeUTF(phone);
        out.writeLong(since);
        out.writeUTF(credit);
        out.writeDouble(creditLimit.doubleValue());
        out.writeDouble(discount.doubleValue());
        out.writeDouble(balance.doubleValue());
        out.writeDouble(yearToDateBalancePayment.doubleValue());
        out.writeInt(paymentCnt);
        out.writeInt(deliveryCnt);
        out.writeUTF(data);
    }

    

}
