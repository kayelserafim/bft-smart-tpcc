package bftsmart.microbenchmark.tpcc.server.transaction;

import com.esotericsoftware.kryo.KryoSerializable;

import bftsmart.microbenchmark.tpcc.util.KryoHelper;
import bftsmart.util.MultiOperationRequest;

public interface TransactionRequest extends KryoSerializable {

    public String getCommandId();

    public void setCommandId(String commandId);

    public int getTransactionType();

    public void setTransactionType(int transactionType);

    default byte[] serialize() {
        return KryoHelper.getInstance().toBytes(this);
    }

    default byte[] serialize(MultiOperationRequest request) {
        request.add(0, serialize(), getTransactionType());
        return request.serialize();
    }

    static TransactionRequest deserialize(byte[] bytes) {
        return (TransactionRequest) KryoHelper.getInstance().fromBytes(bytes);
    }

    static TransactionRequest deserialize(MultiOperationRequest request) {
        return deserialize(request.operations[0].data);
    }

}
