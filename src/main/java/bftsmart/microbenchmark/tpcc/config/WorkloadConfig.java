package bftsmart.microbenchmark.tpcc.config;

import java.util.HashSet;
import java.util.Set;

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
        if (writeTransactions == null) {
            writeTransactions = new HashSet<>();
        }
        return writeTransactions;
    }

    public Set<TPCCCommandType> getReadTransactions() {
        if (readTransactions == null) {
            readTransactions = new HashSet<>();
        }
        return readTransactions;
    }

    public String getFileName() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("w_").append(warehouses);
        sb.append("_d_").append(TPCCConfig.DIST_PER_WHSE * warehouses);
        sb.append("_c_").append(TPCCConfig.CUST_PER_DIST * TPCCConfig.DIST_PER_WHSE * warehouses);
        sb.append("_i_").append(TPCCConfig.NB_MAX_ITEM * warehouses);
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("WorkloadConfig [warehouses=")
                .append(warehouses)
                .append(", terminals=")
                .append(terminals)
                .append(", txnsPerTerminal=")
                .append(txnsPerTerminal)
                .append(", runMins=")
                .append(runMins)
                .append(", limitTxnsPerMin=")
                .append(limitTxnsPerMin)
                .append(", warmupIterations=")
                .append(warmupIterations)
                .append(", newOrderWeight=")
                .append(newOrderWeight)
                .append(", paymentWeight=")
                .append(paymentWeight)
                .append(", orderStatusWeight=")
                .append(orderStatusWeight)
                .append(", deliveryWeight=")
                .append(deliveryWeight)
                .append(", stockLevelWeight=")
                .append(stockLevelWeight)
                .append(", writeTransactions=")
                .append(writeTransactions)
                .append(", readTransactions=")
                .append(readTransactions)
                .append("]");
        return builder.toString();
    }

}
