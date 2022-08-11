package bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.input;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.annotation.JsonProperty;

import bftsmart.microbenchmark.tpcc.domain.CommandRequest;

public class StockLevelInput implements CommandRequest {

    private String commandId;
    private int transactionType;
    @JsonProperty("w_id")
    private int warehouseId;
    @JsonProperty("d_id")
    private int districtId;
    @JsonProperty("threshold")
    private int threshold;

    @Override
    public String getCommandId() {
        return commandId;
    }

    @Override
    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public StockLevelInput withCommandId(String commandId) {
        setCommandId(commandId);
        return this;
    }

    @Override
    public int getTransactionType() {
        return transactionType;
    }

    @Override
    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public StockLevelInput withTransactionType(int transactionType) {
        setTransactionType(transactionType);
        return this;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public StockLevelInput withWarehouseId(int warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public StockLevelInput withDistrictId(int districtId) {
        setDistrictId(districtId);
        return this;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public StockLevelInput withThreshold(int threshold) {
        setThreshold(threshold);
        return this;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeAscii(commandId);
        output.writeVarInt(transactionType, true);
        output.writeVarInt(warehouseId, true);
        output.writeVarInt(districtId, true);
        output.writeVarInt(threshold, true);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setCommandId(input.readString());
        setTransactionType(input.readVarInt(true));
        setWarehouseId(input.readVarInt(true));
        setDistrictId(input.readVarInt(true));
        setThreshold(input.readVarInt(true));
    }
}
