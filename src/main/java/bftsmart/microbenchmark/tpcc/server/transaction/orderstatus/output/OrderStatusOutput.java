package bftsmart.microbenchmark.tpcc.server.transaction.orderstatus.output;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class OrderStatusOutput implements KryoSerializable {

    private long dateTime;
    private int warehouseId;
    private int districtId;
    private int customerId;
    private String customerFirst;
    private String customerMiddle;
    private String customerLast;
    private double customerBalance;
    private int orderId;
    private long entryDate;
    private int carrierId;
    private OrderLineOutput[] orderLines;

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public OrderStatusOutput withDateTime(long dateTime) {
        setDateTime(dateTime);
        return this;
    }

    public OrderStatusOutput withDateTime(LocalDateTime dateTime) {
        setDateTime(dateTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli());
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public OrderStatusOutput withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public OrderStatusOutput withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public OrderStatusOutput withCustomerId(int customerId) {
        setCustomerId(customerId);
        return this;
    }

    public String getCustomerFirst() {
        return customerFirst;
    }

    public void setCustomerFirst(String customerFirst) {
        this.customerFirst = customerFirst;
    }

    public OrderStatusOutput withCustomerFirst(String customerFirst) {
        setCustomerFirst(customerFirst);
        return this;
    }

    public String getCustomerMiddle() {
        return customerMiddle;
    }

    public void setCustomerMiddle(String customerMiddle) {
        this.customerMiddle = customerMiddle;
    }

    public OrderStatusOutput withCustomerMiddle(String customerMiddle) {
        setCustomerMiddle(customerMiddle);
        return this;
    }

    public String getCustomerLast() {
        return customerLast;
    }

    public void setCustomerLast(String customerLast) {
        this.customerLast = customerLast;
    }

    public OrderStatusOutput withCustomerLast(String customerLast) {
        setCustomerLast(customerLast);
        return this;
    }

    public double getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(double customerBalance) {
        this.customerBalance = customerBalance;
    }

    public OrderStatusOutput withCustomerBalance(double customerBalance) {
        setCustomerBalance(customerBalance);
        return this;
    }

    public OrderStatusOutput withCustomerBalance(BigDecimal customerBalance) {
        setCustomerBalance(customerBalance.setScale(2, RoundingMode.HALF_UP).doubleValue());
        return this;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public OrderStatusOutput withOrderId(int orderId) {
        setOrderId(orderId);
        return this;
    }

    public long getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(long entryDate) {
        this.entryDate = entryDate;
    }

    public OrderStatusOutput withEntryDate(long entryDate) {
        setEntryDate(entryDate);
        return this;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }

    public OrderStatusOutput withCarrierId(int carrierId) {
        setCarrierId(carrierId);
        return this;
    }

    public OrderLineOutput[] getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(OrderLineOutput[] orderLines) {
        this.orderLines = orderLines;
    }

    public OrderStatusOutput withOrderLines(OrderLineOutput[] orderLines) {
        setOrderLines(orderLines);
        return this;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeVarLong(dateTime, true);
        output.writeVarInt(warehouseId, true);
        output.writeVarInt(districtId, true);
        output.writeVarInt(customerId, true);
        output.writeAscii(customerFirst);
        output.writeAscii(customerMiddle);
        output.writeAscii(customerLast);
        output.writeVarDouble(customerBalance, 2, true);
        output.writeVarInt(orderId, true);
        output.writeVarLong(entryDate, true);
        output.writeVarInt(carrierId, true);
        kryo.writeObject(output, orderLines);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setDateTime(input.readVarLong(true));
        setWarehouseId(input.readVarInt(true));
        setDistrictId(input.readVarInt(true));
        setCustomerId(input.readVarInt(true));
        setCustomerFirst(input.readString());
        setCustomerMiddle(input.readString());
        setCustomerLast(input.readString());
        setCustomerBalance(input.readVarDouble(2, true));
        setOrderId(input.readVarInt(true));
        setEntryDate(input.readVarLong(true));
        setCarrierId(input.readVarInt(true));
        setOrderLines(kryo.readObject(input, OrderLineOutput[].class));
    }

}
