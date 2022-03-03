package bftsmart.microbenchmark.tpcc.table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import org.javatuples.Pair;
import org.javatuples.Tuple;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import bftsmart.microbenchmark.tpcc.probject.ModelType;
import bftsmart.microbenchmark.tpcc.probject.PRObject;

/**
 * <ol>
 * <li>Primary Key: I_ID</li>
 * </ol>
 */
@JsonDeserialize(builder = Item.Builder.class)
public class Item implements PRObject {

    private static final long serialVersionUID = -187222444516578203L;

    private static final ModelType MODEL_TYPE = ModelType.ITEM;

    private final Tuple key;
    private final Set<Tuple> secondaryKeys;

    /**
     * i_id
     */
    @JsonProperty("i_id")
    private final Integer itemId;
    /**
     * i_im_id - 200,000 unique IDs - Image ID associated to Item
     */
    @JsonProperty("i_im_id")
    private final Integer imageId;
    /**
     * i_name - variable text, size 24
     */
    @JsonProperty("i_name")
    private final String name;
    /**
     * i_price - numeric(5, 2)
     */
    @JsonProperty("i_price")
    private final BigDecimal price;
    /**
     * i_data - variable text, size 50 - Brand information
     */
    @JsonProperty("i_data")
    private final String data;

    private Item(Builder builder) {
        this.itemId = builder.itemId;
        this.imageId = builder.imageId;
        this.name = builder.name;
        this.price = builder.price.setScale(2, RoundingMode.HALF_UP);
        this.data = builder.data;
        this.key = key(itemId);
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

    public Integer getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getData() {
        return data;
    }

    public static Tuple key(Integer itemId) {
        return Pair.with(MODEL_TYPE, itemId);
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
        Item other = (Item) obj;
        return Objects.equals(key, other.key);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Item.class.getSimpleName() + "[", "]").add("key=" + key)
                .add("secondaryKeys=" + secondaryKeys)
                .add("itemId=" + itemId)
                .add("imageId=" + imageId)
                .add("name='" + name + "'")
                .add("price=" + price)
                .add("data='" + data + "'")
                .toString();
    }

    @JsonPOJOBuilder
    public static class Builder {
        @JsonProperty("i_id")
        private Integer itemId;
        @JsonProperty("i_im_id")
        private Integer imageId;
        @JsonProperty("i_name")
        private String name;
        @JsonProperty("i_price")
        private BigDecimal price;
        @JsonProperty("i_data")
        private String data;

        public Builder itemId(Integer itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder imageId(Integer imageId) {
            this.imageId = imageId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }

}