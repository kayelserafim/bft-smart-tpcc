package bftsmart.microbenchmark.tpcc.monitor;

import java.time.Duration;
import java.util.StringJoiner;

import bftsmart.microbenchmark.tpcc.probject.TransactionType;
import bftsmart.microbenchmark.tpcc.util.Numbers;

public class BenchResult {

    private final TransactionType transactionType;
    private final Duration elapsed;
    private final Integer size;
    private final Long totalErrors;

    public BenchResult(TransactionType transactionType, Duration elapsed, Integer size, Long totalErrors) {
        this.transactionType = transactionType;
        this.elapsed = elapsed;
        this.size = size;
        this.totalErrors = totalErrors;
    }

    public TransactionType getTransactionType() {
        return transactionType;
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
        return new StringJoiner(", ", BenchResult.class.getSimpleName() + "[", "]")
                .add("transactionType=" + transactionType)
                .add("elapsed=" + elapsed)
                .add("size=" + size)
                .add("totalErrors=" + totalErrors)
                .toString();
    }
}
