package bftsmart.microbenchmark.tpcc.monitor;

import java.time.Duration;

import com.google.common.base.Stopwatch;

import bftsmart.microbenchmark.tpcc.probject.TransactionType;

public class RawResult {

    private int terminalId;
    private String terminalName;
    private TransactionType transactionType;
    private Duration elapsed;
    private Integer status;
    private String message;
    private final Stopwatch stopwatch;

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

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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

    public void setStatus(Integer result) {
        this.status = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RawResult [terminalId=")
                .append(terminalId)
                .append(", terminalName=")
                .append(terminalName)
                .append(", commandType=")
                .append(transactionType)
                .append(", elapsed=")
                .append(elapsed)
                .append(", status=")
                .append(status)
                .append(", message=")
                .append(message)
                .append(", stopwatch=")
                .append(stopwatch)
                .append("]");
        return builder.toString();
    }

}
