package bftsmart.microbenchmark.tpcc.client.monitor;

import java.time.Duration;

import bftsmart.microbenchmark.tpcc.probject.TransactionType;
import bftsmart.microbenchmark.tpcc.util.Numbers;

public class TransactionResult {

    private final TransactionType type;
    private final Duration runtime;
    private final Integer size;
    private final Long totalErrors;

    public TransactionResult(TransactionType type, Duration runtime, Integer size, Long totalErrors) {
        this.type = type;
        this.runtime = runtime;
        this.size = size;
        this.totalErrors = totalErrors;
    }

    public TransactionType getType() {
        return type;
    }

    public Duration getRuntime() {
        return runtime;
    }

    public Integer getSize() {
        return size;
    }

    public Long getTotalErrors() {
        return totalErrors;
    }

    public Double getAverageLatency() {
        return Numbers.divide(getRuntime().toMillis(), getSize());
    }

    public Double getThroughput() {
        return Numbers.divide(getSize(), getRuntime().getSeconds());
    }

}
