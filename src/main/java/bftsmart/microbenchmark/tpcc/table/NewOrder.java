package bftsmart.microbenchmark.tpcc.table;

import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.javatuples.Tuple;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.ImmutableSet;

import bftsmart.microbenchmark.tpcc.domain.ModelType;
import bftsmart.microbenchmark.tpcc.domain.Persistable;

/**
 * <ol>
 * <li>Primary Key: (NO_W_ID, NO_D_ID, NO_O_ID)</li>
 * <li>((NO_W_ID, NO_D_ID, NO_O_ID) Foreign Key, references (O_W_ID, O_D_ID,
 * O_ID)</li>
 * </ol>
 */
@JsonDeserialize(builder = NewOrder.Builder.class)
public class NewOrder implements Persistable {

    private static final ModelType MODEL_TYPE = ModelType.NEW_ORDER;

    private final Tuple key;
    private final Set<Tuple> secondaryKeys;

    /**
     * no_o_id - 10,000,000 unique IDs
     * 
     */
    @JsonProperty("no_o_id")
    private final Integer orderId;
    /**
     * no_d_id - 20 unique IDs
     */
    @JsonProperty("no_d_id")
    private final Integer districtId;
    /**
     * no_w_id - 2*W unique IDs
     */
    @JsonProperty("no_w_id")
    private final Integer warehouseId;

    private NewOrder(Builder builder) {
        this.orderId = builder.orderId;
        this.districtId = builder.districtId;
        this.warehouseId = builder.warehouseId;
        this.key = key(warehouseId, districtId, orderId);
        this.secondaryKeys = ImmutableSet.of(districtKey(warehouseId, districtId));
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

    public static Tuple key(Integer warehouseId, Integer districtId, Integer orderId) {
        return Quartet.with(MODEL_TYPE, warehouseId, districtId, orderId);
    }

    public static Tuple districtKey(Integer warehouseId, Integer districtId) {
        return Triplet.with(MODEL_TYPE, warehouseId, districtId);
    }

    public static Builder builder() {
        return new Builder();
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
        NewOrder other = (NewOrder) obj;
        return Objects.equals(key, other.key);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NewOrder.class.getSimpleName() + "[", "]").add("key=" + key)
                .add("secondaryKeys=" + secondaryKeys)
                .add("orderId=" + orderId)
                .add("districtId=" + districtId)
                .add("warehouseId=" + warehouseId)
                .toString();
    }

    @JsonPOJOBuilder
    public static class Builder {
        @JsonProperty("no_o_id")
        private Integer orderId;
        @JsonProperty("no_d_id")
        private Integer districtId;
        @JsonProperty("no_w_id")
        private Integer warehouseId;

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

        public NewOrder build() {
            return new NewOrder(this);
        }
    }

}
