package bftsmart.microbenchmark.tpcc.client.terminal;

import java.time.Duration;

import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.domain.TransactionType;
import bftsmart.microbenchmark.tpcc.exception.ConfigurationException;

public class TPCCTerminalData {

    private final Integer terminalId;
    private final String terminalName;
    private final Integer warehouseId;
    private final Integer districtId;
    private final Integer newOrderWeight;
    private final Integer paymentWeight;
    private final Integer deliveryWeight;
    private final Integer orderStatusWeight;
    private final Integer stockLevelWeight;
    private final Integer warehouseCount;
    private final Integer warmupIterations;
    private final Duration runMins;
    private final Boolean parallel;

    public Integer getTerminalId() {
        return terminalId;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public Integer getWarehouseCount() {
        return warehouseCount;
    }

    public Integer getWarmupIterations() {
        return warmupIterations;
    }

    public Duration getRunMins() {
        return runMins;
    }

    public Boolean getParallel() {
        return parallel;
    }

    public TransactionType getTransactionType(Integer weight) {
        TransactionType transactionType;
        if (weight <= paymentWeight) {
            transactionType = TransactionType.PAYMENT;
        } else if (weight <= paymentWeight + stockLevelWeight) {
            transactionType = TransactionType.STOCK_LEVEL;
        } else if (weight <= paymentWeight + stockLevelWeight + orderStatusWeight) {
            transactionType = TransactionType.ORDER_STATUS;
        } else if (weight <= paymentWeight + stockLevelWeight + orderStatusWeight + deliveryWeight) {
            transactionType = TransactionType.DELIVERY;
        } else if (weight <= paymentWeight + stockLevelWeight + orderStatusWeight + deliveryWeight + newOrderWeight) {
            transactionType = TransactionType.NEW_ORDER;
        } else {
            throw new ConfigurationException("The sum of transaction percentage parameters exceeds 100%!");
        }
        return transactionType;
    }

    public static Builder builder() {
        return new Builder();
    }

    private TPCCTerminalData(Builder builder) {
        terminalId = builder.terminalId;
        terminalName = builder.terminalName;
        warehouseId = builder.warehouseId;
        districtId = builder.districtId;
        newOrderWeight = builder.newOrderWeight;
        paymentWeight = builder.paymentWeight;
        deliveryWeight = builder.deliveryWeight;
        orderStatusWeight = builder.orderStatusWeight;
        stockLevelWeight = builder.stockLevelWeight;
        warehouseCount = builder.warehouseCount;
        warmupIterations = builder.warmupIterations;
        runMins = builder.runMins;
        parallel = builder.parallel;
    }

    public static class Builder {

        private Integer terminalId;
        private String terminalName;
        private Integer warehouseId;
        private Integer districtId;
        private Integer newOrderWeight;
        private Integer paymentWeight;
        private Integer deliveryWeight;
        private Integer orderStatusWeight;
        private Integer stockLevelWeight;
        private Integer warehouseCount;
        private Integer warmupIterations;
        private Duration runMins;
        private Boolean parallel;

        public Builder warehouseCount(Integer warehouseCount) {
            this.warehouseCount = warehouseCount;
            return this;
        }

        public Builder stockLevelWeight(Integer stockLevelWeight) {
            this.stockLevelWeight = stockLevelWeight;
            return this;
        }

        public Builder orderStatusWeight(Integer orderStatusWeight) {
            this.orderStatusWeight = orderStatusWeight;
            return this;
        }

        public Builder deliveryWeight(Integer deliveryWeight) {
            this.deliveryWeight = deliveryWeight;
            return this;
        }

        public Builder paymentWeight(Integer paymentWeight) {
            this.paymentWeight = paymentWeight;
            return this;
        }

        public Builder newOrderWeight(Integer newOrderWeight) {
            this.newOrderWeight = newOrderWeight;
            return this;
        }

        public Builder warehouseId(Integer warehouseId) {
            this.warehouseId = warehouseId;
            return this;
        }

        public Builder districtId(Integer districtId) {
            this.districtId = districtId;
            return this;
        }

        public Builder terminalId(Integer terminalId) {
            this.terminalId = terminalId;
            this.terminalName = "Term-" + terminalId;
            return this;
        }

        public Builder warmupIterations(Integer warmupIterations) {
            this.warmupIterations = warmupIterations;
            return this;
        }

        public Builder runMins(Duration runMins) {
            this.runMins = runMins;
            return this;
        }

        public Builder parallel(Boolean parallel) {
            this.parallel = parallel;
            return this;
        }

        public Builder workload(TPCCConfig workload) {
            return warehouseCount(workload.getWarehouses()).stockLevelWeight(workload.getStockLevelWeight())
                    .orderStatusWeight(workload.getOrderStatusWeight())
                    .deliveryWeight(workload.getDeliveryWeight())
                    .paymentWeight(workload.getPaymentWeight())
                    .newOrderWeight(workload.getNewOrderWeight())
                    .warmupIterations(workload.getWarmupIterations())
                    .runMins(workload.getRunMins());
        }

        public TPCCTerminalData build() {
            return new TPCCTerminalData(this);
        }

    }

}
