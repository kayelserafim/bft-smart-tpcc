package bftsmart.microbenchmark.tpcc.client.terminal;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bftsmart.microbenchmark.tpcc.client.monitor.RawResult;
import bftsmart.microbenchmark.tpcc.client.service.TPCCService;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class TPCCTerminal implements Callable<List<RawResult>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TPCCTerminal.class);

    private final TPCCTerminalData terminalData;
    private final TPCCRandom random;
    private final List<RawResult> results;

    private final TPCCService transaction;

    public TPCCTerminal(TPCCService transaction, TPCCTerminalData terminalData, TPCCRandom random) {
        this.transaction = transaction;
        this.terminalData = terminalData;
        this.random = random;
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
                executeTransaction();
                warmupIterations--;
            }
            LOGGER.debug("Warmup iterations executed");
        }
    }

    private void executeTransactions() {
        LOGGER.debug("Executing for a limited time...");
        Instant instantToWait = terminalData.instantToWait();
        while (instantToWait.isAfter(Instant.now())) {
            results.add(executeTransaction());
        }
        LOGGER.debug("Limited time execution is over...");
    }

    private RawResult executeTransaction() {
        RawResult rawResult = new RawResult(terminalData.getTerminalId(), terminalData.getTerminalName());

        TPCCCommand tpccCommand = transaction.process(terminalData, random);
        rawResult.stop();
        rawResult.setCommandId(tpccCommand.getCommandId());
        rawResult.setTransactionType(tpccCommand.getTransactionType());
        rawResult.setConflict(tpccCommand.getConflict());
        rawResult.setStatus(tpccCommand.getStatus());
        rawResult.setMessage("Response received: " + tpccCommand.getResponse());

        return rawResult;
    }

}
