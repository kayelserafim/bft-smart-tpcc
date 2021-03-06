package bftsmart.microbenchmark.tpcc.server.repository;

import java.util.Comparator;
import java.util.Optional;

import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.server.repository.base.KVRepository;
import bftsmart.microbenchmark.tpcc.table.NewOrder;
import bftsmart.microbenchmark.tpcc.table.Order;

@Singleton
public class NewOrderRepository {

    @Inject
    private KVRepository<Tuple, NewOrder> newOrderDao;

    public NewOrder save(Integer nextOrderId, Integer districtId, Integer warehouseId) {
        NewOrder newOrder =
                NewOrder.builder().orderId(nextOrderId).districtId(districtId).warehouseId(warehouseId).build();
        return save(newOrder);
    }

    public NewOrder save(NewOrder newOrder) {
        return newOrderDao.save(newOrder);
    }

    public boolean delete(Order order) {
        return delete(order.getWarehouseId(), order.getDistrictId(), order.getOrderId());
    }

    public boolean delete(Integer warehouseId, Integer districtId, Integer orderId) {
        Tuple key = NewOrder.key(orderId, districtId, warehouseId);
        return newOrderDao.delete(key);
    }

    public Optional<NewOrder> findFirst(Integer districtId, Integer warehouseId) {
        return newOrderDao.findAll(NewOrder.districtKey(warehouseId, districtId))
                .stream()
                .sorted(Comparator.comparing(NewOrder::getOrderId))
                .limit(1)
                .findFirst();
    }

}
