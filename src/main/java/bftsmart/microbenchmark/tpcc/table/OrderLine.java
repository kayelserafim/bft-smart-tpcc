package bftsmart.microbenchmark.tpcc.table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import org.javatuples.Quartet;
import org.javatuples.Quintet;
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
 * <li>Primary Key: (OL_W_ID, OL_D_ID, OL_O_ID, OL_NUMBER)</li>
 * <li>(OL_W_ID, OL_D_ID, OL_O_ID) Foreign Key, references (O_W_ID, O_D_ID,
 * O_ID)</li>
 * <li>(OL_SUPPLY_W_ID, OL_I_ID) Foreign Key, references (S_W_ID, S_I_ID)</li>
 * </ol>
 */
@JsonDeserialize(builder = OrderLine.Builder.class)
public class OrderLine implements Persistable {

    private static final ModelType MODEL_TYPE = ModelType.ORDER_LINE;

    private final Tuple key;
    private final Set<Tuple> secondaryKeys;

    /**
     * ol_o_id - 10,000,000 unique IDs
     */
    @JsonProperty("ol_o_id")
    private final Integer orderId;
    /**
     * ol_d_id - 20 unique IDs
     */
    @JsonProperty("ol_d_id")
    private final Integer districtId;
    /**
     * ol_w_id - 2*W unique IDs
     */
    @JsonProperty("ol_w_id")
    private final Integer warehouseId;
    /**
     * ol_number - 15 unique IDs
     */
    @JsonProperty("ol_number")
    private final Integer number;
    /**
     * ol_i_id - 200,000 unique IDs
     */
    @JsonProperty("ol_i_id")
    private final Integer itemId;
    /**
     * ol_supply_w_id - 2*W unique IDs
     */
    @JsonProperty("ol_supply_w_id")
    private final Integer supplyWarehouseId;
    /**
     * ol_delivery_d - date and time, or null
     */
    @JsonProperty("ol_delivery_d")
    private final Long deliveryDateTime;
    /**
     * ol_quantity - numeric(2)
     */
    @JsonProperty("ol_quantity")
    private final Integer quantity;
    /**
     * ol_amount - signed numeric(6, 2)
     */
    @JsonProperty("ol_amount")
    private final BigDecimal amount;
    /**
     * ol_dist_info - fixed text, size 24
     */
    @JsonProperty("ol_dist_info")
    private final String districtInfo;

    private OrderLine(Builder builder) {
        this.orderId = builder.orderId;
        this.districtId = builder.districtId;
        this.warehouseId = builder.warehouseId;
        this.number = builder.number;
        this.itemId = builder.itemId;
        this.supplyWarehouseId = builder.supplyWarehouseId;
        this.deliveryDateTime = builder.deliveryDateTime;
        this.quantity = builder.quantity;
        this.amount = builder.amount.setScale(2, RoundingMode.HALF_UP);
        this.districtInfo = builder.districtInfo;
        this.key = key(warehouseId, districtId, orderId, number);
        this.secondaryKeys =
                ImmutableSet.of(orderKey(warehouseId, districtId, orderId), districtKey(warehouseId, districtId));
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

    public Integer getNumber() {
        return number;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getSupplyWarehouseId() {
        return supplyWarehouseId;
    }

    public Long getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDistrictInfo() {
        return districtInfo;
    }

    public static Tuple key(Integer warehouseId, Integer districtId, Integer orderId, Integer number) {
        return Quintet.with(MODEL_TYPE, warehouseId, districtId, orderId, number);
    }

    public static Tuple orderKey(Integer warehouseId, Integer districtId, Integer orderId) {
        return Quartet.with(MODEL_TYPE, warehouseId, districtId, orderId);
    }

    public static Tuple districtKey(Integer warehouseId, Integer districtId) {
        return Triplet.with(MODEL_TYPE, warehouseId, districtId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder from(OrderLine orderLine) {
        return new Builder(orderLine);
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
        OrderLine other = (OrderLine) obj;
        return Objects.equals(key, other.key);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrderLine.class.getSimpleName() + "[", "]").add("key=" + key)
                .add("secondaryKeys=" + secondaryKeys)
                .add("orderId=" + orderId)
                .add("districtId=" + districtId)
                .add("warehouseId=" + warehouseId)
                .add("number=" + number)
                .add("itemId=" + itemId)
                .add("supplyWarehouseId=" + supplyWarehouseId)
                .add("deliveryDateTime=" + deliveryDateTime)
                .add("quantity=" + quantity)
                .add("amount=" + amount)
                .add("districtInfo='" + districtInfo + "'")
                .toString();
    }

    @JsonPOJOBuilder
    public static class Builder {

        @JsonProperty("ol_o_id")
        private Integer orderId;
        @JsonProperty("ol_d_id")
        private Integer districtId;
        @JsonProperty("ol_w_id")
        private Integer warehouseId;
        @JsonProperty("ol_number")
        private Integer number;
        @JsonProperty("ol_i_id")
        private Integer itemId;
        @JsonProperty("ol_supply_w_id")
        private Integer supplyWarehouseId;
        @JsonProperty("ol_delivery_d")
        private Long deliveryDateTime;
        @JsonProperty("ol_quantity")
        private Integer quantity;
        @JsonProperty("ol_amount")
        private BigDecimal amount;
        @JsonProperty("ol_dist_info")
        private String districtInfo;

        public Builder() {
            super();
        }

        public Builder(OrderLine orderLine) {
            this.orderId = orderLine.orderId;
            this.districtId = orderLine.districtId;
            this.warehouseId = orderLine.warehouseId;
            this.number = orderLine.number;
            this.itemId = orderLine.itemId;
            this.supplyWarehouseId = orderLine.supplyWarehouseId;
            this.deliveryDateTime = orderLine.deliveryDateTime;
            this.quantity = orderLine.quantity;
            this.amount = orderLine.amount;
            this.districtInfo = orderLine.districtInfo;
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

        public Builder number(Integer number) {
            this.number = number;
            return this;
        }

        public Builder itemId(Integer itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder supplyWarehouseId(Integer supplyWarehouseId) {
            this.supplyWarehouseId = supplyWarehouseId;
            return this;
        }

        public Builder deliveryDateTime(long deliveryDateTime) {
            this.deliveryDateTime = deliveryDateTime;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder districtInfo(String districtInfo) {
            this.districtInfo = districtInfo;
            return this;
        }

        public OrderLine build() {
            return new OrderLine(this);
        }
    }

}