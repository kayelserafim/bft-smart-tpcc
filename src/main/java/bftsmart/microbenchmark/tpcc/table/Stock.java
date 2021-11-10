package bftsmart.microbenchmark.tpcc.table;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.javatuples.Triplet;
import org.javatuples.Tuple;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import bftsmart.microbenchmark.tpcc.probject.ModelType;
import bftsmart.microbenchmark.tpcc.probject.PRObject;

/**
 * <ul>
 * <li>Primary Key: (S_W_ID, S_I_ID)</li>
 * <li>S_W_ID Foreign Key, references W_ID</li>
 * <li>S_I_ID Foreign Key, references I_ID</li>
 * </ul>
 */
@JsonDeserialize(builder = Stock.Builder.class)
public class Stock implements PRObject {

    private static final long serialVersionUID = -2966437037838760265L;

    private static final ModelType MODEL_TYPE = ModelType.STOCK;

    private final Tuple key;
    private final Set<Tuple> secondaryKeys;

    /**
     * s_i_id - 200,000 unique IDs - 100,000 populated per warehouse
     */
    @JsonProperty("s_i_id")
    private final Integer itemId;
    /**
     * s_w_id - 2*W unique IDs
     */
    @JsonProperty("s_w_id")
    private final Integer warehouseId;
    /**
     * s_quantity - signed numeric(4)
     */
    @JsonProperty("s_quantity")
    private final Integer quantity;
    /**
     * s_dist_01 - fixed text, size 24
     */
    @JsonProperty("s_dist_01")
    private final String district01;
    /**
     * s_dist_02 - fixed text, size 24
     */
    @JsonProperty("s_dist_02")
    private final String district02;
    /**
     * s_dist_03 - fixed text, size 24
     */
    @JsonProperty("s_dist_03")
    private final String district03;
    /**
     * s_dist_04 - fixed text, size 24
     */
    @JsonProperty("s_dist_04")
    private final String district04;
    /**
     * s_dist_05 - fixed text, size 24
     */
    @JsonProperty("s_dist_05")
    private final String district05;
    /**
     * s_dist_06 - fixed text, size 24
     */
    @JsonProperty("s_dist_06")
    private final String district06;
    /**
     * s_dist_07 - fixed text, size 24
     */
    @JsonProperty("s_dist_07")
    private final String district07;
    /**
     * s_dist_08 - fixed text, size 24
     */
    @JsonProperty("s_dist_08")
    private final String district08;
    /**
     * s_dist_09 - fixed text, size 24
     */
    @JsonProperty("s_dist_09")
    private final String district09;
    /**
     * s_dist_10 - fixed text, size 24
     */
    @JsonProperty("s_dist_10")
    private final String district10;
    /**
     * s_ytd - numeric(8)
     */
    @JsonProperty("s_ytd")
    private final Long yearToDate;
    /**
     * s_order_cnt - numeric(4)
     */
    @JsonProperty("s_order_cnt")
    private final Integer orderCount;
    /**
     * s_remote_cnt - numeric(4)
     */
    @JsonProperty("s_remote_cnt")
    private final Integer remoteCount;
    /**
     * s_data - variable text, size 50 - Make information
     */
    @JsonProperty("s_data")
    private final String data;

    private Stock(Builder builder) {
        this.itemId = builder.itemId;
        this.warehouseId = builder.warehouseId;
        this.quantity = builder.quantity;
        this.district01 = builder.district01;
        this.district02 = builder.district02;
        this.district03 = builder.district03;
        this.district04 = builder.district04;
        this.district05 = builder.district05;
        this.district06 = builder.district06;
        this.district07 = builder.district07;
        this.district08 = builder.district08;
        this.district09 = builder.district09;
        this.district10 = builder.district10;
        this.yearToDate = builder.yearToDate;
        this.orderCount = builder.orderCount;
        this.remoteCount = builder.remoteCount;
        this.data = builder.data;
        this.key = key(warehouseId, itemId);
        this.secondaryKeys = Collections.emptySet();
    }

    @Override
    public Tuple getKey() {
        return key;
    }

