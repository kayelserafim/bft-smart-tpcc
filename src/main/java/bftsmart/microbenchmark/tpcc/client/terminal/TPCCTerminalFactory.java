package bftsmart.microbenchmark.tpcc.client.terminal;

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

    public TPCCTerminal create(TPCCTerminalData terminalData, TPCCRandom random) {
        return new TPCCTerminal(transaction, terminalData, random);
    }

}
