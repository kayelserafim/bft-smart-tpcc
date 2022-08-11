package bftsmart.microbenchmark.tpcc.server.hazelcast.repository;

import org.apache.commons.collections4.IterableUtils;
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

import bftsmart.microbenchmark.tpcc.table.NewOrder;
import bftsmart.microbenchmark.tpcc.table.NewOrder.NewOrderKey;
import bftsmart.microbenchmark.tpcc.workload.Workload;

@Singleton
public class NewOrderRepository {

    private static final String TABLE_NAME = NewOrder.MODEL_TYPE.name();
    private final HazelcastInstance hazelcastInstance;

    @Inject
    NewOrderRepository(HazelcastInstance hazelcastInstance, Workload workload) {
        this.hazelcastInstance = hazelcastInstance;

        IMap<NewOrderKey, NewOrder> itemMap = hazelcastInstance.getMap(TABLE_NAME);
        itemMap.addIndex(new IndexConfig(IndexType.SORTED, "warehouseId"));
        itemMap.addIndex(new IndexConfig(IndexType.SORTED, "districtId"));
        itemMap.addIndex(new IndexConfig(IndexType.SORTED, "orderId"));

        workload.getNewOrders().forEach(newOrder -> itemMap.put(newOrder.createKey(), newOrder));
    }

    public NewOrder save(TransactionContext txCxt, Integer nextOrderId, Integer districtId, Integer warehouseId) {
        NewOrder newOrder =
                new NewOrder().withOrderId(nextOrderId).withDistrictId(districtId).withWarehouseId(warehouseId);

        return save(txCxt, newOrder);
    }

    public NewOrder save(TransactionContext txCxt, NewOrder newOrder) {
        TransactionalMap<NewOrderKey, NewOrder> newOrdersMap = txCxt.getMap(TABLE_NAME);

        newOrdersMap.put(newOrder.createKey(), newOrder);

        return newOrder;
    }

    public boolean delete(TransactionContext txCxt, NewOrder newOrder) {
        TransactionalMap<Tuple, NewOrder> newOrdersMap = txCxt.getMap(TABLE_NAME);

        return newOrdersMap.remove(newOrder.createKey()) != null;
    }

    public boolean delete(TransactionContext txCxt, Integer warehouseId, Integer districtId, Integer orderId) {
        TransactionalMap<NewOrderKey, NewOrder> newOrdersMap = txCxt.getMap(TABLE_NAME);

        NewOrderKey key = new NewOrderKey(warehouseId, districtId, orderId);

        return newOrdersMap.remove(key) != null;
    }

    public NewOrder findFirst(Integer districtId, Integer warehouseId) {
        IMap<NewOrderKey, NewOrder> newOrdersMap = hazelcastInstance.getMap(TABLE_NAME);
        EntryObject e = Predicates.newPredicateBuilder().getEntryObject();

        PredicateBuilder warehousePredicate = e.get("warehouseId").equal(warehouseId);
        PredicateBuilder districtPredicate = e.get("districtId").equal(districtId);

        Predicate<NewOrderKey, NewOrder> predicate = Predicates.and(warehousePredicate, districtPredicate);

        return IterableUtils.first(newOrdersMap.values(predicate));
    }

}
