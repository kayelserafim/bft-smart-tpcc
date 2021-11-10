package bftsmart.microbenchmark.tpcc.repository;

import java.util.Optional;

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

    public Optional<Warehouse> find(int warehouseId) {
        return warehouseDao.find(Warehouse.key(warehouseId));
    }

    public Warehouse findBy(int warehouseId) {
        return find(warehouseId).orElseThrow(() -> new NotFoundException("Warehouse %s not found", warehouseId));
    }

    public Warehouse save(Warehouse warehouse) {
        return warehouseDao.save(warehouse);
    }

}
