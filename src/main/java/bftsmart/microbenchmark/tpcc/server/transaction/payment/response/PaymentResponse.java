package bftsmart.microbenchmark.tpcc.server.transaction.payment.response;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.Warehouse;

public class PaymentResponse implements KryoSerializable {

    private long dateTime;
    private int warehouseId;
    private String warehouseStreet1;
    private String warehouseStreet2;
    private String warehouseCity;
    private String warehouseState;
    private String warehouseZip;
    private int districtId;
    private String districtStreet1;
    private String districtStreet2;
    private String districtCity;
    private String districtState;
    private String districtZip;
    private int customerId;
    private String customerFirst;
    private String customerMiddle;
    private String customerLast;
    private String customerStreet1;
    private String customerStreet2;
    private String customerCity;
    private String customerState;
    private String customerZip;
    private long customerSince;
    private String customerCredit;
    private double customerDiscount;
    private String customerPhone;
    private double amountPaid;
    private double customerCreditLimit;
    private double customerBalance;
    private String customerData;

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public PaymentResponse withDateTime(long dateTime) {
        setDateTime(dateTime);
        return this;
    }

    public PaymentResponse withDateTime(LocalDateTime dateTime) {
        setDateTime(dateTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli());
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public PaymentResponse withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public PaymentResponse warehouse(Warehouse warehouse) {
        setWarehouseId(warehouse.getWarehouseId());
        setWarehouseStreet1(warehouse.getStreet1());
        setWarehouseStreet2(warehouse.getStreet2());
        setWarehouseCity(warehouse.getCity());
        setWarehouseState(warehouse.getState());
        setWarehouseZip(warehouse.getZip());
        return this;
    }

    public String getWarehouseStreet1() {
        return warehouseStreet1;
    }

    public void setWarehouseStreet1(String warehouseStreet1) {
        this.warehouseStreet1 = warehouseStreet1;
    }

    public PaymentResponse withWarehouseStreet1(String warehouseStreet1) {
        setWarehouseStreet1(warehouseStreet1);
        return this;
    }

    public String getWarehouseStreet2() {
        return warehouseStreet2;
    }

    public void setWarehouseStreet2(String warehouseStreet2) {
        this.warehouseStreet2 = warehouseStreet2;
    }

    public PaymentResponse withWarehouseStreet2(String warehouseStreet2) {
        setWarehouseStreet2(warehouseStreet2);
        return this;
    }

    public String getWarehouseCity() {
        return warehouseCity;
    }

    public void setWarehouseCity(String warehouseCity) {
        this.warehouseCity = warehouseCity;
    }

    public PaymentResponse withWarehouseCity(String warehouseCity) {
        setWarehouseCity(warehouseCity);
        return this;
    }

    public String getWarehouseState() {
        return warehouseState;
    }

    public void setWarehouseState(String warehouseState) {
        this.warehouseState = warehouseState;
    }

    public PaymentResponse withWarehouseState(String warehouseState) {
        setWarehouseState(warehouseState);
        return this;
    }

    public String getWarehouseZip() {
        return warehouseZip;
    }

    public void setWarehouseZip(String warehouseZip) {
        this.warehouseZip = warehouseZip;
    }

    public PaymentResponse withWarehouseZip(String warehouseZip) {
        setWarehouseZip(warehouseZip);
        return this;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public PaymentResponse withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public PaymentResponse district(District district) {
        setDistrictId(district.getDistrictId());
        setDistrictStreet1(district.getStreet1());
        setDistrictStreet2(district.getStreet2());
        setDistrictCity(district.getCity());
        setDistrictState(district.getState());
        setDistrictZip(district.getZip());
        return this;
    }

    public String getDistrictStreet1() {
        return districtStreet1;
    }

    public void setDistrictStreet1(String districtStreet1) {
        this.districtStreet1 = districtStreet1;
    }

    public PaymentResponse withDistrictStreet1(String districtStreet1) {
        setDistrictStreet1(districtStreet1);
        return this;
    }

    public String getDistrictStreet2() {
        return districtStreet2;
    }

    public void setDistrictStreet2(String districtStreet2) {
        this.districtStreet2 = districtStreet2;
    }

    public PaymentResponse withDistrictStreet2(String districtStreet2) {
        setDistrictStreet2(districtStreet2);
        return this;
    }

    public String getDistrictCity() {
        return districtCity;
    }

    public void setDistrictCity(String districtCity) {
        this.districtCity = districtCity;
    }

    public PaymentResponse withDistrictCity(String districtCity) {
        setDistrictCity(districtCity);
        return this;
    }

    public String getDistrictState() {
        return districtState;
    }

    public void setDistrictState(String districtState) {
        this.districtState = districtState;
    }

    public PaymentResponse withDistrictState(String districtState) {
        setDistrictState(districtState);
        return this;
    }

    public String getDistrictZip() {
        return districtZip;
    }

    public void setDistrictZip(String districtZip) {
        this.districtZip = districtZip;
    }

    public PaymentResponse withDistrictZip(String districtZip) {
        setDistrictZip(districtZip);
        return this;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public PaymentResponse withCustomerId(int customerId) {
        setCustomerId(customerId);
        return this;
    }

    public PaymentResponse customer(Customer customer) {
        setCustomerId(customer.getCustomerId());
        setCustomerFirst(customer.getFirst());
        setCustomerMiddle(customer.getMiddle());
        setCustomerLast(customer.getLast());
        setCustomerStreet1(customer.getStreet1());
        setCustomerStreet2(customer.getStreet2());
        setCustomerCity(customer.getCity());
        setCustomerState(customer.getState());
        setCustomerZip(customer.getState());
        setCustomerSince(customer.getSince());
        setCustomerCredit(customer.getCredit());
        setCustomerDiscount(customer.getDiscount().setScale(4, RoundingMode.HALF_UP).doubleValue());
        setCustomerPhone(customer.getPhone());
        setCustomerCreditLimit(customer.getCreditLimit().setScale(2, RoundingMode.HALF_UP).doubleValue());
        setCustomerBalance(customer.getBalance().setScale(2, RoundingMode.HALF_UP).doubleValue());
        setCustomerData(customer.getData());
        return this;
    }

    public String getCustomerFirst() {
        return customerFirst;
    }

    public void setCustomerFirst(String customerFirst) {
        this.customerFirst = customerFirst;
    }

    public PaymentResponse withCustomerFirst(String customerFirst) {
        setCustomerFirst(customerFirst);
        return this;
    }

    public String getCustomerMiddle() {
        return customerMiddle;
    }

    public void setCustomerMiddle(String customerMiddle) {
        this.customerMiddle = customerMiddle;
    }

    public PaymentResponse withCustomerMiddle(String customerMiddle) {
        setCustomerMiddle(customerMiddle);
        return this;
    }

    public String getCustomerLast() {
        return customerLast;
    }

    public void setCustomerLast(String customerLast) {
        this.customerLast = customerLast;
    }

    public PaymentResponse withCustomerLast(String customerLast) {
        setCustomerLast(customerLast);
        return this;
    }

    public String getCustomerStreet1() {
        return customerStreet1;
    }

    public void setCustomerStreet1(String customerStreet1) {
        this.customerStreet1 = customerStreet1;
    }

    public PaymentResponse withCustomerStreet1(String customerStreet1) {
        setCustomerStreet1(customerStreet1);
        return this;
    }

    public String getCustomerStreet2() {
        return customerStreet2;
    }

    public void setCustomerStreet2(String customerStreet2) {
        this.customerStreet2 = customerStreet2;
    }

    public PaymentResponse withCustomerStreet2(String customerStreet2) {
        setCustomerStreet2(customerStreet2);
        return this;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public PaymentResponse withCustomerCity(String customerCity) {
        setCustomerCity(customerCity);
        return this;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public PaymentResponse withCustomerState(String customerState) {
        setCustomerState(customerState);
        return this;
    }

    public String getCustomerZip() {
        return customerZip;
    }

    public void setCustomerZip(String customerZip) {
        this.customerZip = customerZip;
    }

    public PaymentResponse withCustomerZip(String customerZip) {
        setCustomerZip(customerZip);
        return this;
    }

    public long getCustomerSince() {
        return customerSince;
    }

    public void setCustomerSince(long customerSince) {
        this.customerSince = customerSince;
    }

    public PaymentResponse withCustomerSince(long customerSince) {
        setCustomerSince(customerSince);
        return this;
    }

    public String getCustomerCredit() {
        return customerCredit;
    }

    public void setCustomerCredit(String customerCredit) {
        this.customerCredit = customerCredit;
    }

    public PaymentResponse withCustomerCredit(String customerCredit) {
        setCustomerCredit(customerCredit);
        return this;
    }

    public double getCustomerDiscount() {
        return customerDiscount;
    }

    public void setCustomerDiscount(double customerDiscount) {
        this.customerDiscount = customerDiscount;
    }

    public PaymentResponse withCustomerDiscount(double customerDiscount) {
        setCustomerDiscount(customerDiscount);
        return this;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public PaymentResponse withCustomerPhone(String customerPhone) {
        setCustomerPhone(customerPhone);
        return this;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public PaymentResponse withAmountPaid(double amountPaid) {
        setAmountPaid(amountPaid);
        return this;
    }

    public double getCustomerCreditLimit() {
        return customerCreditLimit;
    }

    public void setCustomerCreditLimit(double customerCreditLimit) {
        this.customerCreditLimit = customerCreditLimit;
    }

    public PaymentResponse withCustomerCreditLimit(double customerCreditLimit) {
        setCustomerCreditLimit(customerCreditLimit);
        return this;
    }

    public double getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(double customerBalance) {
        this.customerBalance = customerBalance;
    }

    public PaymentResponse withCustomerBalance(double customerBalance) {
        setCustomerBalance(customerBalance);
        return this;
    }

    public String getCustomerData() {
        return customerData;
    }

    public void setCustomerData(String customerData) {
        this.customerData = customerData;
    }

    public PaymentResponse withCustomerData(String customerData) {
        setCustomerData(customerData);
        return this;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeVarLong(dateTime, true);
        output.writeVarInt(warehouseId, true);
        output.writeAscii(warehouseStreet1);
        output.writeAscii(warehouseCity);
        output.writeAscii(warehouseState);
        output.writeAscii(warehouseZip);
        output.writeVarInt(districtId, true);
        output.writeAscii(districtStreet1);
        output.writeAscii(districtStreet2);
        output.writeAscii(districtCity);
        output.writeAscii(districtState);
        output.writeAscii(districtZip);
        output.writeVarInt(customerId, true);
        output.writeAscii(customerFirst);
        output.writeAscii(customerMiddle);
        output.writeAscii(customerLast);
        output.writeAscii(customerStreet1);
        output.writeAscii(customerStreet2);
        output.writeAscii(customerCity);
        output.writeAscii(customerState);
        output.writeAscii(customerZip);
        output.writeVarLong(customerSince, true);
        output.writeAscii(customerCredit);
        output.writeVarDouble(customerDiscount, 4, true);
        output.writeAscii(customerPhone);
        output.writeVarDouble(amountPaid, 2, true);
        output.writeVarDouble(customerCreditLimit, 2, true);
        output.writeVarDouble(customerBalance, 2, true);
        output.writeAscii(customerData);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setDateTime(input.readVarLong(true));
        setWarehouseId(input.readVarInt(true));
        setWarehouseStreet1(input.readString());
        setWarehouseCity(input.readString());
        setWarehouseState(input.readString());
        setWarehouseZip(input.readString());
        setDistrictId(input.readVarInt(true));
        setDistrictStreet1(input.readString());
        setDistrictStreet2(input.readString());
        setDistrictCity(input.readString());
        setDistrictState(input.readString());
        setDistrictZip(input.readString());
        setCustomerId(input.readVarInt(true));
        setCustomerFirst(input.readString());
        setCustomerMiddle(input.readString());
        setCustomerLast(input.readString());
        setCustomerStreet1(input.readString());
        setCustomerStreet2(input.readString());
        setCustomerCity(input.readString());
        setCustomerState(input.readString());
        setCustomerZip(input.readString());
        setCustomerSince(input.readVarLong(true));
        setCustomerCredit(input.readString());
        setCustomerDiscount(input.readVarDouble(4, true));
        setCustomerPhone(input.readString());
        setAmountPaid(input.readVarDouble(2, true));
        setCustomerCreditLimit(input.readVarDouble(2, true));
        setCustomerBalance(input.readVarDouble(2, true));
        setCustomerData(input.readString());
    }

}
