package bftsmart.microbenchmark.tpcc.table.key;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class NewOrderKey implements Externalizable, Comparable<NewOrderKey> {
    private int warehouseId;
    private int districtId;
    private int orderId;

    public NewOrderKey() {
        super();
    }

    public NewOrderKey(int warehouseId, int districtId, int orderId) {
        this.warehouseId = warehouseId;
        this.districtId = districtId;
        this.orderId = orderId;
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

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.warehouseId = in.readInt();
        this.districtId = in.readInt();
        this.orderId = in.readInt();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(warehouseId);
        out.writeInt(districtId);
        out.writeInt(orderId);
    }

    @Override
    public int compareTo(NewOrderKey key) {
        return new CompareToBuilder().append(getWarehouseId(), key.getWarehouseId())
                .append(getDistrictId(), key.getDistrictId())
                .append(getOrderId(), key.getOrderId())
                .toComparison();
    }

    @Override
    public int hashCode() {
        return Objects.hash(districtId, orderId, warehouseId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NewOrderKey other = (NewOrderKey) obj;
        return districtId == other.districtId && orderId == other.orderId && warehouseId == other.warehouseId;
    }
}