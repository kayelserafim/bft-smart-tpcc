package bftsmart.microbenchmark.tpcc.domain;

import com.esotericsoftware.kryo.KryoSerializable;

import bftsmart.microbenchmark.tpcc.util.KryoHelper;
import bftsmart.util.MultiOperationRequest;

public interface CommandRequest extends KryoSerializable {

    String getCommandId();

    void setCommandId(String commandId);

    int getTransactionType();

    void setTransactionType(int transactionType);

    default byte[] serialize() {
        return KryoHelper.getInstance().toBytes(this);
    }

    default byte[] serialize(MultiOperationRequest request) {
        request.add(0, serialize(), getTransactionType());
        return request.serialize();
    }

    static CommandRequest deserialize(byte[] bytes) {
        return (CommandRequest) KryoHelper.getInstance().fromBytes(bytes);
    }

    static CommandRequest deserialize(MultiOperationRequest request) {
        return deserialize(request.operations[0].data);
    }

}
