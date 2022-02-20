package bftsmart.microbenchmark.tpcc.client.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Singleton;

import bftsmart.tom.ServiceProxy;

@Singleton
public class BFTServiceProxy {

    private final Map<Integer, ServiceProxy> multitons = new ConcurrentHashMap<>();

    public ServiceProxy getInstance(final Integer terminalId) {
        return multitons.computeIfAbsent(terminalId, ServiceProxy::new);
    }

}