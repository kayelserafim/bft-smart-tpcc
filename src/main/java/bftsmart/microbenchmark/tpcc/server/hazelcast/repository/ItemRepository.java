package bftsmart.microbenchmark.tpcc.server.hazelcast.repository;

import java.util.Optional;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hazelcast.config.IndexConfig;
import com.hazelcast.config.IndexType;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import bftsmart.microbenchmark.tpcc.exception.NotFoundException;
import bftsmart.microbenchmark.tpcc.table.Item;
import bftsmart.microbenchmark.tpcc.workload.Workload;

@Singleton
public class ItemRepository {

    private final HazelcastInstance hazelcastInstance;

    @Inject
    ItemRepository(HazelcastInstance hazelcastInstance, Workload workload) {
        this.hazelcastInstance = hazelcastInstance;

        IMap<Integer, Item> itemMap = hazelcastInstance.getMap(Item.MODEL_TYPE.name());
        itemMap.addIndex(new IndexConfig(IndexType.SORTED, "itemId"));

        workload.getItems().forEach(item -> itemMap.put(item.getItemId(), item));
    }

    public Item find(int itemId) {
        IMap<Integer, Item> itemMap = hazelcastInstance.getMap(Item.MODEL_TYPE.name());

        return Optional.ofNullable(itemMap.get(itemId))
                .orElseThrow(() -> new NotFoundException("Item %s not found", itemId));
    }

}
