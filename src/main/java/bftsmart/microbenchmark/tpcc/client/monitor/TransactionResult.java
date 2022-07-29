package bftsmart.microbenchmark.tpcc.client.monitor;

import java.time.Duration;

import bftsmart.microbenchmark.tpcc.probject.TransactionType;
import bftsmart.microbenchmark.tpcc.util.Numbers;

public class TransactionResult {

    private final TransactionType type;
    private final Duration elapsed;
    private final Integer size;
    private final Long totalErrors;

    public TransactionResult(TransactionType type, Duration elapsed, Integer size, Long totalErrors) {
        this.type = type;
        this.elapsed = elapsed;
        this.size = size;
        this.totalErrors = totalErrors;
    }

    public TransactionType getType() {
        return type;
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

    public Double getAverageLatency() {
        return Numbers.divide(getElapsed().toMillis(), getSize());
    }

    public Double getThroughput() {
        return Numbers.divide(getSize(), getElapsed().getSeconds());
    }

}
