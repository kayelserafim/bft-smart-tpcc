package bftsmart.microbenchmark.tpcc.table.key;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class OrderLineKey implements Externalizable, Comparable<OrderLineKey> {
    private int warehouseId;
    private int districtId;
    private int orderId;
    private int number;

    public OrderLineKey() {
        super();
    }

    public OrderLineKey(int warehouseId, int districtId, int orderId, int number) {
        this.warehouseId = warehouseId;
        this.districtId = districtId;
        this.orderId = orderId;
        this.number = number;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.warehouseId = in.readInt();
        this.districtId = in.readInt();
        this.orderId = in.readInt();
        this.number = in.readInt();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(warehouseId);
        out.writeInt(districtId);
        out.writeInt(orderId);
        out.writeInt(number);
    }

    @Override
    public int compareTo(OrderLineKey key) {
        return new CompareToBuilder().append(getWarehouseId(), key.getWarehouseId())
                .append(getDistrictId(), key.getDistrictId())
                .append(getOrderId(), key.getOrderId())
                .append(getNumber(), key.getNumber())
                .toComparison();
    }

    @Override
    public int hashCode() {
        return Objects.hash(districtId, number, orderId, warehouseId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderLineKey other = (OrderLineKey) obj;
        return districtId == other.districtId && number == other.number && orderId == other.orderId
                && warehouseId == other.warehouseId;
    }

}