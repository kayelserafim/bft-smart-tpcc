package bftsmart.microbenchmark.tpcc.client.terminal;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Range;

import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;
import bftsmart.microbenchmark.tpcc.exception.ConfigurationException;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;

public class TPCCTerminalData {

    private final Integer terminalId;
    private final String terminalName;
    private final Integer warehouseId;
    private final Integer districtId;
    private final Integer numTransactions;
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

    public Integer getNumTransactions() {
        return numTransactions;
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

    public TPCCCommandType getTPCCCommandType(Integer transactionType) {
        TPCCCommandType commandType;
        if (getNewOrderWeight().contains(transactionType)) {
            commandType = TPCCCommandType.NEW_ORDER;
        } else if (getPaymentWeight().contains(transactionType)) {
            commandType = TPCCCommandType.PAYMENT;
        } else if (getStockLevelWeight().contains(transactionType)) {
            commandType = TPCCCommandType.STOCK_LEVEL;
        } else if (getOrderStatusWeight().contains(transactionType)) {
            commandType = TPCCCommandType.ORDER_STATUS;
        } else if (getDeliveryWeight().contains(transactionType)) {
            commandType = TPCCCommandType.DELIVERY;
        } else {
            throw new ConfigurationException("The sum of mix percentage parameters of transactions exceeds 100%!");
        }
        return commandType;
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
        numTransactions = builder.numTransactions;
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
        private Integer numTransactions;
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

        public Builder numTransactions(Integer numTransactions) {
            this.numTransactions = numTransactions;
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
            newOrderWeight(workload.getNewOrderWeight());
            paymentWeight(workload.getPaymentWeight());
            deliveryWeight(workload.getDeliveryWeight());
            orderStatusWeight(workload.getOrderStatusWeight());
            stockLevelWeight(workload.getStockLevelWeight());
            warehouseCount(workload.getWarehouses());
            limitPerTerminal(workload.getLimitTxnsPerMin());
            warmupIterations(workload.getWarmupIterations());
            runMins(workload.getRunMins());
            numTransactions(workload.getTxnsPerTerminal());
            return this;
        }

        public TPCCTerminalData build() {
            return new TPCCTerminalData(this);
        }

    }

}
