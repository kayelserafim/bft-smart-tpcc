package bftsmart.microbenchmark.tpcc.server.hazelcast.repository;

import java.util.Set;

import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hazelcast.config.IndexConfig;
import com.hazelcast.config.IndexType;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.PredicateBuilder.EntryObject;
import com.hazelcast.query.Predicates;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionalMap;

import bftsmart.microbenchmark.tpcc.table.Stock;
import bftsmart.microbenchmark.tpcc.table.Stock.StockKey;
import bftsmart.microbenchmark.tpcc.workload.Workload;

@Singleton
public class StockRepository {

    private static final String TABLE_NAME = Stock.MODEL_TYPE.name();
    private final HazelcastInstance hazelcastInstance;

    @Inject
    StockRepository(HazelcastInstance hazelcastInstance, Workload workload) {
        this.hazelcastInstance = hazelcastInstance;

        IMap<StockKey, Stock> stockMap = hazelcastInstance.getMap(TABLE_NAME);
        stockMap.addIndex(new IndexConfig(IndexType.SORTED, "warehouseId"));
        stockMap.addIndex(new IndexConfig(IndexType.SORTED, "quantity"));

        workload.getStocks().forEach(stock -> stockMap.put(stock.createKey(), stock));
    }

    public Stock find(int itemId, int warehouseId) {
        StockKey key = new StockKey(warehouseId, itemId);
        IMap<StockKey, Stock> stocks = hazelcastInstance.getMap(TABLE_NAME);

        return stocks.get(key);
    }

    public Stock save(TransactionContext txCxt, Stock stock) {
        TransactionalMap<StockKey, Stock> orders = txCxt.getMap(TABLE_NAME);

        orders.put(stock.createKey(), stock);

        return stock;
    }

    public int count(Set<StockKey> stockIds, int threshold) {
        IMap<StockKey, Stock> stocks = hazelcastInstance.getMap(TABLE_NAME);
        EntryObject e = Predicates.newPredicateBuilder().getEntryObject();

        PredicateBuilder keyPredicate = e.get("key").in(stockIds.toArray(Tuple[]::new));
        PredicateBuilder quantityPredicate = e.get("quantity").lessEqual(threshold);

        Predicate<StockKey, Stock> predicate = Predicates.and(keyPredicate, quantityPredicate);

        return stocks.values(predicate).size();
    }

}
