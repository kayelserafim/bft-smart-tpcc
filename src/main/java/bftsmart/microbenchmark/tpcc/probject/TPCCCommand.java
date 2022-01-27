package bftsmart.microbenchmark.tpcc.probject;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

public class TPCCCommand implements Serializable {

    private static final long serialVersionUID = 6569180706287210660L;

    private TPCCCommandType commandType;
    private Map<String, Serializable> request;
    private String message;
    private Integer result = -1;

    private TPCCCommand() {
        super();
        result = -1;
    }

    public TPCCCommand(TPCCCommandType commandType, Map<String, Serializable> request) {
        this.commandType = commandType;
        this.request = request;
    }

    public TPCCCommandType getCommandType() {
        return commandType;
    }

    public Map<String, Serializable> getRequest() {
        return request;
    }

    public String getMessage() {
        return message;
    }

    public Integer getResult() {
        return result;
    }

    public static TPCCCommand from(TPCCCommand command) {
        TPCCCommand tpccMessage = new TPCCCommand();
        if (command != null) {
            tpccMessage.commandType = command.getCommandType();
            tpccMessage.request = command.getRequest();
            tpccMessage.message = command.getMessage();
            tpccMessage.result = command.getResult();
        }
        return tpccMessage;
    }

    public static TPCCCommand from(TPCCCommand command, String description) {
        TPCCCommand tpccMessage = from(command);
        tpccMessage.message += " " + description;
        return tpccMessage;
    }

    public static TPCCCommand newSuccessMessage(TPCCCommand command, String successMessage) {
        TPCCCommand tpccMessage = new TPCCCommand();
        if (command != null) {
            tpccMessage.commandType = command.getCommandType();
            tpccMessage.request = command.getRequest();
            tpccMessage.message = StringUtils.defaultIfBlank(successMessage, "OK");
            tpccMessage.result = 0;
        }
        return tpccMessage;
    }

    public static TPCCCommand newErrorMessage(TPCCCommand command, String errorMessage) {
        TPCCCommand tpccMessage = new TPCCCommand();
        if (command != null) {
            tpccMessage.commandType = command.getCommandType();
            tpccMessage.request = command.getRequest();
        }
        tpccMessage.message = errorMessage;
        tpccMessage.result = -1;
        return tpccMessage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Comand Type: ").append(commandType).append("\n");
        sb.append("Request Params:").append("\n");
        for (Entry<String, Serializable> entry : request.entrySet()) {
            sb.append("key: ").append(entry.getKey()).append(", value: ").append(entry.getValue()).append("\n");
        }
        sb.append("Result message: ").append(message).append("\n");
        sb.append("Result type: ").append(result);
        return sb.toString();
    }

    public byte[] getBytes() {
        return SerializationUtils.serialize(this);
    }

    public static TPCCCommand getObject(byte[] bytes) {
        return (TPCCCommand) SerializationUtils.deserialize(bytes);
    }

}
