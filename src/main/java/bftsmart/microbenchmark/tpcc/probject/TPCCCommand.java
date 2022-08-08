package bftsmart.microbenchmark.tpcc.probject;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import bftsmart.microbenchmark.tpcc.util.KryoHelper;
import bftsmart.util.MultiOperationRequest;
import bftsmart.util.MultiOperationResponse;

public class TPCCCommand implements KryoSerializable {

    private String commandId;
    private int transactionType;
    private byte[] request;
    private boolean conflict;
    private int status;
    private String response;

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public TPCCCommand withCommandId(String commandId) {
        setCommandId(commandId);
        return this;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public TPCCCommand withTransactionType(int transactionType) {
        setTransactionType(transactionType);
        return this;
    }

    public byte[] getRequest() {
        return request;
    }

    public void setRequest(byte[] request) {
        this.request = request;
    }

    public TPCCCommand withRequest(byte[] request) {
        setRequest(request);
        return this;
    }

    public boolean isConflict() {
        return conflict;
    }

    public void setConflict(boolean conflict) {
        this.conflict = conflict;
    }

    public TPCCCommand withConflict(boolean conflict) {
        setConflict(conflict);
        return this;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TPCCCommand withStatus(int status) {
        setStatus(status);
        return this;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public TPCCCommand withResponse(String response) {
        setResponse(response);
        return this;
    }

    public byte[] serialize() {
        return KryoHelper.getInstance().toBytes(this);
    }

    public byte[] serialize(MultiOperationRequest request) {
        request.add(0, serialize(), transactionType);
        return request.serialize();
    }

    public byte[] serialize(MultiOperationResponse response) {
        response.add(0, serialize());
        return response.serialize();
    }

    public static TPCCCommand deserialize(byte[] bytes) {
        return (TPCCCommand) KryoHelper.getInstance().fromBytes(bytes);
    }

    public static TPCCCommand deserialize(MultiOperationRequest request) {
        return deserialize(request.operations[0].data);
    }

    public static TPCCCommand deserialize(MultiOperationResponse response) {
        return deserialize(response.operations[0].data);
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeAscii(commandId);
        output.writeInt(transactionType, true);
        output.writeInt(request != null ? request.length : 0, true);
        if (request != null) {
            output.writeBytes(request);
        }
        output.writeBoolean(conflict);
        output.writeInt(status, true);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setCommandId(input.readString());
        setTransactionType(input.readInt(true));
        int length = input.readInt(true);
        if (length > 0) {
            setRequest(input.readBytes(length));
        }
        setConflict(input.readBoolean());
        setStatus(input.readInt(true));
    }

}
