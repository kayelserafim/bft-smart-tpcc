package bftsmart.microbenchmark.tpcc.server.hazelcast.repository;

import java.util.HashSet;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hazelcast.config.IndexType;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionalMap;

import bftsmart.microbenchmark.tpcc.table.Stock;
import bftsmart.microbenchmark.tpcc.table.key.StockKey;
import bftsmart.microbenchmark.tpcc.workload.Workload;

@Singleton
public class StockRepository {

    private static final String TABLE_NAME = Stock.MODEL_TYPE.name();
    private final HazelcastInstance hazelcastInstance;

    @Inject
    StockRepository(HazelcastInstance hazelcastInstance, Workload workload) {
        this.hazelcastInstance = hazelcastInstance;

        IMap<StockKey, Stock> stockMap = hazelcastInstance.getMap(TABLE_NAME);
        stockMap.addIndex(IndexType.SORTED, "warehouseId", "itemId", "quantity");

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

    public int count(Set<StockKey> stockKeys, int threshold) {
        IMap<StockKey, Stock> stocks = hazelcastInstance.getMap(TABLE_NAME);

        Set<Integer> warehouseIds = new HashSet<>();
        Set<Integer> itemIds = new HashSet<>();
        for (StockKey stockKey : stockKeys) {
            warehouseIds.add(stockKey.getWarehouseId());
            itemIds.add(stockKey.getItemId());
        }

        Predicate<StockKey, Stock> quantityPredicate = Predicates.lessEqual("quantity", threshold);
        Predicate<StockKey, Stock> warehouseIdPredicate =
                Predicates.in("warehouseId", warehouseIds.toArray(Integer[]::new));
        Predicate<StockKey, Stock> itemIdPredicate = Predicates.in("itemId", itemIds.toArray(Integer[]::new));

        Predicate<StockKey, Stock> predicate = Predicates.and(warehouseIdPredicate, itemIdPredicate, quantityPredicate);

        return stocks.values(predicate).size();
    }

}
