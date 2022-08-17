package bftsmart.microbenchmark.tpcc.table.key;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class CustomerKey implements Externalizable, Comparable<CustomerKey> {

    private int warehouseId;
    private int districtId;
    private int customerId;

    public CustomerKey() {
        super();
    }

    public CustomerKey(int warehouseId, int districtId, int customerId) {
        this.warehouseId = warehouseId;
        this.districtId = districtId;
        this.customerId = customerId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public int getCustomerId() {
        return customerId;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.warehouseId = in.readInt();
        this.districtId = in.readInt();
        this.customerId = in.readInt();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(warehouseId);
        out.writeInt(districtId);
        out.writeInt(customerId);
    }

    @Override
    public int compareTo(CustomerKey key) {
        return new CompareToBuilder().append(getWarehouseId(), key.getWarehouseId())
                .append(getDistrictId(), key.getDistrictId())
                .append(getCustomerId(), key.getCustomerId())
                .toComparison();
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, districtId, warehouseId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomerKey other = (CustomerKey) obj;
        return customerId == other.customerId && districtId == other.districtId && warehouseId == other.warehouseId;
    }

}