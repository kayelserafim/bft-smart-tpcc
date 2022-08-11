package bftsmart.microbenchmark.tpcc.table;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <ol>
 * <li>Primary Key: (O_W_ID, O_D_ID, O_ID)</li>
 * <li>(O_W_ID, O_D_ID, O_C_ID) Foreign Key, references (C_W_ID, C_D_ID,
 * C_ID)</li>
 * </ol>
 */
public class Order implements Externalizable {

    public static final TableType MODEL_TYPE = TableType.ORDER;

    /**
     * o_id - 10,000,000 unique IDs
     */
    @JsonProperty("o_id")
    private int orderId;
    /**
     * o_d_id - 20 unique IDs
     */
    @JsonProperty("o_d_id")
    private int districtId;
    /**
     * o_w_id - 2*W unique IDs
     */
    @JsonProperty("o_w_id")
    private int warehouseId;
    /**
     * o_c_id - 96,000 unique IDs
     */
    @JsonProperty("o_c_id")
    private int customerId;
    /**
     * o_entry_d - date and time
     */
    @JsonProperty("o_entry_d")
    private long entryDate;
    /**
     * o_carrier_id - 10 unique IDs, or null
     */
    @JsonProperty("o_carrier_id")
    private int carrierId;
    /**
     * o_ol_cnt - numeric(2) - Count of Order-Lines
     */
    @JsonProperty("o_ol_cnt")
    private int orderLineCounter;
    /**
     * o_all_local - numeric(1)
     */
    @JsonProperty("o_all_local")
    private int allLocal;

    public OrderKey createKey() {
        return new OrderKey(warehouseId, districtId, orderId);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Order withOrderId(int orderId) {
        setOrderId(orderId);
        return this;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public Order withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Order withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Order withCustomerId(int customerId) {
        setCustomerId(customerId);
        return this;
    }

    public long getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(long entryDate) {
        this.entryDate = entryDate;
    }

    public Order withEntryDate(long entryDate) {
        setEntryDate(entryDate);
        return this;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }

    public Order withCarrierId(int carrierId) {
        setCarrierId(carrierId);
        return this;
    }

    public int getOrderLineCounter() {
        return orderLineCounter;
    }

    public void setOrderLineCounter(int orderLineCounter) {
        this.orderLineCounter = orderLineCounter;
    }

    public Order withOrderLineCounter(int orderLineCounter) {
        setOrderLineCounter(orderLineCounter);
        return this;
    }

    public int getAllLocal() {
        return allLocal;
    }

    public void setAllLocal(int allLocal) {
        this.allLocal = allLocal;
    }

    public Order withAllLocal(int allLocal) {
        setAllLocal(allLocal);
        return this;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.orderId = in.readInt();
        this.districtId = in.readInt();
        this.warehouseId = in.readInt();
        this.customerId = in.readInt();
        this.entryDate = in.readLong();
        this.carrierId = in.readInt();
        this.orderLineCounter = in.readInt();
        this.allLocal = in.readInt();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(orderId);
        out.writeInt(districtId);
        out.writeInt(warehouseId);
        out.writeInt(customerId);
        out.writeLong(entryDate);
        out.writeInt(carrierId);
        out.writeInt(orderLineCounter);
        out.writeInt(allLocal);
    }

    public static class OrderKey implements Externalizable, Comparable<OrderKey> {
        private int warehouseId;
        private int districtId;
        private int orderId;

        public OrderKey() {
            super();
        }

        public OrderKey(int warehouseId, int districtId, int orderId) {
            this.warehouseId = warehouseId;
            this.districtId = districtId;
            this.orderId = orderId;
        }

        public int getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(int warehouseId) {
            this.warehouseId = warehouseId;
        }

        public int getDistrictId() {
            return districtId;
        }

        public void setDistrictId(int districtId) {
            this.districtId = districtId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            this.warehouseId = in.readInt();
            this.districtId = in.readInt();
            this.orderId = in.readInt();
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeInt(warehouseId);
            out.writeInt(districtId);
            out.writeInt(orderId);
        }

        @Override
        public int compareTo(OrderKey key) {
            return new CompareToBuilder().append(getWarehouseId(), key.getWarehouseId())
                    .append(getDistrictId(), key.getDistrictId())
                    .append(getOrderId(), key.getOrderId())
                    .toComparison();
        }

        @Override
        public int hashCode() {
            return Objects.hash(districtId, orderId, warehouseId);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            OrderKey other = (OrderKey) obj;
            return districtId == other.districtId && orderId == other.orderId && warehouseId == other.warehouseId;
        }

    }

}
