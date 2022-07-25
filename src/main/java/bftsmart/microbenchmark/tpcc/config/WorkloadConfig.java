package bftsmart.microbenchmark.tpcc.config;

import java.time.Duration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class WorkloadConfig {

    public static final String RESOURCE_PATH = "workload.properties";

    @Inject
    @Named("warehousesCnt")
    private Integer warehouses;
    @Inject
    @Named("terminalRunMins")
    private Integer runMins;
    @Inject
    @Named("warmupIterations")
    private Integer warmupIterations;
    @Inject
    @Named("newOrderWeight")
    private Integer newOrderWeight;
    @Inject
    @Named("paymentWeight")
    private Integer paymentWeight;
    @Inject
    @Named("orderStatusWeight")
    private Integer orderStatusWeight;
    @Inject
    @Named("deliveryWeight")
    private Integer deliveryWeight;
    @Inject
    @Named("stockLevelWeight")
    private Integer stockLevelWeight;

    public Integer getWarehouses() {
        return warehouses;
    }

    public Duration getRunMins() {
        return Duration.ofMinutes(runMins);
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
                .append(", runMins=")
                .append(runMins)
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
                .append(']');
        return builder.toString();
    }

}
