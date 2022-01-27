package bftsmart.microbenchmark.tpcc.repository;

import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.exception.NotFoundException;
import bftsmart.microbenchmark.tpcc.repository.base.KVRepository;
import bftsmart.microbenchmark.tpcc.table.District;

@Singleton
public class DistrictRepository {

    @Inject
    private KVRepository<Tuple, District> districtDao;

    public District find(int districtId, int warehouseId) {
        Tuple key = District.key(warehouseId, districtId);
        return districtDao.find(key).orElseThrow(() -> new NotFoundException("District %s not found", key));
    }

    public District save(District district) {
        return districtDao.save(district);
    }

}
