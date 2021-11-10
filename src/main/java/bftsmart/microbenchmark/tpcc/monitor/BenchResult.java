package bftsmart.microbenchmark.tpcc.monitor;

import java.time.Duration;

import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;
import bftsmart.microbenchmark.tpcc.util.Numbers;

public class BenchResult {

    private final TPCCCommandType commandType;
    private final Duration elapsed;
    private final Integer size;
    private final Long totalErrors;

    public BenchResult(TPCCCommandType commandType, Duration elapsed, Integer size, Long totalErrors) {
        this.commandType = commandType;
        this.elapsed = elapsed;
        this.size = size;
        this.totalErrors = totalErrors;
    }

    public TPCCCommandType getCommandType() {
        return commandType;
    }

    public Duration getElapsed() {
        return elapsed;
    }

    public Integer getSize() {
        return size;
    }

    public Long getTotalErrors() {
        return totalErrors;
    }

    public Double getThroughput() {
        return Numbers.divide(getSize(), getElapsed().toMillis());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Result [commandType=")
                .append(commandType)
                .append(", elapsed=")
                .append(elapsed)
                .append(", size=")
                .append(size)
                .append("]");
        return builder.toString();
    }

}
