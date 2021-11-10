package bftsmart.microbenchmark.tpcc.config;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;

public class WorkloadConfig {

    public static final String RESOURCE_PATH = "workload.properties";

    @JsonProperty("warehousesCnt")
    private Integer warehouses;
    @JsonProperty("terminalsCnt")
    private Integer terminals;
    @JsonProperty("terminalTxnsPerTerminal")
    private Integer txnsPerTerminal;
    @JsonProperty("terminalRunMins")
    private Integer runMins;
    @JsonProperty("limitTxnsPerMin")
    private Integer limitTxnsPerMin;
    @JsonProperty("warmupIterations")
    private Integer warmupIterations;
    @JsonProperty("newOrderWeight")
    private Integer newOrderWeight;
    @JsonProperty("paymentWeight")
    private Integer paymentWeight;
    @JsonProperty("orderStatusWeight")
    private Integer orderStatusWeight;
    @JsonProperty("deliveryWeight")
    private Integer deliveryWeight;
    @JsonProperty("stockLevelWeight")
    private Integer stockLevelWeight;
    @JsonProperty("transactionWrites")
    private Set<TPCCCommandType> writeTransactions;
    @JsonProperty("transactionReads")
    private Set<TPCCCommandType> readTransactions;

    public Integer getWarehouses() {
        return warehouses;
    }

    public Integer getTerminals() {
        return terminals;
    }

    public Integer getTxnsPerTerminal() {
        return txnsPerTerminal;
    }

    public Integer getRunMins() {
        return runMins;
    }

    public long getRunMins(TimeUnit unit) {
        return unit.convert(runMins, TimeUnit.MINUTES);
    }

    public Integer getLimitTxnsPerMin() {
        return limitTxnsPerMin;
    }

    public Integer getWarmupIterations() {
        return warmupIterations;
    }

    public Integer getNewOrderWeight() {
        return newOrderWeight;
    }

    public Integer getPaymentWeight() {
        return paymentWeight;
    }

    public Integer getOrderStatusWeight() {
        return orderStatusWeight;
    }

    public Integer getDeliveryWeight() {
        return deliveryWeight;
    }

    public Integer getStockLevelWeight() {
        return stockLevelWeight;
    }

    public Set<TPCCCommandType> getWriteTransactions() {
        return writeTransactions;
    }

    public Set<TPCCCommandType> getReadTransactions() {
        return readTransactions;
    }

    public String getFileName() {
        int distPerWhse = TPCCConfig.DIST_PER_WHSE * warehouses;
        int custPerDist = TPCCConfig.CUST_PER_DIST * distPerWhse;
        int stockItemsPerWhse = TPCCConfig.NB_MAX_ITEM * warehouses;
        return "w_" + warehouses + "_d_" + distPerWhse + "_c_" + custPerDist + "_i_" + stockItemsPerWhse;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
