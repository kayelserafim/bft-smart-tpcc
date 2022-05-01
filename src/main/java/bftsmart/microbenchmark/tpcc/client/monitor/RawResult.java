package bftsmart.microbenchmark.tpcc.client.monitor;

import java.time.Duration;

import com.google.common.base.Stopwatch;

import bftsmart.microbenchmark.tpcc.probject.TransactionType;

public class RawResult {

    private final Stopwatch stopwatch;
    private final int terminalId;
    private final String terminalName;
    private String commandId;
    private TransactionType transactionType;
    private Boolean conflict;
    private Duration elapsed;
    private Integer status;
    private String message;

    public RawResult(int terminalId, String terminalName) {
        this.stopwatch = Stopwatch.createStarted();
        this.terminalId = terminalId;
        this.terminalName = terminalName;
        this.message = "Request Sent";
    }

    public void stop() {
        stopwatch.stop();
        elapsed = stopwatch.elapsed();
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Boolean getConflict() {
        return conflict;
    }

    public void setConflict(Boolean conflict) {
        this.conflict = conflict;
    }

    public Duration getElapsed() {
        return elapsed;
    }

    public void setElapsed(Duration elapsed) {
        this.elapsed = elapsed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Stopwatch getStopwatch() {
        return stopwatch;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public String getTerminalName() {
        return terminalName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RawResult [stopwatch=")
                .append(stopwatch)
                .append(", terminalId=")
                .append(terminalId)
                .append(", terminalName=")
                .append(terminalName)
                .append(", commandId=")
                .append(commandId)
                .append(", transactionType=")
                .append(transactionType)
                .append(", conflict=")
                .append(conflict)
                .append(", elapsed=")
                .append(elapsed)
                .append(", status=")
                .append(status)
                .append(", message=")
                .append(message)
                .append(']');
        return builder.toString();
    }

}
