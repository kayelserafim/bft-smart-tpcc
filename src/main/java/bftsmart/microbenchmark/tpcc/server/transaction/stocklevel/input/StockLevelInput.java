package bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.input;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StockLevelInput implements KryoSerializable {

    @JsonProperty("w_id")
    private int warehouseId;
    @JsonProperty("d_id")
    private int districtId;
    @JsonProperty("threshold")
    private int threshold;

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public StockLevelInput withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public StockLevelInput withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public StockLevelInput withThreshold(int threshold) {
        setThreshold(threshold);
        return this;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeVarInt(warehouseId, true);
        output.writeVarInt(districtId, true);
        output.writeVarInt(threshold, true);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setWarehouseId(input.readVarInt(true));
        setDistrictId(input.readVarInt(true));
        setThreshold(input.readVarInt(true));
    }
}
