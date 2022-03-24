package bftsmart.microbenchmark.tpcc.client.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Singleton;

import bftsmart.tom.ParallelServiceProxy;

@Singleton
public class BFTServiceProxy {

    private final Map<Integer, ParallelServiceProxy> multitons = new ConcurrentHashMap<>();

    public ParallelServiceProxy getInstance(final Integer terminalId) {
        return multitons.computeIfAbsent(terminalId, ParallelServiceProxy::new);
    }

}