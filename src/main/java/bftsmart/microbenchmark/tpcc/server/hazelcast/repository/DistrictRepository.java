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
import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.key.DistrictKey;
import bftsmart.microbenchmark.tpcc.workload.Workload;

@Singleton
public class DistrictRepository {

    private static final String TABLE_NAME = District.TABLE_TYPE.name();

    private final HazelcastInstance hazelcastInstance;

    @Inject
    DistrictRepository(HazelcastInstance hazelcastInstance, Workload workload) {
        this.hazelcastInstance = hazelcastInstance;

        IMap<DistrictKey, District> districtMap = hazelcastInstance.getMap(TABLE_NAME);
        districtMap.addIndex(new IndexConfig(IndexType.SORTED, "warehouseId"));
        districtMap.addIndex(new IndexConfig(IndexType.SORTED, "districtId"));

        workload.getDistricts().forEach(district -> districtMap.put(district.createKey(), district));
    }

    public District find(int districtId, int warehouseId) {
        DistrictKey key = new DistrictKey(warehouseId, districtId);
        IMap<DistrictKey, District> districts = hazelcastInstance.getMap(TABLE_NAME);

        return Optional.ofNullable(districts.get(key))
                .orElseThrow(() -> new NotFoundException("District %s not found", key));
    }

    public District save(TransactionContext txCxt, District district) {
        TransactionalMap<DistrictKey, District> orders = txCxt.getMap(TABLE_NAME);

        orders.put(district.createKey(), district);

        return district;
    }

}
