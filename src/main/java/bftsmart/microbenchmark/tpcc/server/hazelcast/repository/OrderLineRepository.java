package bftsmart.microbenchmark.tpcc.server.hazelcast.repository;

import java.util.Collection;

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

import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.OrderLine;
import bftsmart.microbenchmark.tpcc.table.OrderLine.OrderLineKey;
import bftsmart.microbenchmark.tpcc.workload.Workload;

@Singleton
public class OrderLineRepository {

    private static final String TABLE_NAME = OrderLine.MODEL_TYPE.name();
    private static final String DISTRICT_ID_FILTER = "districtId";
    private static final String WAREHOUSE_ID_FILTER = "warehouseId";
    private static final String ORDER_ID_FILTER = "orderId";

    private final HazelcastInstance hazelcastInstance;

    @Inject
    OrderLineRepository(HazelcastInstance hazelcastInstance, Workload workload) {
        this.hazelcastInstance = hazelcastInstance;

        IMap<OrderLineKey, OrderLine> orderLineMap = hazelcastInstance.getMap(TABLE_NAME);
        orderLineMap.addIndex(new IndexConfig(IndexType.SORTED, ORDER_ID_FILTER));
        orderLineMap.addIndex(new IndexConfig(IndexType.SORTED, WAREHOUSE_ID_FILTER));
        orderLineMap.addIndex(new IndexConfig(IndexType.SORTED, ORDER_ID_FILTER));

        workload.getOrderLines().forEach(district -> orderLineMap.put(district.createKey(), district));
    }

    public OrderLine save(TransactionContext txCxt, OrderLine orderLine) {
        TransactionalMap<OrderLineKey, OrderLine> orders = txCxt.getMap(TABLE_NAME);

        orders.put(orderLine.createKey(), orderLine);

        return orderLine;
    }

    public Collection<OrderLine> find(Integer orderId, Integer districtId, Integer warehouseId) {
        IMap<OrderLineKey, OrderLine> orderLineMap = hazelcastInstance.getMap(TABLE_NAME);
        EntryObject e = Predicates.newPredicateBuilder().getEntryObject();

        PredicateBuilder warehousePredicate = e.get(WAREHOUSE_ID_FILTER).equal(warehouseId);
        PredicateBuilder districtPredicate = e.get(DISTRICT_ID_FILTER).equal(districtId);
        PredicateBuilder orderLinePredicate = e.get(ORDER_ID_FILTER).equal(orderId);

        Predicate<OrderLineKey, OrderLine> predicate =
                Predicates.and(warehousePredicate, districtPredicate, orderLinePredicate);

        return orderLineMap.values(predicate);
    }

    /**
     * Examining the level of stock for items on the last 20 orders is done in one or more database transactions
     * 
     * @param district
     *            The district table
     * @param warehouseId
     *            The warehouse id
     * @see clause 2.8.2.1
     * @return all instances of the type {@link OrderLine}.
     */
    public Collection<OrderLine> find(District district, Integer warehouseId) {
        IMap<OrderLineKey, OrderLine> orderLineMap = hazelcastInstance.getMap(TABLE_NAME);
        EntryObject e = Predicates.newPredicateBuilder().getEntryObject();

        PredicateBuilder warehousePredicate = e.get(WAREHOUSE_ID_FILTER).equal(warehouseId);
        PredicateBuilder districtPredicate = e.get(DISTRICT_ID_FILTER).equal(district.getDistrictId());
        PredicateBuilder orderIdGtPredicate = e.get(ORDER_ID_FILTER).greaterEqual(district.getNextOrderId() - 20);
        PredicateBuilder orderIdLtPredicate = e.get(ORDER_ID_FILTER).lessEqual(district.getNextOrderId());

        Predicate<OrderLineKey, OrderLine> predicate =
                Predicates.and(warehousePredicate, districtPredicate, orderIdGtPredicate, orderIdLtPredicate);

        return orderLineMap.values(predicate);
    }

}
