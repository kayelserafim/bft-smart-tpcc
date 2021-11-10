package bftsmart.microbenchmark.tpcc.repository;

import java.util.Optional;

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

    public Optional<District> find(int districtId, int warehouseId) {
        return districtDao.find(District.key(warehouseId, districtId));
    }

    public District findBy(int districtId, int warehouseId) {
        return find(districtId, warehouseId)
                .orElseThrow(() -> new NotFoundException("District %s not found", districtId));
    }

    public District save(District district) {
        return districtDao.save(district);
    }

}
