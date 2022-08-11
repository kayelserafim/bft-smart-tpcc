package bftsmart.microbenchmark.tpcc.server.hazelcast.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.multimap.MultiMap;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionalMultiMap;

import bftsmart.microbenchmark.tpcc.table.History;
import bftsmart.microbenchmark.tpcc.table.History.HistoryKey;
import bftsmart.microbenchmark.tpcc.workload.Workload;

@Singleton
public class HistoryRepository {

    private static final String TABLE_NAME = History.TABLE_TYPE.name();

    @Inject
    HistoryRepository(HazelcastInstance hazelcastInstance, Workload workload) {
        MultiMap<HistoryKey, History> historyMap = hazelcastInstance.getMultiMap(TABLE_NAME);
        workload.getHistories().forEach(history -> historyMap.put(history.createKey(), history));
    }

    public History save(TransactionContext txCxt, History history) {
        TransactionalMultiMap<HistoryKey, History> historyMap = txCxt.getMultiMap(TABLE_NAME);

        historyMap.put(history.createKey(), history);

        return history;
    }

}
