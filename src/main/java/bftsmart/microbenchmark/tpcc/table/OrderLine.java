package bftsmart.microbenchmark.tpcc.table;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import bftsmart.microbenchmark.tpcc.table.key.OrderLineKey;

/**
 * <ol>
 * <li>Primary Key: (OL_W_ID, OL_D_ID, OL_O_ID, OL_NUMBER)</li>
 * <li>(OL_W_ID, OL_D_ID, OL_O_ID) Foreign Key, references (O_W_ID, O_D_ID,
 * O_ID)</li>
 * <li>(OL_SUPPLY_W_ID, OL_I_ID) Foreign Key, references (S_W_ID, S_I_ID)</li>
 * </ol>
 */
public class OrderLine implements Externalizable {

    public static final TableType MODEL_TYPE = TableType.ORDER_LINE;

    /**
     * ol_o_id - 10,000,000 unique IDs
     */
    @JsonProperty("ol_o_id")
    private int orderId;
    /**
     * ol_d_id - 20 unique IDs
     */
    @JsonProperty("ol_d_id")
    private int districtId;
    /**
     * ol_w_id - 2*W unique IDs
     */
    @JsonProperty("ol_w_id")
    private int warehouseId;
    /**
     * ol_number - 15 unique IDs
     */
    @JsonProperty("ol_number")
    private int number;
    /**
     * ol_i_id - 200,000 unique IDs
     */
    @JsonProperty("ol_i_id")
    private int itemId;
    /**
     * ol_supply_w_id - 2*W unique IDs
     */
    @JsonProperty("ol_supply_w_id")
    private int supplyWarehouseId;
    /**
     * ol_delivery_d - date and time, or null
     */
    @JsonProperty("ol_delivery_d")
    private long deliveryDateTime;
    /**
     * ol_quantity - numeric(2)
     */
    @JsonProperty("ol_quantity")
    private int quantity;
    /**
     * ol_amount - signed numeric(6, 2)
     */
    @JsonProperty("ol_amount")
    private BigDecimal amount;
    /**
     * ol_dist_info - fixed text, size 24
     */
    @JsonProperty("ol_dist_info")
    private String districtInfo;

    public OrderLineKey createKey() {
        return new OrderLineKey(warehouseId, districtId, orderId, number);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public OrderLine withOrderId(int orderId) {
        setOrderId(orderId);
        return this;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public OrderLine withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public OrderLine withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public OrderLine withNumber(int number) {
        setNumber(number);
        return this;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public OrderLine withItemId(int itemId) {
        setItemId(itemId);
        return this;
    }

    public int getSupplyWarehouseId() {
        return supplyWarehouseId;
    }

    public void setSupplyWarehouseId(int supplyWarehouseId) {
        this.supplyWarehouseId = supplyWarehouseId;
    }

    public OrderLine withSupplyWarehouseId(int supplyWarehouseId) {
        setSupplyWarehouseId(supplyWarehouseId);
        return this;
    }

    public long getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(long deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public OrderLine withDeliveryDateTime(long deliveryDateTime) {
        setDeliveryDateTime(deliveryDateTime);
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderLine withQuantity(int quantity) {
        setQuantity(quantity);
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OrderLine withAmount(BigDecimal amount) {
        setAmount(amount);
        return this;
    }

    public String getDistrictInfo() {
        return districtInfo;
    }

    public void setDistrictInfo(String districtInfo) {
        this.districtInfo = districtInfo;
    }

    public OrderLine withDistrictInfo(String districtInfo) {
        setDistrictInfo(districtInfo);
        return this;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.orderId = in.readInt();
        this.districtId = in.readInt();
        this.warehouseId = in.readInt();
        this.number = in.readInt();
        this.itemId = in.readInt();
        this.supplyWarehouseId = in.readInt();
        this.deliveryDateTime = in.readLong();
        this.quantity = in.readInt();
        this.amount = BigDecimal.valueOf(in.readDouble());
        this.districtInfo = in.readUTF();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(orderId);
        out.writeInt(districtId);
        out.writeInt(warehouseId);
        out.writeInt(number);
        out.writeInt(itemId);
        out.writeInt(supplyWarehouseId);
        out.writeLong(deliveryDateTime);
        out.writeInt(quantity);
        out.writeDouble(amount.doubleValue());
        out.writeUTF(districtInfo);
    }

}