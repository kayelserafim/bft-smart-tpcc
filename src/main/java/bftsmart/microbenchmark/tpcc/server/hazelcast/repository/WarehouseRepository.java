package bftsmart.microbenchmark.tpcc.server.hazelcast.repository;

import java.util.Optional;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hazelcast.config.IndexConfig;
import com.hazelcast.config.IndexType;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionalMap;

import bftsmart.microbenchmark.tpcc.exception.NotFoundException;
import bftsmart.microbenchmark.tpcc.table.Warehouse;
import bftsmart.microbenchmark.tpcc.workload.Workload;

@Singleton
public class WarehouseRepository {

    private static final String TABLE_NAME = Warehouse.MODEL_TYPE.name();
    private final HazelcastInstance hazelcastInstance;

    @Inject
    WarehouseRepository(HazelcastInstance hazelcastInstance, Workload workload) {
        this.hazelcastInstance = hazelcastInstance;

        IMap<Integer, Warehouse> warehouseMap = hazelcastInstance.getMap(TABLE_NAME);
        warehouseMap.addIndex(new IndexConfig(IndexType.SORTED, "warehouseId"));

        workload.getWarehouses().forEach(warehouse -> warehouseMap.put(warehouse.getWarehouseId(), warehouse));
    }

    public Warehouse find(int warehouseId) {
        IMap<Integer, Warehouse> orderLineMap = hazelcastInstance.getMap(TABLE_NAME);

        return Optional.ofNullable(orderLineMap.get(warehouseId))
                .orElseThrow(() -> new NotFoundException("Warehouse %s not found", warehouseId));
    }

    public Warehouse save(TransactionContext txCxt, Warehouse warehouse) {
        TransactionalMap<Integer, Warehouse> orders = txCxt.getMap(TABLE_NAME);

        orders.put(warehouse.getWarehouseId(), warehouse);

        return warehouse;
    }

}
