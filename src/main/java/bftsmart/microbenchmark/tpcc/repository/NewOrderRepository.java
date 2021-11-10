package bftsmart.microbenchmark.tpcc.repository;

import java.util.Comparator;
import java.util.Optional;

import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.repository.base.KVRepository;
import bftsmart.microbenchmark.tpcc.table.NewOrder;

@Singleton
public class NewOrderRepository {

    @Inject
    private KVRepository<Tuple, NewOrder> newOrderDao;

    public NewOrder save(Integer nextOrderId, Integer districtId, Integer warehouseId) {
        NewOrder newOrder =
                NewOrder.builder().orderId(nextOrderId).districtId(districtId).warehouseId(warehouseId).build();
        return newOrderDao.save(newOrder);
    }

    public boolean deleteBy(Integer warehouseId, Integer districtId, Integer orderId) {
        return newOrderDao.delete(NewOrder.key(orderId, districtId, warehouseId));
    }

    public Optional<NewOrder> findFirstBy(Integer districtId, Integer warehouseId) {
        return newOrderDao.findAll(NewOrder.districtKey(warehouseId, districtId))
                .stream()
                .sorted(Comparator.comparing(NewOrder::getOrderId))
                .limit(1)
                .findFirst();
    }

}
