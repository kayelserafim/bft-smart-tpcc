package bftsmart.microbenchmark.tpcc.client.terminal;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Range;

import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;
import bftsmart.microbenchmark.tpcc.exception.ConfigurationException;
import bftsmart.microbenchmark.tpcc.probject.TransactionType;

public class TPCCTerminalData {

    private final Integer terminalId;
    private final String terminalName;
    private final Integer warehouseId;
    private final Integer districtId;
    private final Integer limitPerTerminal;
    private final Integer warmupIterations;
    private final Integer runMins;
    private final Integer newOrderWeight;
    private final Integer paymentWeight;
    private final Integer deliveryWeight;
    private final Integer orderStatusWeight;
    private final Integer stockLevelWeight;
    private final Integer warehouseCount;

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

    public Integer getLimitPerTerminal() {
        return limitPerTerminal;
    }

    public Integer getWarmupIterations() {
        return warmupIterations;
    }

    public Integer getRunMins() {
        return runMins;
    }

    public long getRunMins(TimeUnit unit) {
        return unit.convert(getRunMins(), TimeUnit.MINUTES);
    }

    public Instant instantToWait() {
        return Instant.now().plusSeconds(getRunMins(TimeUnit.SECONDS));
    }

    public Range<Integer> getNewOrderWeight() {
        return Range.between(1, newOrderWeight);
    }

    public Range<Integer> getPaymentWeight() {
        Integer baseWeight = getNewOrderWeight().getMaximum();
        Integer fromInclusive = baseWeight + 1;
        Integer toInclusive = baseWeight + paymentWeight;
        return Range.between(fromInclusive, toInclusive);
    }

    public Range<Integer> getStockLevelWeight() {
        Integer baseWeight = getPaymentWeight().getMaximum();
        Integer fromInclusive = baseWeight + 1;
        Integer toInclusive = baseWeight + stockLevelWeight;
        return Range.between(fromInclusive, toInclusive);
    }

    public Range<Integer> getOrderStatusWeight() {
        Integer baseWeight = getStockLevelWeight().getMaximum();
        Integer fromInclusive = baseWeight + 1;
        Integer toInclusive = baseWeight + orderStatusWeight;
        return Range.between(fromInclusive, toInclusive);
    }

    public Range<Integer> getDeliveryWeight() {
        Integer baseWeight = getOrderStatusWeight().getMaximum();
        Integer fromInclusive = baseWeight + 1;
        Integer toInclusive = baseWeight + deliveryWeight;
        return Range.between(fromInclusive, toInclusive);
    }

    public TransactionType getTransactionType(Integer transactionTypeNumber) {
        TransactionType transactionType;
        if (getNewOrderWeight().contains(transactionTypeNumber)) {
            transactionType = TransactionType.NEW_ORDER;
        } else if (getPaymentWeight().contains(transactionTypeNumber)) {
            transactionType = TransactionType.PAYMENT;
        } else if (getStockLevelWeight().contains(transactionTypeNumber)) {
            transactionType = TransactionType.STOCK_LEVEL;
        } else if (getOrderStatusWeight().contains(transactionTypeNumber)) {
            transactionType = TransactionType.ORDER_STATUS;
        } else if (getDeliveryWeight().contains(transactionTypeNumber)) {
            transactionType = TransactionType.DELIVERY;
        } else {
            throw new ConfigurationException("The sum of transaction percentage parameters exceeds 100%!");
        }
        return transactionType;
    }

    public Integer getWarehouseCount() {
        return warehouseCount;
    }

    public static Builder builder() {
        return new Builder();
    }

    private TPCCTerminalData(Builder builder) {
        terminalId = builder.terminalId;
        terminalName = builder.terminalName;
        warehouseId = builder.warehouseId;
        districtId = builder.districtId;
        limitPerTerminal = builder.limitPerTerminal;
        warmupIterations = builder.warmupIterations;
        runMins = builder.runMins;
        newOrderWeight = builder.newOrderWeight;
        paymentWeight = builder.paymentWeight;
        deliveryWeight = builder.deliveryWeight;
        orderStatusWeight = builder.orderStatusWeight;
        stockLevelWeight = builder.stockLevelWeight;
        warehouseCount = builder.warehouseCount;
    }

    public static class Builder {

        private Integer terminalId;
        private String terminalName;
        private Integer warehouseId;
        private Integer districtId;
        private Integer limitPerTerminal;
        private Integer warmupIterations;
        private Integer runMins;
        private Integer newOrderWeight;
        private Integer paymentWeight;
        private Integer deliveryWeight;
        private Integer orderStatusWeight;
        private Integer stockLevelWeight;
        private Integer warehouseCount;

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

        public Builder limitPerTerminal(Integer limitPerTerminal) {
            this.limitPerTerminal = limitPerTerminal;
            return this;
        }

        public Builder warmupIterations(Integer warmupIterations) {
            this.warmupIterations = warmupIterations;
            return this;
        }

        public Builder runMins(Integer runMins) {
            this.runMins = runMins;
            return this;
        }

        public Builder workload(WorkloadConfig workload) {
            return warehouseCount(workload.getWarehouses()).stockLevelWeight(workload.getStockLevelWeight())
                    .orderStatusWeight(workload.getOrderStatusWeight())
                    .deliveryWeight(workload.getDeliveryWeight())
                    .paymentWeight(workload.getPaymentWeight())
                    .newOrderWeight(workload.getNewOrderWeight())
                    .limitPerTerminal(workload.getTxnsPerTerminal())
                    .warmupIterations(workload.getWarmupIterations())
                    .runMins(workload.getRunMins());
        }

        public TPCCTerminalData build() {
            return new TPCCTerminalData(this);
        }

    }

}
