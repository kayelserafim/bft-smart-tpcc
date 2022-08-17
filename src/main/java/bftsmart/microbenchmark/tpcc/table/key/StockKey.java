package bftsmart.microbenchmark.tpcc.table.key;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class StockKey implements Externalizable, Comparable<StockKey> {
    private int warehouseId;
    private int itemId;

    public StockKey() {
        super();
    }

    public StockKey(int warehouseId, int itemId) {
        this.warehouseId = warehouseId;
        this.itemId = itemId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.warehouseId = in.readInt();
        this.itemId = in.readInt();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(warehouseId);
        out.writeInt(itemId);
    }

    @Override
    public int compareTo(StockKey key) {
        return new CompareToBuilder().append(getWarehouseId(), key.getWarehouseId())
                .append(getItemId(), key.getItemId())
                .toComparison();
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, warehouseId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StockKey other = (StockKey) obj;
        return itemId == other.itemId && warehouseId == other.warehouseId;
    }

}