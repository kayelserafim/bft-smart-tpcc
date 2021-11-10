package bftsmart.microbenchmark.tpcc.client.terminal;

import org.apache.commons.lang3.concurrent.TimedSemaphore;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.client.service.TPCCService;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

@Singleton
public class TPCCTerminalFactory {

    private final TPCCService transaction;

    @Inject
    public TPCCTerminalFactory(TPCCService transaction) {
        this.transaction = transaction;
    }

    public TPCCTerminal create(TPCCTerminalData terminalData, TPCCRandom random, TimedSemaphore timedSemaphore) {
        return new TPCCTerminal(transaction, terminalData, random, timedSemaphore);
    }

}
