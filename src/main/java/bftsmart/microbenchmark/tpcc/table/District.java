package bftsmart.microbenchmark.tpcc.table;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import bftsmart.microbenchmark.tpcc.table.key.DistrictKey;

/**
 * <ol>
 * <li>Primary Key: (D_W_ID, D_ID)</li>
 * <li>D_W_ID Foreign Key, references W_ID</li>
 * </ol>
 */
public class District implements Externalizable {

    private static final AtomicInteger NEXT_ORDER_ID = new AtomicInteger();

    public static final TableType TABLE_TYPE = TableType.DISTRICT;

    /**
     * d_id
     */
    @JsonProperty("d_id")
    private int districtId;
    /**
     * d_w_id - 2*W unique IDs
     */
    @JsonProperty("d_w_id")
    private int warehouseId;
    /**
     * d_name - variable text, size 10
     */
    @JsonProperty("d_name")
    private String name;
    /**
     * d_street_1 - variable text, size 20
     */
    @JsonProperty("d_street_1")
    private String street1;
    /**
     * d_street_2 - variable text, size 20
     */
    @JsonProperty("d_street_2")
    private String street2;
    /**
     * d_city - variable text, size 20
     */
    @JsonProperty("d_city")
    private String city;
    /**
     * d_state - fixed text, size 2
     */
    @JsonProperty("d_state")
    private String state;
    /**
     * d_zip - fixed text, size 9
     */
    @JsonProperty("d_zip")
    private String zip;
    /**
     * d_tax - Sales tax - signed numeric(4,4)
     */
    @JsonProperty("d_tax")
    private BigDecimal tax;
    /**
     * d_ytd - Year to date balance - signed numeric(12,2)
     */
    @JsonProperty("d_ytd")
    private BigDecimal yearToDateBalance;
    /**
     * d_next_o_id - Next available Order number - 10,000,000 unique IDs
     */
    @JsonProperty("d_next_o_id")
    private int nextOrderId;

    public DistrictKey createKey() {
        return new DistrictKey(warehouseId, districtId);
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public District withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public District withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public District withName(String name) {
        setName(name);
        return this;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public District withStreet1(String street1) {
        setStreet1(street1);
        return this;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public District withStreet2(String street2) {
        setStreet2(street2);
        return this;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public District withCity(String city) {
        setCity(city);
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public District withState(String state) {
        setState(state);
        return this;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public District withZip(String zip) {
        setZip(zip);
        return this;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public District withTax(BigDecimal tax) {
        setTax(tax);
        return this;
    }

    public BigDecimal getYearToDateBalance() {
        return yearToDateBalance;
    }

    public void setYearToDateBalance(BigDecimal yearToDateBalance) {
        this.yearToDateBalance = yearToDateBalance;
    }

    public District withYearToDateBalance(BigDecimal yearToDateBalance) {
        setYearToDateBalance(yearToDateBalance);
        return this;
    }

    public int getNextOrderId() {
        return nextOrderId;
    }

    public void setNextOrderId(int nextOrderId) {
        this.nextOrderId = nextOrderId;
    }

    public District withNextOrderId(int nextOrderId) {
        setNextOrderId(nextOrderId);
        return this;
    }

    public District nextOrderIdIncrement() {
        NEXT_ORDER_ID.compareAndSet(0, nextOrderId);
        this.nextOrderId = NEXT_ORDER_ID.getAndIncrement();
        return this;
    }

    public District addYearToDateBalance(BigDecimal amount) {
        this.yearToDateBalance = Optional.ofNullable(yearToDateBalance).orElse(BigDecimal.ZERO).add(amount);
        return this;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.districtId = in.readInt();
        this.warehouseId = in.readInt();
        this.name = in.readUTF();
        this.street1 = in.readUTF();
        this.street2 = in.readUTF();
        this.city = in.readUTF();
        this.state = in.readUTF();
        this.zip = in.readUTF();
        this.tax = BigDecimal.valueOf(in.readDouble());
        this.yearToDateBalance = BigDecimal.valueOf(in.readDouble());
        this.nextOrderId = in.readInt();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(districtId);
        out.writeInt(warehouseId);
        out.writeUTF(name);
        out.writeUTF(street1);
        out.writeUTF(street2);
        out.writeUTF(city);
        out.writeUTF(state);
        out.writeUTF(zip);
        out.writeDouble(tax.doubleValue());
        out.writeDouble(yearToDateBalance.doubleValue());
        out.writeInt(nextOrderId);
    }

}
