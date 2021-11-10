package bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.output;

public class StockLevelOutput {

    private final Integer warehouseId;
    private final Integer districtId;
    private final Integer threshold;
    private final Long stockCount;

    public StockLevelOutput(Builder builder) {
        this.warehouseId = builder.warehouseId;
        this.districtId = builder.districtId;
        this.threshold = builder.threshold;
        this.stockCount = builder.stockCount;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public Long getStockCount() {
        return stockCount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer warehouseId;
        private Integer districtId;
        private Integer threshold;
        private Long stockCount;

        public Builder warehouseId(Integer warehouseId) {
            this.warehouseId = warehouseId;
            return this;
        }

        public Builder districtId(Integer districtId) {
            this.districtId = districtId;
            return this;
        }

        public Builder threshold(Integer threshold) {
            this.threshold = threshold;
            return this;
        }

        public Builder stockCount(Long stockCount) {
            this.stockCount = stockCount;
            return this;
        }

        public StockLevelOutput build() {
            return new StockLevelOutput(this);
        }

    }

}