    @Override
    public Set<Tuple> getSecondaryKeys() {
        return secondaryKeys;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDistrict01() {
        return district01;
    }

    public String getDistrict02() {
        return district02;
    }

    public String getDistrict03() {
        return district03;
    }

    public String getDistrict04() {
        return district04;
    }

    public String getDistrict05() {
        return district05;
    }

    public String getDistrict06() {
        return district06;
    }

    public String getDistrict07() {
        return district07;
    }

    public String getDistrict08() {
        return district08;
    }

    public String getDistrict09() {
        return district09;
    }

    public String getDistrict10() {
        return district10;
    }

    public double getYearToDate() {
        return yearToDate;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public Integer getRemoteCount() {
        return remoteCount;
    }

    public String getData() {
        return data;
    }

    public static Tuple key(Integer warehouseId, Integer itemId) {
        return Triplet.with(MODEL_TYPE, warehouseId, itemId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder from(Stock stock) {
        return new Builder(stock);
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
        Stock other = (Stock) obj;
        return Objects.equals(key, other.key);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonPOJOBuilder
    public static class Builder {
        @JsonProperty("s_i_id")
        private Integer itemId;
        @JsonProperty("s_w_id")
        private Integer warehouseId;
        @JsonProperty("s_quantity")
        private Integer quantity;
        @JsonProperty("s_dist_01")
        private String district01;
        @JsonProperty("s_dist_02")
        private String district02;
        @JsonProperty("s_dist_03")
        private String district03;
        @JsonProperty("s_dist_04")
        private String district04;
        @JsonProperty("s_dist_05")
        private String district05;
        @JsonProperty("s_dist_06")
        private String district06;
        @JsonProperty("s_dist_07")
        private String district07;
        @JsonProperty("s_dist_08")
        private String district08;
        @JsonProperty("s_dist_09")
        private String district09;
        @JsonProperty("s_dist_10")
        private String district10;
        @JsonProperty("s_ytd")
        private Long yearToDate;
        @JsonProperty("s_order_cnt")
        private Integer orderCount;
        @JsonProperty("s_remote_cnt")
        private Integer remoteCount;
        @JsonProperty("s_data")
        private String data;

        public Builder() {
            super();
        }

        public Builder(Stock stock) {
            this.itemId = stock.itemId;
            this.warehouseId = stock.warehouseId;
            this.quantity = stock.quantity;
            this.district01 = stock.district01;
            this.district02 = stock.district02;
            this.district03 = stock.district03;
            this.district04 = stock.district04;
            this.district05 = stock.district05;
            this.district06 = stock.district06;
            this.district07 = stock.district07;
            this.district08 = stock.district08;
            this.district09 = stock.district09;
            this.district10 = stock.district10;
            this.yearToDate = stock.yearToDate;
            this.orderCount = stock.orderCount;
            this.remoteCount = stock.remoteCount;
            this.data = stock.data;
        }

        public Builder itemId(Integer itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder warehouseId(Integer warehouseId) {
            this.warehouseId = warehouseId;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder addQuantity(Integer amount) {
            this.quantity = Optional.ofNullable(this.quantity).orElse(0);
            if (this.quantity - amount >= 10) {
                this.quantity = this.quantity - amount;
            } else {
                this.quantity = this.quantity - amount + 91;
            }
            return this;
        }

        public Builder district01(String district01) {
            this.district01 = district01;
            return this;
        }

        public Builder district02(String district02) {
            this.district02 = district02;
            return this;
        }

        public Builder district03(String district03) {
            this.district03 = district03;
            return this;
        }

        public Builder district04(String district04) {
            this.district04 = district04;
            return this;
        }

        public Builder district05(String district05) {
            this.district05 = district05;
            return this;
        }

        public Builder district06(String district06) {
            this.district06 = district06;
            return this;
        }

        public Builder district07(String district07) {
            this.district07 = district07;
            return this;
        }

        public Builder district08(String district08) {
            this.district08 = district08;
            return this;
        }

        public Builder district09(String district09) {
            this.district09 = district09;
            return this;
        }

        public Builder district10(String district10) {
            this.district10 = district10;
            return this;
        }

        public Builder yearToDate(Long yearToDate) {
            this.yearToDate = yearToDate;
            return this;
        }

        public Builder addYearToDate(Integer amount) {
            this.yearToDate = Optional.ofNullable(yearToDate).orElse(0L) + amount;
            return this;
        }

        public Builder orderCount(Integer orderCount) {
            this.orderCount = orderCount;
            return this;
        }

        public Builder orderCountIncrement() {
            this.orderCount = Optional.ofNullable(orderCount).orElse(0) + 1;
            return this;
        }

        public Builder remoteCount(Integer remoteCount) {
            this.remoteCount = remoteCount;
            return this;
        }

        public Builder remoteCntIncrement(boolean increment) {
            this.remoteCount = Optional.ofNullable(remoteCount).orElse(0);
            if (increment) {
                this.remoteCount = this.remoteCount + 1;
            }
            return this;
        }

        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public Stock build() {
            return new Stock(this);
        }
    }

}