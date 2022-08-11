package bftsmart.microbenchmark.tpcc.table;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Primary Key: W_ID
 * 
 */
public class Warehouse implements Externalizable {

    public static final TableType MODEL_TYPE = TableType.WAREHOUSE;

    /**
     * w_id - 2*W unique IDs
     */
    @JsonProperty("w_id")
    private int warehouseId;
    /**
     * w_name - variable text, size 10
     */
    @JsonProperty("w_name")
    private String name;
    /**
     * w_street_1 - variable text, size 20
     */
    @JsonProperty("w_street_1")
    private String street1;
    /**
     * w_street_2 - variable text, size 20
     */
    @JsonProperty("w_street_2")
    private String street2;
    /**
     * w_city - variable text, size 20
     */
    @JsonProperty("w_city")
    private String city;
    /**
     * w_state - fixed text, size 2
     */
    @JsonProperty("w_state")
    private String state;
    /**
     * w_zip - fixed text, size 9
     */
    @JsonProperty("w_zip")
    private String zip;
    /**
     * w_tax - Sales tax - signed numeric(4,4)
     */
    @JsonProperty("w_tax")
    private BigDecimal tax;
    /**
     * w_ytd - Year to date balance - signed numeric(12,2)
     */
    @JsonProperty("w_ytd")
    private BigDecimal yearToDateBalance;

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Warehouse withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Warehouse withName(String name) {
        setName(name);
        return this;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public Warehouse withStreet1(String street1) {
        setStreet1(street1);
        return this;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public Warehouse withStreet2(String street2) {
        setStreet2(street2);
        return this;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Warehouse withCity(String city) {
        setCity(city);
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Warehouse withState(String state) {
        setState(state);
        return this;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Warehouse withZip(String zip) {
        setZip(zip);
        return this;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Warehouse withTax(BigDecimal tax) {
        setTax(tax);
        return this;
    }

    public BigDecimal getYearToDateBalance() {
        return yearToDateBalance;
    }

    public void setYearToDateBalance(BigDecimal yearToDateBalance) {
        this.yearToDateBalance = yearToDateBalance;
    }

    public Warehouse withYearToDateBalance(BigDecimal yearToDateBalance) {
        setYearToDateBalance(yearToDateBalance);
        return this;
    }

    public Warehouse addYearToDateBalance(BigDecimal amount) {
        this.yearToDateBalance = Optional.ofNullable(yearToDateBalance).orElse(BigDecimal.ZERO).add(amount);
        return this;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.warehouseId = in.readInt();
        this.name = in.readUTF();
        this.street1 = in.readUTF();
        this.street2 = in.readUTF();
        this.city = in.readUTF();
        this.state = in.readUTF();
        this.zip = in.readUTF();
        this.tax = BigDecimal.valueOf(in.readDouble());
        this.yearToDateBalance = BigDecimal.valueOf(in.readDouble());
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(warehouseId);
        out.writeUTF(name);
        out.writeUTF(street1);
        out.writeUTF(street2);
        out.writeUTF(city);
        out.writeUTF(state);
        out.writeUTF(zip);
        out.writeDouble(tax.doubleValue());
        out.writeDouble(yearToDateBalance.doubleValue());
    }

}
