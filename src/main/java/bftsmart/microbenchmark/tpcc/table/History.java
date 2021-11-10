package bftsmart.microbenchmark.tpcc.table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.javatuples.Quartet;
import org.javatuples.Tuple;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import bftsmart.microbenchmark.tpcc.probject.ModelType;
import bftsmart.microbenchmark.tpcc.probject.PRObject;

/**
 * <ol>
 * <li>Primary Key: none</li>
 * <li>(H _C_W_ID, H _C_D_ID, H _C_ID) Foreign Key, references (C_W_ID,
 * C_D_ID,C_ID)</li>
 * <li>(H _W_ID, H _D_ID) Foreign Key, references (D_W_ID, D_ID)</li>
 * </ol>
 * 
 * <p>
 * <b>Comment:</b> Row s in the History table do not have a primary key as,
 * within the context of the
 * benchmark, there is no need to uniquely id entify a row within this table.
 * </p>
 * 
 * <p>
 * <b>Note:</b> The TPC-C application does not have to be capable of utilizing
 * the increased range of C_ID
 * values beyond 6,000.
 * </p>
 */
@JsonDeserialize(builder = History.Builder.class)
public class History implements PRObject {

    private static final long serialVersionUID = -955900175234829846L;

    private static final ModelType MODEL_TYPE = ModelType.HISTORY;

    private final Tuple key;
    private final Set<Tuple> secondaryKeys;

    /**
     * h_c_id - 96,000 unique IDs
     */
    @JsonProperty("h_c_id")
    private final Integer customerId;
    /**
     * h_c_d_id - 20 unique IDs
     */
    @JsonProperty("h_c_d_id")
    private final Integer customerDistrictId;
    /**
     * h_c_w_id - 2*W unique IDs
     */
    @JsonProperty("h_c_w_id")
    private final Integer customerWarehouseId;
    /**
     * h_d_id - 20 unique IDs
     */
    @JsonProperty("h_d_id")
    private final Integer districtId;
    /**
     * h_w_id - 2*W unique IDs
     */
    @JsonProperty("h_w_id")
    private final Integer warehouseId;
    /**
     * h_date - date and time
     */
    @JsonProperty("h_date")
    private final Long date;
    /**
     * h_amount - signed numeric(6, 2)
     */
    @JsonProperty("h_amount")
    private final BigDecimal amount;
    /**
     * h_data - variable text, size 24
     */
    @JsonProperty("h_data")
    private final String data;

    private History(Builder builder) {
        this.customerId = builder.customerId;
        this.customerDistrictId = builder.customerDistrictId;
        this.customerWarehouseId = builder.customerWarehouseId;
        this.districtId = builder.districtId;
        this.warehouseId = builder.warehouseId;
        this.date = builder.date;
        this.amount = builder.amount.setScale(2, RoundingMode.HALF_UP);
        this.data = builder.data;
        this.key = key(customerWarehouseId, customerDistrictId, customerId);
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

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getCustomerDistrictId() {
        return customerDistrictId;
    }

    public Integer getCustomerWarehouseId() {
        return customerWarehouseId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public Long getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getData() {
        return data;
    }

    public static Tuple key(Integer warehouseId, Integer districtId, Integer customerId) {
        return Quartet.with(MODEL_TYPE, warehouseId, districtId, customerId);
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
        History other = (History) obj;
        return Objects.equals(key, other.key);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonPOJOBuilder
    public static class Builder {
        @JsonProperty("h_c_id")
        private Integer customerId;
        @JsonProperty("h_c_d_id")
        private Integer customerDistrictId;
        @JsonProperty("h_c_w_id")
        private Integer customerWarehouseId;
        @JsonProperty("h_d_id")
        private Integer districtId;
        @JsonProperty("h_w_id")
        private Integer warehouseId;
        @JsonProperty("h_date")
        private Long date;
        @JsonProperty("h_amount")
        private BigDecimal amount;
        @JsonProperty("h_data")
        private String data;

        public Builder customerId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder customerDistrictId(Integer customerDistrictId) {
            this.customerDistrictId = customerDistrictId;
            return this;
        }

        public Builder customerWarehouseId(Integer customerWarehouseId) {
            this.customerWarehouseId = customerWarehouseId;
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

        public Builder date(Long date) {
            this.date = date;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public History build() {
            return new History(this);
        }
    }

}
