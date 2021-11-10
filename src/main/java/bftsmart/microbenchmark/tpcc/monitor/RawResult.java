package bftsmart.microbenchmark.tpcc.monitor;

import java.time.Duration;

import com.google.common.base.Stopwatch;

import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;

public class RawResult {

    private int terminalId;
    private String terminalName;
    private TPCCCommandType commandType;
    private Duration elapsed;
    private Integer result;
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

    public TPCCCommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(TPCCCommandType commandType) {
        this.commandType = commandType;
    }

    public Duration getElapsed() {
        return elapsed;
    }

    public void setElapsed(Duration elapsed) {
        this.elapsed = elapsed;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RawResult [terminalId=")
                .append(terminalId)
                .append(", terminalName=")
                .append(terminalName)
                .append(", commandType=")
                .append(commandType)
                .append(", elapsed=")
                .append(elapsed)
                .append(", result=")
                .append(result)
                .append(", message=")
                .append(message)
                .append(", stopwatch=")
                .append(stopwatch)
                .append("]");
        return builder.toString();
    }

}
