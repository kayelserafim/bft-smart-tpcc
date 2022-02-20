package bftsmart.microbenchmark.tpcc.probject;

import java.io.Serializable;
import java.util.StringJoiner;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

public class TPCCCommand implements Serializable {

    private static final long serialVersionUID = 6569180706287210660L;

    private TransactionType transactionType;
    private Serializable request;
    private String response;
    private Integer status = -1;

    private TPCCCommand() {
        super();
    }

    public TPCCCommand(TransactionType transactionType, Serializable request) {
        this.transactionType = transactionType;
        this.request = request;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Serializable getRequest() {
        return request;
    }

    public String getResponse() {
        return response;
    }

    public Integer getStatus() {
        return status;
    }

    public static TPCCCommand from(TPCCCommand command) {
        TPCCCommand tpccMessage = new TPCCCommand();
        if (command != null) {
            tpccMessage.transactionType = command.getTransactionType();
            tpccMessage.request = command.getRequest();
            tpccMessage.response = command.getResponse();
            tpccMessage.status = command.getStatus();
        }
        return tpccMessage;
    }

    public static TPCCCommand from(TPCCCommand command, String description) {
        TPCCCommand tpccMessage = from(command);
        tpccMessage.response += " " + description;
        return tpccMessage;
    }

    public static TPCCCommand newSuccessMessage(TPCCCommand command, String successMessage) {
        TPCCCommand tpccMessage = new TPCCCommand();
        if (command != null) {
            tpccMessage.transactionType = command.getTransactionType();
            tpccMessage.request = command.getRequest();
            tpccMessage.response = StringUtils.defaultIfBlank(successMessage, "OK");
            tpccMessage.status = 0;
        }
        return tpccMessage;
    }

    public static TPCCCommand newErrorMessage(TPCCCommand command, String errorMessage) {
        TPCCCommand tpccMessage = new TPCCCommand();
        if (command != null) {
            tpccMessage.transactionType = command.getTransactionType();
            tpccMessage.request = command.getRequest();
        }
        tpccMessage.response = errorMessage;
        tpccMessage.status = -1;
        return tpccMessage;
    }

    public byte[] getBytes() {
        return SerializationUtils.serialize(this);
    }

    public static TPCCCommand getObject(byte[] bytes) {
        return SerializationUtils.deserialize(bytes);
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
