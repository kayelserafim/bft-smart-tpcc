package bftsmart.microbenchmark.tpcc.table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

import org.javatuples.Pair;
import org.javatuples.Tuple;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import bftsmart.microbenchmark.tpcc.domain.ModelType;
import bftsmart.microbenchmark.tpcc.domain.Persistable;

/**
 * Primary Key: W_ID
 * 
 * @author kayel
 *
 */
@JsonDeserialize(builder = Warehouse.Builder.class)
public class Warehouse implements Persistable {

    private static final ModelType MODEL_TYPE = ModelType.WAREHOUSE;

    private final Tuple key;
    private final Set<Tuple> secondaryKeys;

    /**
     * w_id - 2*W unique IDs
     */
    @JsonProperty("w_id")
    private final Integer warehouseId;
    /**
     * w_name - variable text, size 10
     */
    @JsonProperty("w_name")
    private final String name;
    /**
     * w_street_1 - variable text, size 20
     */
    @JsonProperty("w_street_1")
    private final String street1;
    /**
     * w_street_2 - variable text, size 20
     */
    @JsonProperty("w_street_2")
    private final String street2;
    /**
     * w_city - variable text, size 20
     */
    @JsonProperty("w_city")
    private final String city;
    /**
     * w_state - fixed text, size 2
     */
    @JsonProperty("w_state")
    private final String state;
    /**
     * w_zip - fixed text, size 9
     */
    @JsonProperty("w_zip")
    private final String zip;
    /**
     * w_tax - Sales tax - signed numeric(4,4)
     */
    @JsonProperty("w_tax")
    private final BigDecimal tax;
    /**
     * w_ytd - Year to date balance - signed numeric(12,2)
     */
    @JsonProperty("w_ytd")
    private final BigDecimal yearToDateBalance;

    private Warehouse(Builder builder) {
        this.warehouseId = builder.warehouseId;
        this.name = builder.name;
        this.street1 = builder.street1;
        this.street2 = builder.street2;
        this.city = builder.city;
        this.state = builder.state;
        this.zip = builder.zip;
        this.tax = builder.tax.setScale(4, RoundingMode.HALF_UP);
        this.yearToDateBalance = builder.yearToDateBalance.setScale(2, RoundingMode.HALF_UP);
        this.key = key(warehouseId);
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

    public static Tuple key(Integer warehouseId) {
        return Pair.with(MODEL_TYPE, warehouseId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder from(Warehouse warehouse) {
        return new Builder(warehouse);
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
        Warehouse other = (Warehouse) obj;
        return Objects.equals(key, other.key);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Warehouse.class.getSimpleName() + "[", "]").add("key=" + key)
                .add("secondaryKeys=" + secondaryKeys)
                .add("warehouseId=" + warehouseId)
                .add("name='" + name + "'")
                .add("street1='" + street1 + "'")
                .add("street2='" + street2 + "'")
                .add("city='" + city + "'")
                .add("state='" + state + "'")
                .add("zip='" + zip + "'")
                .add("tax=" + tax)
                .add("yearToDateBalance=" + yearToDateBalance)
                .toString();
    }

    @JsonPOJOBuilder
    public static class Builder {

        @JsonProperty("w_id")
        private Integer warehouseId;
        @JsonProperty("w_name")
        private String name;
        @JsonProperty("w_street_1")
        private String street1;
        @JsonProperty("w_street_2")
        private String street2;
        @JsonProperty("w_city")
        private String city;
        @JsonProperty("w_state")
        private String state;
        @JsonProperty("w_zip")
        private String zip;
        @JsonProperty("w_tax")
        private BigDecimal tax;
        @JsonProperty("w_ytd")
        private BigDecimal yearToDateBalance;

        public Builder() {
            super();
        }

        public Builder(Warehouse warehouse) {
            this.warehouseId = warehouse.warehouseId;
            this.name = warehouse.name;
            this.street1 = warehouse.street1;
            this.street2 = warehouse.street2;
            this.city = warehouse.city;
            this.state = warehouse.state;
            this.zip = warehouse.zip;
            this.tax = warehouse.tax;
            this.yearToDateBalance = warehouse.yearToDateBalance;
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

        public Warehouse build() {
            return new Warehouse(this);
        }
    }

}
