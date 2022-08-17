package bftsmart.microbenchmark.tpcc.table.key;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class HistoryKey implements Externalizable, Comparable<HistoryKey> {
    private int warehouseId;
    private int districtId;

    public HistoryKey() {
        super();
    }

    public HistoryKey(int warehouseId, int districtId) {
        this.warehouseId = warehouseId;
        this.districtId = districtId;
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

    @Override
    public int compareTo(HistoryKey key) {
        return new CompareToBuilder().append(getWarehouseId(), key.getWarehouseId())
                .append(getDistrictId(), key.getDistrictId())
                .toComparison();
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.warehouseId = in.readInt();
        this.districtId = in.readInt();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(warehouseId);
        out.writeInt(districtId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(districtId, warehouseId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HistoryKey other = (HistoryKey) obj;
        return districtId == other.districtId && warehouseId == other.warehouseId;
    }
}