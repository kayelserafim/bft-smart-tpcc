package bftsmart.microbenchmark.tpcc.table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

import org.javatuples.Triplet;
import org.javatuples.Tuple;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import bftsmart.microbenchmark.tpcc.probject.ModelType;
import bftsmart.microbenchmark.tpcc.probject.PRObject;

/**
 * <ol>
 * <li>Primary Key: (D_W_ID, D_ID)</li>
 * <li>D_W_ID Foreign Key, references W_ID</li>
 * </ol>
 */
@JsonDeserialize(builder = District.Builder.class)
public class District implements PRObject {

    private static final long serialVersionUID = 6392498602231684536L;

    private static final AtomicInteger NEXT_ORDER_ID = new AtomicInteger();

    private static final ModelType MODEL_TYPE = ModelType.DISTRICT;

    private final Tuple key;
    private final Set<Tuple> secondaryKeys;

    /**
     * d_id
     */
    @JsonProperty("d_id")
    private final Integer districtId;
    /**
     * d_w_id - 2*W unique IDs
     */
    @JsonProperty("d_w_id")
    private final Integer warehouseId;
    /**
     * d_name - variable text, size 10
     */
    @JsonProperty("d_name")
    private final String name;
    /**
     * d_street_1 - variable text, size 20
     */
    @JsonProperty("d_street_1")
    private final String street1;
    /**
     * d_street_2 - variable text, size 20
     */
    @JsonProperty("d_street_2")
    private final String street2;
    /**
     * d_city - variable text, size 20
     */
    @JsonProperty("d_city")
    private final String city;
    /**
     * d_state - fixed text, size 2
     */
    @JsonProperty("d_state")
    private final String state;
    /**
     * d_zip - fixed text, size 9
     */
    @JsonProperty("d_zip")
    private final String zip;
    /**
     * d_tax - Sales tax - signed numeric(4,4)
     */
    @JsonProperty("d_tax")
    private final BigDecimal tax;
    /**
     * d_ytd - Year to date balance - signed numeric(12,2)
     */
    @JsonProperty("d_ytd")
    private final BigDecimal yearToDateBalance;
    /**
     * d_next_o_id - Next available Order number - 10,000,000 unique IDs
     */
    @JsonProperty("d_next_o_id")
    private final Integer nextOrderId;

    private District(Builder builder) {
        this.districtId = builder.districtId;
        this.warehouseId = builder.warehouseId;
        this.name = builder.name;
        this.street1 = builder.street1;
        this.street2 = builder.street2;
        this.city = builder.city;
        this.state = builder.state;
        this.zip = builder.zip;
        this.tax = builder.tax.setScale(4, RoundingMode.HALF_UP);
        this.yearToDateBalance = builder.yearToDateBalance.setScale(2, RoundingMode.HALF_UP);
        this.nextOrderId = builder.nextOrderId;
        this.key = key(warehouseId, districtId);
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

    public Integer getDistrictId() {
        return districtId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public String getName() {
        return name;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getYearToDateBalance() {
        return yearToDateBalance;
    }

    public Integer getNextOrderId() {
        return nextOrderId;
    }

    public static Tuple key(Integer warehouseId, Integer districtId) {
        return Triplet.with(MODEL_TYPE, warehouseId, districtId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder from(District district) {
        return new Builder(district);
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
        District other = (District) obj;
        return Objects.equals(key, other.key);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", District.class.getSimpleName() + "[", "]").add("key=" + key)
                .add("secondaryKeys=" + secondaryKeys)
                .add("districtId=" + districtId)
                .add("warehouseId=" + warehouseId)
                .add("name='" + name + "'")
                .add("street1='" + street1 + "'")
                .add("street2='" + street2 + "'")
                .add("city='" + city + "'")
                .add("state='" + state + "'")
                .add("zip='" + zip + "'")
                .add("tax=" + tax)
                .add("yearToDateBalance=" + yearToDateBalance)
                .add("nextOrderId=" + nextOrderId)
                .toString();
    }

    @JsonPOJOBuilder
    public static class Builder {
        @JsonProperty("d_id")
        private Integer districtId;
        @JsonProperty("d_w_id")
        private Integer warehouseId;
        @JsonProperty("d_name")
        private String name;
        @JsonProperty("d_street_1")
        private String street1;
        @JsonProperty("d_street_2")
        private String street2;
        @JsonProperty("d_city")
        private String city;
        @JsonProperty("d_state")
        private String state;
        @JsonProperty("d_zip")
        private String zip;
        @JsonProperty("d_tax")
        private BigDecimal tax;
        @JsonProperty("d_ytd")
        private BigDecimal yearToDateBalance;
        @JsonProperty("d_next_o_id")
        private Integer nextOrderId;

        public Builder() {
            super();
        }

        public Builder(District district) {
            this.districtId = district.districtId;
            this.warehouseId = district.warehouseId;
            this.name = district.name;
            this.street1 = district.street1;
            this.street2 = district.street2;
            this.city = district.city;
            this.state = district.state;
            this.zip = district.zip;
            this.tax = district.tax;
            this.yearToDateBalance = district.yearToDateBalance;
            this.nextOrderId = district.nextOrderId;
        }

        public Builder districtId(Integer districtId) {
            this.districtId = districtId;
            return this;
        }

        public Builder warehouseId(Integer warehouseId) {
            this.warehouseId = warehouseId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder street1(String street1) {
            this.street1 = street1;
            return this;
        }

        public Builder street2(String street2) {
            this.street2 = street2;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder zip(String zip) {
            this.zip = zip;
            return this;
        }

        public Builder tax(BigDecimal tax) {
            this.tax = tax;
            return this;
        }

        public Builder yearToDateBalance(BigDecimal yearToDateBalance) {
            this.yearToDateBalance = yearToDateBalance;
            return this;
        }

        public Builder addYearToDateBalance(BigDecimal amount) {
            this.yearToDateBalance = Optional.ofNullable(yearToDateBalance).orElse(BigDecimal.ZERO).add(amount);
            return this;
        }

        public Builder nextOrderId(Integer nextOrderId) {
            this.nextOrderId = nextOrderId;
            return this;
        }

        public Builder nextOrderIdIncrement() {
            NEXT_ORDER_ID.compareAndSet(0, nextOrderId);
            this.nextOrderId = NEXT_ORDER_ID.getAndIncrement();
            return this;
        }

        public District build() {
            return new District(this);
        }
    }

}
