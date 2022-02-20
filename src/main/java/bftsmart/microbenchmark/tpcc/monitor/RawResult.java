package bftsmart.microbenchmark.tpcc.monitor;

import java.time.Duration;
import java.util.StringJoiner;

import com.google.common.base.Stopwatch;

import bftsmart.microbenchmark.tpcc.probject.TransactionType;

public class RawResult {

    private final Stopwatch stopwatch;
    private final int terminalId;
    private final String terminalName;
    private TransactionType transactionType;
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

    public int getTerminalId() {
        return terminalId;
    }

    public String getTerminalName() {
        return terminalName;
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
        return new StringJoiner(", ", RawResult.class.getSimpleName() + "[", "]")
                .add("terminalId=" + terminalId)
                .add("terminalName='" + terminalName + "'")
                .add("transactionType=" + transactionType)
                .add("elapsed=" + elapsed)
                .add("status=" + status)
                .add("message='" + message + "'")
                .toString();
    }
}
