package bftsmart.microbenchmark.tpcc.client.terminal;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

import bftsmart.microbenchmark.tpcc.client.monitor.RawResult;
import bftsmart.microbenchmark.tpcc.client.service.TPCCService;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class TPCCTerminal implements Callable<List<RawResult>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TPCCTerminal.class);

    private final TPCCTerminalData terminalData;
    private final TPCCRandom random;

    private final TPCCService transaction;

    public TPCCTerminal(TPCCService transaction, TPCCTerminalData terminalData, TPCCRandom random) {
        this.transaction = transaction;
        this.terminalData = terminalData;
        this.random = random;
    }

    @Override
    public List<RawResult> call() {
        List<RawResult> results = new ArrayList<>();
        executeWarmupTransactions();
        executeTransactions(results);
        executeWarmupTransactions();
        return results;
    }

    private void executeWarmupTransactions() {
        if (terminalData.getWarmupIterations() > 0) {
            int warmupIterations = terminalData.getWarmupIterations();
            LOGGER.debug("Executing {} warmup iterations...", warmupIterations);
            while (warmupIterations > 0) {
                transaction.process(terminalData, random);
                warmupIterations--;
            }
            LOGGER.debug("Warmup iterations executed");
        }
    }

    private void executeTransactions(List<RawResult> results) {
        LOGGER.debug("Executing for a limited time...");
        Duration minsToWait = terminalData.getRunMins();
        Stopwatch stopwatch = Stopwatch.createStarted();
        while (true) {
            RawResult rawResult = transaction.process(terminalData, random);
            if (minsToWait.compareTo(stopwatch.elapsed()) >= 0) {
                results.add(rawResult);
            } else {
                stopwatch.stop();
                break;
            }
        }
        LOGGER.debug("Limited time execution is over. Elapsed time {}", stopwatch.elapsed().getSeconds());
    }

}
