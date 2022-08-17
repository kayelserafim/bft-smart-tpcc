package bftsmart.microbenchmark.tpcc.table;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import bftsmart.microbenchmark.tpcc.table.key.HistoryKey;

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
 * benchmark, there is no need to uniquely id entity a row within this table.
 * </p>
 * 
 * <p>
 * <b>Note:</b> The TPC-C application does not have to be capable of utilizing
 * the increased range of C_ID
 * values beyond 6,000.
 * </p>
 */
public class History implements Externalizable {

    public static final TableType TABLE_TYPE = TableType.HISTORY;

    /**
     * h_c_id - 96,000 unique IDs
     */
    @JsonProperty("h_c_id")
    private int customerId;
    /**
     * h_c_d_id - 20 unique IDs
     */
    @JsonProperty("h_c_d_id")
    private int customerDistrictId;
    /**
     * h_c_w_id - 2*W unique IDs
     */
    @JsonProperty("h_c_w_id")
    private int customerWarehouseId;
    /**
     * h_d_id - 20 unique IDs
     */
    @JsonProperty("h_d_id")
    private int districtId;
    /**
     * h_w_id - 2*W unique IDs
     */
    @JsonProperty("h_w_id")
    private int warehouseId;
    /**
     * h_date - date and time
     */
    @JsonProperty("h_date")
    private long date;
    /**
     * h_amount - signed numeric(6, 2)
     */
    @JsonProperty("h_amount")
    private BigDecimal amount;
    /**
     * h_data - variable text, size 24
     */
    @JsonProperty("h_data")
    private String data;

    public HistoryKey createKey() {
        return new HistoryKey(warehouseId, districtId);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public History withCustomerId(int customerId) {
        setCustomerId(customerId);
        return this;
    }

    public int getCustomerDistrictId() {
        return customerDistrictId;
    }

    public void setCustomerDistrictId(int customerDistrictId) {
        this.customerDistrictId = customerDistrictId;
    }

    public History withCustomerDistrictId(int customerDistrictId) {
        setCustomerDistrictId(customerDistrictId);
        return this;
    }

    public int getCustomerWarehouseId() {
        return customerWarehouseId;
    }

    public void setCustomerWarehouseId(int customerWarehouseId) {
        this.customerWarehouseId = customerWarehouseId;
    }

    public History withCustomerWarehouseId(int customerWarehouseId) {
        setCustomerWarehouseId(customerWarehouseId);
        return this;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public History withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public History withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public History withDate(long date) {
        setDate(date);
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public History withAmount(BigDecimal amount) {
        setAmount(amount);
        return this;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public History withData(String data) {
        setData(data);
        return this;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.customerId = in.readInt();
        this.customerDistrictId = in.readInt();
        this.customerWarehouseId = in.readInt();
        this.districtId = in.readInt();
        this.date = in.readLong();
        this.amount = BigDecimal.valueOf(in.readDouble());
        this.data = in.readUTF();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(customerId);
        out.writeInt(customerDistrictId);
        out.writeInt(customerWarehouseId);
        out.writeInt(districtId);
        out.writeLong(date);
        out.writeDouble(amount.doubleValue());
        out.writeUTF(data);
    }

}
