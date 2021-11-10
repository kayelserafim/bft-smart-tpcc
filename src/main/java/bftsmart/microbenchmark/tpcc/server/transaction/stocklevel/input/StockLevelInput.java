package bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.input;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockLevelInput {

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

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
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
