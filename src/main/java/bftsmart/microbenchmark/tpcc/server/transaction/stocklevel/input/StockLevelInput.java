package bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.input;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockLevelInput implements Serializable {

    private static final long serialVersionUID = -1939225465527379511L;

    @JsonProperty("w_id")
    private Integer warehouseId;
    @JsonProperty("d_id")
    private Integer districtId;
    @JsonProperty("threshold")
    private Integer threshold;

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public StockLevelInput withWarehouseId(Integer warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public StockLevelInput withDistrictId(Integer districtId) {
        setDistrictId(districtId);
        return this;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public StockLevelInput withThreshold(Integer threshold) {
        setThreshold(threshold);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StockLevelInput [warehouseId=")
                .append(warehouseId)
                .append(", districtId=")
                .append(districtId)
                .append(", threshold=")
                .append(threshold)
                .append(']');
        return builder.toString();
    }

}
