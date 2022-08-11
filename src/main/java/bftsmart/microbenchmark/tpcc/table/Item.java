package bftsmart.microbenchmark.tpcc.table;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <ol>
 * <li>Primary Key: I_ID</li>
 * </ol>
 */
public class Item implements Externalizable {

    public static final TableType MODEL_TYPE = TableType.ITEM;

    /**
     * i_id
     */
    @JsonProperty("i_id")
    private int itemId;
    /**
     * i_im_id - 200,000 unique IDs - Image ID associated to Item
     */
    @JsonProperty("i_im_id")
    private int imageId;
    /**
     * i_name - variable text, size 24
     */
    @JsonProperty("i_name")
    private String name;
    /**
     * i_price - numeric(5, 2)
     */
    @JsonProperty("i_price")
    private BigDecimal price;
    /**
     * i_data - variable text, size 50 - Brand information
     */
    @JsonProperty("i_data")
    private String data;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Item withItemId(int itemId) {
        setItemId(itemId);
        return this;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Item withImageId(int imageId) {
        setImageId(imageId);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item withName(String name) {
        setName(name);
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Item withPrice(BigDecimal price) {
        setPrice(price);
        return this;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Item withData(String data) {
        setData(data);
        return this;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.itemId = in.readInt();
        this.imageId = in.readInt();
        this.name = in.readUTF();
        this.price = BigDecimal.valueOf(in.readDouble());
        this.data = in.readUTF();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(itemId);
        out.writeInt(imageId);
        out.writeUTF(name);
        out.writeDouble(price.doubleValue());
        out.writeUTF(data);
    }

}