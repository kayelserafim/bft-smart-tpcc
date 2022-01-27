package bftsmart.microbenchmark.tpcc.repository;

import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.exception.NotFoundException;
import bftsmart.microbenchmark.tpcc.repository.base.KVRepository;
import bftsmart.microbenchmark.tpcc.table.Warehouse;

@Singleton
public class WarehouseRepository {

    @Inject
    private KVRepository<Tuple, Warehouse> warehouseDao;

    public Warehouse find(int warehouseId) {
        Tuple key = Warehouse.key(warehouseId);
        return warehouseDao.find(key).orElseThrow(() -> new NotFoundException("Warehouse %s not found", key));
    }

    public Warehouse save(Warehouse warehouse) {
        return warehouseDao.save(warehouse);
    }

}
