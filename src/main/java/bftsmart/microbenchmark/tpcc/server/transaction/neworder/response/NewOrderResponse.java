package bftsmart.microbenchmark.tpcc.server.transaction.neworder.response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class NewOrderResponse implements KryoSerializable {

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
    private NewOrderLineResponse[] orderLines;

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public NewOrderResponse withDateTime(long dateTime) {
        setDateTime(dateTime);
        return this;
    }

    public NewOrderResponse withDateTime(LocalDateTime dateTime) {
        setDateTime(dateTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli());
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public NewOrderResponse withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public double getWarehouseTax() {
        return warehouseTax;
    }

    public void setWarehouseTax(double warehouseTax) {
        this.warehouseTax = warehouseTax;
    }

    public NewOrderResponse withWarehouseTax(double warehouseTax) {
        setWarehouseTax(warehouseTax);
        return this;
    }

    public NewOrderResponse withWarehouseTax(BigDecimal warehouseTax) {
        setWarehouseTax(warehouseTax.setScale(4, RoundingMode.HALF_UP).doubleValue());
        return this;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public NewOrderResponse withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public double getDistrictTax() {
        return districtTax;
    }

    public void setDistrictTax(double districtTax) {
        this.districtTax = districtTax;
    }

    public NewOrderResponse withDistrictTax(double districtTax) {
        setDistrictTax(districtTax);
        return this;
    }

    public NewOrderResponse withDistrictTax(BigDecimal districtTax) {
        setDistrictTax(districtTax.setScale(4, RoundingMode.HALF_UP).doubleValue());
        return this;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public NewOrderResponse withOrderId(int orderId) {
        setOrderId(orderId);
        return this;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public NewOrderResponse withCustomerId(int customerId) {
        setCustomerId(customerId);
        return this;
    }

    public String getCustomerLast() {
        return customerLast;
    }

    public void setCustomerLast(String customerLast) {
        this.customerLast = customerLast;
    }

    public NewOrderResponse withCustomerLast(String customerLast) {
        setCustomerLast(customerLast);
        return this;
    }

    public String getCustomerCredit() {
        return customerCredit;
    }

    public void setCustomerCredit(String customerCredit) {
        this.customerCredit = customerCredit;
    }

    public NewOrderResponse withCustomerCredit(String customerCredit) {
        setCustomerCredit(customerCredit);
        return this;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public NewOrderResponse withDiscount(double discount) {
        setDiscount(discount);
        return this;
    }

    public NewOrderResponse withDiscount(BigDecimal discount) {
        setDiscount(discount.setScale(4, RoundingMode.HALF_UP).doubleValue());
        return this;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public NewOrderResponse withTotalAmount(double totalAmount) {
        setTotalAmount(totalAmount);
        return this;
    }

    public NewOrderResponse withTotalAmount(BigDecimal totalAmount) {
        setTotalAmount(totalAmount.setScale(2, RoundingMode.HALF_UP).doubleValue());
        return this;
    }

    public NewOrderLineResponse[] getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(NewOrderLineResponse[] orderLines) {
        this.orderLines = orderLines;
    }

    public NewOrderResponse withOrderLines(NewOrderLineResponse[] orderLines) {
        setOrderLines(orderLines);
        return this;
    }

    public NewOrderResponse withOrderLine(int index, NewOrderLineResponse orderLine) {
        orderLines[index] = orderLine;
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
        setOrderLines(kryo.readObject(input, NewOrderLineResponse[].class));
    }
}
