package bftsmart.microbenchmark.tpcc.server.hazelcast.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hazelcast.config.IndexConfig;
import com.hazelcast.config.IndexType;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionalMap;

import bftsmart.microbenchmark.tpcc.table.Order;
import bftsmart.microbenchmark.tpcc.table.key.OrderKey;
import bftsmart.microbenchmark.tpcc.util.Lists;
import bftsmart.microbenchmark.tpcc.workload.Workload;

@Singleton
public class OrderRepository {

    private static final String TABLE_NAME = Order.MODEL_TYPE.name();
    private final HazelcastInstance hazelcastInstance;

    @Inject
    OrderRepository(HazelcastInstance hazelcastInstance, Workload workload) {
        this.hazelcastInstance = hazelcastInstance;

        IMap<OrderKey, Order> orderMap = hazelcastInstance.getMap(TABLE_NAME);
        orderMap.addIndex(new IndexConfig(IndexType.SORTED, "warehouseId"));
        orderMap.addIndex(new IndexConfig(IndexType.SORTED, "districtId"));
        orderMap.addIndex(new IndexConfig(IndexType.SORTED, "customerId"));

        workload.getOrders().forEach(order -> orderMap.put(order.createKey(), order));
    }

    public Order findByOrderId(Integer orderId, Integer districtId, Integer warehouseId) {
        IMap<OrderKey, Order> orders = hazelcastInstance.getMap(TABLE_NAME);
        OrderKey key = new OrderKey(warehouseId, districtId, orderId);
        return orders.get(key);
    }

    public Order findByCustomerId(Integer customerId, Integer districtId, Integer warehouseId) {
        IMap<OrderKey, Order> orders = hazelcastInstance.getMap(TABLE_NAME);

        Predicate<OrderKey, Order> warehousePredicate = Predicates.equal("warehouseId", warehouseId);
        Predicate<OrderKey, Order> districtPredicate = Predicates.equal("districtId", districtId);
        Predicate<OrderKey, Order> customerIdPredicate = Predicates.equal("customerId", customerId);

        Predicate<OrderKey, Order> predicate =
                Predicates.and(warehousePredicate, districtPredicate, customerIdPredicate);

        return Lists.getFirst(orders.values(predicate));
    }

    public Order save(TransactionContext txCxt, Order order) {
        TransactionalMap<OrderKey, Order> orders = txCxt.getMap(TABLE_NAME);

        orders.put(order.createKey(), order);

        return order;
    }

}
