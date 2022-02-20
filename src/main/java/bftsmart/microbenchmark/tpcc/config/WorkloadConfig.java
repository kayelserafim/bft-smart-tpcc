package bftsmart.microbenchmark.tpcc.config;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonProperty;

import bftsmart.microbenchmark.tpcc.probject.TransactionType;

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
    private Set<TransactionType> writeTransactions;
    @JsonProperty("transactionReads")
    private Set<TransactionType> readTransactions;

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

    public Set<TransactionType> getWriteTransactions() {
        if (writeTransactions == null) {
            writeTransactions = new HashSet<>();
        }
        return writeTransactions;
    }

    public Set<TransactionType> getReadTransactions() {
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
        return new StringJoiner(", ", WorkloadConfig.class.getSimpleName() + "[", "]")
                .add("warehouses=" + warehouses)
                .add("terminals=" + terminals)
                .add("txnsPerTerminal=" + txnsPerTerminal)
                .add("runMins=" + runMins)
                .add("limitTxnsPerMin=" + limitTxnsPerMin)
                .add("warmupIterations=" + warmupIterations)
                .add("newOrderWeight=" + newOrderWeight)
                .add("paymentWeight=" + paymentWeight)
                .add("orderStatusWeight=" + orderStatusWeight)
                .add("deliveryWeight=" + deliveryWeight)
                .add("stockLevelWeight=" + stockLevelWeight)
                .add("writeTransactions=" + writeTransactions)
                .add("readTransactions=" + readTransactions)
                .toString();
    }
}
