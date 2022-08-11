package bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.response;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class StockLevelResponse implements KryoSerializable {

    private int warehouseId;
    private int districtId;
    private int threshold;
    private long stockCount;

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public StockLevelResponse withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public StockLevelResponse withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public StockLevelResponse withThreshold(int threshold) {
        setThreshold(threshold);
        return this;
    }

    public long getStockCount() {
        return stockCount;
    }

    public void setStockCount(long stockCount) {
        this.stockCount = stockCount;
    }

    public StockLevelResponse withStockCount(long stockCount) {
        setStockCount(stockCount);
        return this;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeVarInt(warehouseId, true);
        output.writeVarInt(districtId, true);
        output.writeVarInt(threshold, true);
        output.writeVarLong(stockCount, true);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setWarehouseId(input.readInt(true));
        setDistrictId(input.readInt(true));
        setThreshold(input.readInt(true));
        setStockCount(input.readLong(true));
    }

}
