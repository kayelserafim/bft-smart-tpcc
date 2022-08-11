package bftsmart.microbenchmark.tpcc.table;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <ul>
 * <li>Primary Key: (S_W_ID, S_I_ID)</li>
 * <li>S_W_ID Foreign Key, references W_ID</li>
 * <li>S_I_ID Foreign Key, references I_ID</li>
 * </ul>
 */
public class Stock implements Externalizable {

    private static final AtomicInteger ORDER_CNT = new AtomicInteger();
    private static final AtomicInteger REMOTE_CNT = new AtomicInteger();

    public static final TableType MODEL_TYPE = TableType.STOCK;

    /**
     * s_i_id - 200,000 unique IDs - 100,000 populated per warehouse
     */
    @JsonProperty("s_i_id")
    private int itemId;
    /**
     * s_w_id - 2*W unique IDs
     */
    @JsonProperty("s_w_id")
    private int warehouseId;
    /**
     * s_quantity - signed numeric(4)
     */
    @JsonProperty("s_quantity")
    private int quantity;
    /**
     * s_dist_01 - fixed text, size 24
     */
    @JsonProperty("s_dist_01")
    private String district01;
    /**
     * s_dist_02 - fixed text, size 24
     */
    @JsonProperty("s_dist_02")
    private String district02;
    /**
     * s_dist_03 - fixed text, size 24
     */
    @JsonProperty("s_dist_03")
    private String district03;
    /**
     * s_dist_04 - fixed text, size 24
     */
    @JsonProperty("s_dist_04")
    private String district04;
    /**
     * s_dist_05 - fixed text, size 24
     */
    @JsonProperty("s_dist_05")
    private String district05;
    /**
     * s_dist_06 - fixed text, size 24
     */
    @JsonProperty("s_dist_06")
    private String district06;
    /**
     * s_dist_07 - fixed text, size 24
     */
    @JsonProperty("s_dist_07")
    private String district07;
    /**
     * s_dist_08 - fixed text, size 24
     */
    @JsonProperty("s_dist_08")
    private String district08;
    /**
     * s_dist_09 - fixed text, size 24
     */
    @JsonProperty("s_dist_09")
    private String district09;
    /**
     * s_dist_10 - fixed text, size 24
     */
    @JsonProperty("s_dist_10")
    private String district10;
    /**
     * s_ytd - numeric(8)
     */
    @JsonProperty("s_ytd")
    private long yearToDate;
    /**
     * s_order_cnt - numeric(4)
     */
    @JsonProperty("s_order_cnt")
    private int orderCount;
    /**
     * s_remote_cnt - numeric(4)
     */
    @JsonProperty("s_remote_cnt")
    private int remoteCount;
    /**
     * s_data - variable text, size 50 - Make information
     */
    @JsonProperty("s_data")
    private String data;

