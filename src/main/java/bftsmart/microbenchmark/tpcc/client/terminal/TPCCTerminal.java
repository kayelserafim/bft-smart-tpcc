package bftsmart.microbenchmark.tpcc.client.terminal;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bftsmart.microbenchmark.tpcc.client.service.TPCCService;
import bftsmart.microbenchmark.tpcc.monitor.RawResult;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class TPCCTerminal implements Callable<List<RawResult>> {

    public static final Logger LOGGER = LoggerFactory.getLogger(TPCCTerminal.class);

    private final TPCCTerminalData terminalData;
    private final TPCCRandom random;
    private final List<RawResult> results;
    private final TimedSemaphore timedSemaphore;

    private TPCCService transaction;

    public TPCCTerminal(TPCCService transaction, TPCCTerminalData terminalData, TPCCRandom random,
            TimedSemaphore timedSemaphore) {
        this.transaction = transaction;
        this.terminalData = terminalData;
        this.random = random;
        this.timedSemaphore = timedSemaphore;
        this.results = new ArrayList<>();
    }

    @Override
    public List<RawResult> call() {
        String threadName = Thread.currentThread().getName();
        LOGGER.info("Terminal {} started!", threadName);
        executeWarmupTransactions();
        executeTransactions();
        LOGGER.info("Terminal {} finished!", threadName);
        return results;
    }

    private void executeWarmupTransactions() {
        if (terminalData.getWarmupIterations() > 0) {
            int warmupIterations = terminalData.getWarmupIterations();
            LOGGER.debug("Executing {} warmup iterations...", warmupIterations);
            while (warmupIterations > 0) {
                if (timedSemaphore.tryAcquire()) {
                    executeTransaction();
                    warmupIterations--;
                }
            }
            LOGGER.debug("Warmup iterations {} executed", warmupIterations);
        }
    }

    private void executeTransactions() {
        if (terminalData.getNumTransactions() > 0) {
            int numTransactions = terminalData.getNumTransactions();
            LOGGER.debug("Executing {} transactions...", numTransactions);
            while (numTransactions > 0) {
                if (timedSemaphore.tryAcquire()) {
                    results.add(executeTransaction());
                    numTransactions--;
                }
            }
            LOGGER.debug("transactions {} executed", numTransactions);
        } else {
            Instant instantToWait = terminalData.instantToWait();
            LOGGER.debug("Executing for a limited time...");
            while (instantToWait.isAfter(Instant.now())) {
                if (timedSemaphore.tryAcquire()) {
                    results.add(executeTransaction());
                }
            }
            LOGGER.debug("Limited time execution is over...");
        }
    }

    private RawResult executeTransaction() {
        RawResult rawResult = new RawResult(terminalData.getTerminalId(), terminalData.getTerminalName());

        TPCCCommand response = transaction.process(terminalData, random);
        rawResult.stop();
        rawResult.setCommandType(response.getCommandType());
        rawResult.setResult(response.getResult());
        rawResult.setMessage("Response received: " + response.getMessage());

        return rawResult;
    }

}
