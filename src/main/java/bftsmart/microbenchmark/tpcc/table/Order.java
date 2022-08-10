package bftsmart.microbenchmark.tpcc.table;

import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Tuple;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.ImmutableSet;

import bftsmart.microbenchmark.tpcc.domain.ModelType;
import bftsmart.microbenchmark.tpcc.domain.Persistable;

/**
 * <ol>
 * <li>Primary Key: (O_W_ID, O_D_ID, O_ID)</li>
 * <li>(O_W_ID, O_D_ID, O_C_ID) Foreign Key, references (C_W_ID, C_D_ID,
 * C_ID)</li>
 * </ol>
 */
@JsonDeserialize(builder = Order.Builder.class)
public class Order implements Persistable {

    private static final ModelType MODEL_TYPE = ModelType.ORDER;

    private final Tuple key;
    private final Set<Tuple> secondaryKeys;

    /**
     * o_id - 10,000,000 unique IDs
     */
    @JsonProperty("o_id")
    private final Integer orderId;
    /**
     * o_d_id - 20 unique IDs
     */
    @JsonProperty("o_d_id")
    private final Integer districtId;
    /**
     * o_w_id - 2*W unique IDs
     */
    @JsonProperty("o_w_id")
    private final Integer warehouseId;
    /**
     * o_c_id - 96,000 unique IDs
     */
    @JsonProperty("o_c_id")
    private final Integer customerId;
    /**
     * o_entry_d - date and time
     */
    @JsonProperty("o_entry_d")
    private final Long entryDate;
    /**
     * o_carrier_id - 10 unique IDs, or null
     */
    @JsonProperty("o_carrier_id")
    private final Integer carrierId;
    /**
     * o_ol_cnt - numeric(2) - Count of Order-Lines
     */
    @JsonProperty("o_ol_cnt")
    private final Integer orderLineCounter;
    /**
     * o_all_local - numeric(1)
     */
    @JsonProperty("o_all_local")
    private final Integer allLocal;

    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.districtId = builder.districtId;
        this.warehouseId = builder.warehouseId;
        this.customerId = builder.customerId;
        this.entryDate = builder.entryDate;
        this.carrierId = builder.carrierId;
        this.orderLineCounter = builder.orderLineCounter;
        this.allLocal = builder.allLocal;
        this.key = key(warehouseId, districtId, orderId);
        this.secondaryKeys = ImmutableSet.of(orderKey(warehouseId, districtId, customerId, orderId),
                customerKey(warehouseId, districtId, customerId));
    }

    @Override
    public Tuple getKey() {
        return key;
    }

    @Override
    public Set<Tuple> getSecondaryKeys() {
        return secondaryKeys;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Long getEntryDate() {
        return entryDate;
    }

    public Integer getCarrierId() {
        return carrierId;
    }

    public Integer getOrderLineCounter() {
        return orderLineCounter;
    }

    public Integer getAllLocal() {
        return allLocal;
    }

    public static Tuple orderKey(Integer warehouseId, Integer districtId, Integer customerId, Integer orderId) {
        return Quintet.with(MODEL_TYPE, warehouseId, districtId, customerId, orderId);
    }

    public static Tuple customerKey(Integer warehouseId, Integer districtId, Integer customerId) {
        return Quartet.with(MODEL_TYPE, warehouseId, districtId, customerId);
    }

    public static Tuple key(Integer warehouseId, Integer districtId, Integer orderId) {
        return Quartet.with(MODEL_TYPE, warehouseId, districtId, orderId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder from(Order order) {
        return new Builder(order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        return Objects.equals(key, other.key);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]").add("key=" + key)
                .add("secondaryKeys=" + secondaryKeys)
                .add("orderId=" + orderId)
                .add("districtId=" + districtId)
                .add("warehouseId=" + warehouseId)
                .add("customerId=" + customerId)
                .add("entryDate=" + entryDate)
                .add("carrierId=" + carrierId)
                .add("orderLineCounter=" + orderLineCounter)
                .add("allLocal=" + allLocal)
                .toString();
    }

    @JsonPOJOBuilder
    public static class Builder {
        @JsonProperty("o_id")
        private Integer orderId;
        @JsonProperty("o_d_id")
        private Integer districtId;
        @JsonProperty("o_w_id")
        private Integer warehouseId;
        @JsonProperty("o_c_id")
        private Integer customerId;
        @JsonProperty("o_entry_d")
        private Long entryDate;
        @JsonProperty("o_carrier_id")
        private Integer carrierId;
        @JsonProperty("o_ol_cnt")
        private Integer orderLineCounter;
        @JsonProperty("o_all_local")
        private Integer allLocal;

        private Builder() {
            super();
        }

        private Builder(Order order) {
            this.orderId = order.orderId;
            this.districtId = order.districtId;
            this.warehouseId = order.warehouseId;
            this.customerId = order.customerId;
            this.entryDate = order.entryDate;
            this.carrierId = order.carrierId;
            this.orderLineCounter = order.orderLineCounter;
            this.allLocal = order.allLocal;
        }

        public Builder orderId(Integer orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder districtId(Integer districtId) {
            this.districtId = districtId;
            return this;
        }

        public Builder warehouseId(Integer warehouseId) {
            this.warehouseId = warehouseId;
            return this;
        }

        public Builder customerId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder entryDate(Long entryDate) {
            this.entryDate = entryDate;
            return this;
        }

        public Builder carrierId(Integer carrierId) {
            this.carrierId = carrierId;
            return this;
        }

        public Builder orderLineCounter(Integer orderLineCounter) {
            this.orderLineCounter = orderLineCounter;
            return this;
        }

        public Builder allLocal(Integer allLocal) {
            this.allLocal = allLocal;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