    public StockKey createKey() {
        return new StockKey(warehouseId, itemId);
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Stock withItemId(int itemId) {
        setItemId(itemId);
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Stock withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Stock withQuantity(int quantity) {
        setQuantity(quantity);
        return this;
    }

    public String getDistrict01() {
        return district01;
    }

    public void setDistrict01(String district01) {
        this.district01 = district01;
    }

    public Stock withDistrict01(String district01) {
        setDistrict01(district01);
        return this;
    }

    public String getDistrict02() {
        return district02;
    }

    public void setDistrict02(String district02) {
        this.district02 = district02;
    }

    public Stock withDistrict02(String district02) {
        setDistrict02(district02);
        return this;
    }

    public String getDistrict03() {
        return district03;
    }

    public void setDistrict03(String district03) {
        this.district03 = district03;
    }

    public Stock withDistrict03(String district03) {
        setDistrict03(district03);
        return this;
    }

    public String getDistrict04() {
        return district04;
    }

    public void setDistrict04(String district04) {
        this.district04 = district04;
    }

    public Stock withDistrict04(String district04) {
        setDistrict04(district04);
        return this;
    }

    public String getDistrict05() {
        return district05;
    }

    public void setDistrict05(String district05) {
        this.district05 = district05;
    }

    public Stock withDistrict05(String district05) {
        setDistrict05(district05);
        return this;
    }

    public String getDistrict06() {
        return district06;
    }

    public void setDistrict06(String district06) {
        this.district06 = district06;
    }

    public Stock withDistrict06(String district06) {
        setDistrict06(district06);
        return this;
    }

    public String getDistrict07() {
        return district07;
    }

    public void setDistrict07(String district07) {
        this.district07 = district07;
    }

    public Stock withDistrict07(String district07) {
        setDistrict07(district07);
        return this;
    }

    public String getDistrict08() {
        return district08;
    }

    public void setDistrict08(String district08) {
        this.district08 = district08;
    }

    public Stock withDistrict08(String district08) {
        setDistrict08(district08);
        return this;
    }

    public String getDistrict09() {
        return district09;
    }

    public void setDistrict09(String district09) {
        this.district09 = district09;
    }

    public Stock withDistrict09(String district09) {
        setDistrict09(district09);
        return this;
    }

    public String getDistrict10() {
        return district10;
    }

    public void setDistrict10(String district10) {
        this.district10 = district10;
    }

    public Stock withDistrict10(String district10) {
        setDistrict10(district10);
        return this;
    }

    public long getYearToDate() {
        return yearToDate;
    }

    public void setYearToDate(long yearToDate) {
        this.yearToDate = yearToDate;
    }

    public Stock withYearToDate(long yearToDate) {
        setYearToDate(yearToDate);
        return this;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public Stock withOrderCount(int orderCount) {
        setOrderCount(orderCount);
        return this;
    }

    public int getRemoteCount() {
        return remoteCount;
    }

    public void setRemoteCount(int remoteCount) {
        this.remoteCount = remoteCount;
    }

    public Stock withRemoteCount(int remoteCount) {
        setRemoteCount(remoteCount);
        return this;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Stock withData(String data) {
        setData(data);
        return this;
    }

    public Stock orderCountIncrement() {
        this.orderCount = ORDER_CNT.incrementAndGet();
        return this;
    }

    public Stock remoteCntIncrement(boolean remote) {
        if (remote) {
            this.remoteCount = REMOTE_CNT.incrementAndGet();
        }
        return this;
    }

    public Stock addQuantity(int amount) {
        this.quantity = Optional.ofNullable(this.quantity).orElse(0);
        if (this.quantity - amount >= 10) {
            this.quantity = this.quantity - amount;
        } else {
            this.quantity = this.quantity - amount + 91;
        }
        return this;
    }

    public Stock addYearToDate(int amount) {
        this.yearToDate = Optional.ofNullable(yearToDate).orElse(0L) + amount;
        return this;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.itemId = in.readInt();
        this.warehouseId = in.readInt();
        this.quantity = in.readInt();
        this.district01 = in.readUTF();
        this.district02 = in.readUTF();
        this.district03 = in.readUTF();
        this.district04 = in.readUTF();
        this.district05 = in.readUTF();
        this.district06 = in.readUTF();
        this.district07 = in.readUTF();
        this.district08 = in.readUTF();
        this.district09 = in.readUTF();
        this.district10 = in.readUTF();
        this.yearToDate = in.readLong();
        this.orderCount = in.readInt();
        this.remoteCount = in.readInt();
        this.data = in.readUTF();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(itemId);
        out.writeInt(warehouseId);
        out.writeInt(quantity);
        out.writeUTF(district01);
        out.writeUTF(district02);
        out.writeUTF(district03);
        out.writeUTF(district04);
        out.writeUTF(district05);
        out.writeUTF(district06);
        out.writeUTF(district07);
        out.writeUTF(district08);
        out.writeUTF(district09);
        out.writeUTF(district10);
        out.writeLong(yearToDate);
        out.writeInt(orderCount);
        out.writeInt(remoteCount);
        out.writeUTF(data);
    }

    public static class StockKey implements Externalizable, Comparable<StockKey> {
        private int warehouseId;
        private int itemId;

        public StockKey() {
            super();
        }

        public StockKey(int warehouseId, int itemId) {
            this.warehouseId = warehouseId;
            this.itemId = itemId;
        }

        public int getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(int warehouseId) {
            this.warehouseId = warehouseId;
        }

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            this.warehouseId = in.readInt();
            this.itemId = in.readInt();
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeInt(warehouseId);
            out.writeInt(itemId);
        }

        @Override
        public int compareTo(StockKey key) {
            return new CompareToBuilder().append(getWarehouseId(), key.getWarehouseId())
                    .append(getWarehouseId(), key.getWarehouseId())
                    .toComparison();
        }

        @Override
        public int hashCode() {
            return Objects.hash(itemId, warehouseId);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            StockKey other = (StockKey) obj;
            return itemId == other.itemId && warehouseId == other.warehouseId;
        }

    }

}