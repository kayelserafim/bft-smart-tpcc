package bftsmart.microbenchmark.tpcc.probject;

import java.io.Serializable;
import java.util.StringJoiner;
import java.util.UUID;

import org.apache.commons.lang3.SerializationUtils;

import bftsmart.util.MultiOperationRequest;
import bftsmart.util.MultiOperationResponse;

public class TPCCCommand implements Serializable {

    private static final long serialVersionUID = 6569180706287210660L;

    private final String commandId;
    private final TransactionType transactionType;
    private final Serializable request;
    private final Integer status;
    private final Boolean conflict;
    private final String response;

    public TPCCCommand(Builder builder) {
        this.commandId = builder.commandId == null ? UUID.randomUUID().toString() : builder.commandId;
        this.transactionType = builder.transactionType;
        this.request = builder.request;
        this.response = builder.response;
        this.conflict = builder.conflict;
        this.status = builder.status;
    }

    public String getCommandId() {
        return commandId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Serializable getRequest() {
        return request;
    }

    public Integer getStatus() {
        return status;
    }

    public Boolean getConflict() {
        return conflict;
    }

    public String getResponse() {
        return response;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder from(TPCCCommand command) {
        return new Builder(command);
    }

    public static class Builder {
        private String commandId;
        private TransactionType transactionType;
        private Serializable request;
        private Integer status;
        private Boolean conflict;
        private String response;

        public Builder() {
            super();
        }

        public Builder(TPCCCommand command) {
            this.commandId = command.commandId;
            this.transactionType = command.transactionType;
            this.request = command.request;
            this.status = command.status;
            this.conflict = command.conflict;
            this.response = command.response;
        }

        public Builder commandId(String commandId) {
            this.commandId = commandId;
            return this;
        }

        public Builder transactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder request(Serializable request) {
            this.request = request;
            return this;
        }

        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder conflict(Boolean conflict) {
            this.conflict = conflict;
            return this;
        }

        public Builder response(String response) {
            this.response = response;
            return this;
        }

        public TPCCCommand build() {
            return new TPCCCommand(this);
        }

    }

    public byte[] serialize() {
        return SerializationUtils.serialize(this);
    }

    public byte[] serialize(MultiOperationRequest request) {
        request.add(0, serialize(), transactionType.getClassId());
        return request.serialize();
    }

    public static TPCCCommand deserialize(byte[] bytes) {
        return SerializationUtils.deserialize(bytes);
    }

    public static TPCCCommand deserialize(MultiOperationRequest request) {
        return deserialize(request.operations[0].data);
    }

    public static TPCCCommand deserialize(MultiOperationResponse response) {
        return deserialize(response.operations[0].data);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TPCCCommand.class.getSimpleName() + "[", "]")
                .add("transactionType=" + transactionType)
                .add("request=" + request)
                .add("response='" + response + "'")
                .add("status=" + status)
                .toString();
    }

}
