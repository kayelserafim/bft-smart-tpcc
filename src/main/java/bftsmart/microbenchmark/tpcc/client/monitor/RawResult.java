package bftsmart.microbenchmark.tpcc.client.monitor;

import java.time.Duration;

import bftsmart.microbenchmark.tpcc.probject.TransactionType;

public class RawResult {

    private Integer terminalId;
    private String terminalName;
    private String commandId;
    private TransactionType transactionType;
    private Boolean conflict;
    private Duration elapsed;
    private Integer status;
    private String message;

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public RawResult terminalId(Integer terminalId) {
        setTerminalId(terminalId);
        return this;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public RawResult terminalName(String terminalName) {
        setTerminalName(terminalName);
        return this;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public RawResult commandId(String commandId) {
        setCommandId(commandId);
        return this;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public RawResult transactionType(TransactionType transactionType) {
        setTransactionType(transactionType);
        return this;
    }

    public Boolean getConflict() {
        return conflict;
    }

    public void setConflict(Boolean conflict) {
        this.conflict = conflict;
    }

    public RawResult conflict(Boolean conflict) {
        setConflict(conflict);
        return this;
    }

    public Duration getElapsed() {
        return elapsed;
    }

    public void setElapsed(Duration elapsed) {
        this.elapsed = elapsed;
    }

    public RawResult elapsed(Duration elapsed) {
        setElapsed(elapsed);
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public RawResult status(Integer status) {
        setStatus(status);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RawResult message(String message) {
        setMessage(message);
        return this;
    }

}
