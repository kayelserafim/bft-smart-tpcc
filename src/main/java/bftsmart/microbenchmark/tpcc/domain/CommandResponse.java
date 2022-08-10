package bftsmart.microbenchmark.tpcc.domain;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import bftsmart.microbenchmark.tpcc.util.KryoHelper;
import bftsmart.util.MultiOperationResponse;

public class CommandResponse implements KryoSerializable {

    private boolean conflict;
    private int status;
    private String response;

    public boolean isConflict() {
        return conflict;
    }

    public void setConflict(boolean conflict) {
        this.conflict = conflict;
    }

    public CommandResponse withConflict(boolean conflict) {
        setConflict(conflict);
        return this;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CommandResponse withStatus(int status) {
        setStatus(status);
        return this;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public CommandResponse withResponse(String response) {
        setResponse(response);
        return this;
    }

    public byte[] serialize() {
        return KryoHelper.getInstance().toBytes(this);
    }

    public byte[] serialize(MultiOperationResponse response) {
        response.add(0, serialize());
        return response.serialize();
    }

    public static CommandResponse deserialize(byte[] bytes) {
        return (CommandResponse) KryoHelper.getInstance().fromBytes(bytes);
    }

    public static CommandResponse deserialize(MultiOperationResponse response) {
        return deserialize(response.operations[0].data);
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeBoolean(conflict);
        output.writeInt(status, true);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        setConflict(input.readBoolean());
        setStatus(input.readInt(true));
    }

}
