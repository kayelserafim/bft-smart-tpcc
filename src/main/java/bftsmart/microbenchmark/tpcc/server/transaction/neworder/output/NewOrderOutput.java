package bftsmart.microbenchmark.tpcc.server.transaction.neworder.output;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class NewOrderOutput implements KryoSerializable {

    private long dateTime;
    private int warehouseId;
    private double warehouseTax;
    private int districtId;
    private double districtTax;
    private int orderId;
    private int customerId;
    private String customerLast;
    private String customerCredit;
    private double discount;
    private double totalAmount;
    private NewOrderLineOutput[] orderLines;

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public NewOrderOutput withDateTime(long dateTime) {
        setDateTime(dateTime);
        return this;
    }

    public NewOrderOutput withDateTime(LocalDateTime dateTime) {
        setDateTime(dateTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli());
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public NewOrderOutput withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public double getWarehouseTax() {
        return warehouseTax;
    }

    public void setWarehouseTax(double warehouseTax) {
        this.warehouseTax = warehouseTax;
    }

    public NewOrderOutput withWarehouseTax(double warehouseTax) {
        setWarehouseTax(warehouseTax);
        return this;
    }

    public NewOrderOutput withWarehouseTax(BigDecimal warehouseTax) {
        setWarehouseTax(warehouseTax.setScale(4, RoundingMode.HALF_UP).doubleValue());
        return this;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public NewOrderOutput withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public double getDistrictTax() {
        return districtTax;
    }

    public void setDistrictTax(double districtTax) {
        this.districtTax = districtTax;
    }

    public NewOrderOutput withDistrictTax(double districtTax) {
        setDistrictTax(districtTax);
        return this;
    }

    public NewOrderOutput withDistrictTax(BigDecimal districtTax) {
        setDistrictTax(districtTax.setScale(4, RoundingMode.HALF_UP).doubleValue());
        return this;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public NewOrderOutput withOrderId(int orderId) {
        setOrderId(orderId);
        return this;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public NewOrderOutput withCustomerId(int customerId) {
        setCustomerId(customerId);
        return this;
    }

    public String getCustomerLast() {
        return customerLast;
    }

    public void setCustomerLast(String customerLast) {
        this.customerLast = customerLast;
    }

    public NewOrderOutput withCustomerLast(String customerLast) {
        setCustomerLast(customerLast);
        return this;
    }

    public String getCustomerCredit() {
        return customerCredit;
    }

    public void setCustomerCredit(String customerCredit) {
        this.customerCredit = customerCredit;
    }

    public NewOrderOutput withCustomerCredit(String customerCredit) {
        setCustomerCredit(customerCredit);
        return this;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public NewOrderOutput withDiscount(double discount) {
        setDiscount(discount);
        return this;
    }

    public NewOrderOutput withDiscount(BigDecimal discount) {
        setDiscount(discount.setScale(4, RoundingMode.HALF_UP).doubleValue());
        return this;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public NewOrderOutput withTotalAmount(double totalAmount) {
        setTotalAmount(totalAmount);
        return this;
    }

    public NewOrderOutput withTotalAmount(BigDecimal totalAmount) {
        setTotalAmount(totalAmount.setScale(2, RoundingMode.HALF_UP).doubleValue());
        return this;
    }

    public NewOrderLineOutput[] getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(NewOrderLineOutput[] orderLines) {
        this.orderLines = orderLines;
    }

    public NewOrderOutput withOrderLines(NewOrderLineOutput[] orderLines) {
        setOrderLines(orderLines);
        return this;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeVarLong(dateTime, true);
        output.writeVarInt(warehouseId, true);
        output.writeVarDouble(warehouseTax, 4, true);
        output.writeVarInt(districtId, true);
        output.writeVarDouble(districtTax, 4, true);
        output.writeVarInt(orderId, true);
        output.writeVarInt(customerId, true);
        output.writeAscii(customerLast);
        output.writeAscii(customerCredit);
        output.writeVarDouble(discount, 4, true);
        output.writeVarDouble(totalAmount, 2, true);
        kryo.writeObject(output, orderLines);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setDateTime(input.readVarLong(true));
        setWarehouseId(input.readVarInt(true));
        setWarehouseTax(input.readVarDouble(4, true));
        setDistrictId(input.readVarInt(true));
        setOrderId(input.readVarInt(true));
        setCustomerId(input.readVarInt(true));
        setCustomerLast(input.readString());
        setCustomerCredit(input.readString());
        setDiscount(input.readVarDouble(4, true));
        setTotalAmount(input.readVarDouble(2, true));
        setOrderLines(kryo.readObject(input, NewOrderLineOutput[].class));
    }
}
