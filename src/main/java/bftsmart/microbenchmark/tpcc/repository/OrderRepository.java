package bftsmart.microbenchmark.tpcc.repository;

import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.repository.base.KVRepository;
import bftsmart.microbenchmark.tpcc.table.Order;

@Singleton
public class OrderRepository {

    @Inject
    private KVRepository<Tuple, Order> orderDao;

    public Order findByOrderId(Integer orderId, Integer districtId, Integer warehouseId) {
        Tuple key = Order.key(warehouseId, districtId, orderId);
        return orderDao.find(key).orElse(null);
    }

    public Order findByCustomerId(Integer customerId, Integer districtId, Integer warehouseId) {
        Tuple customerKey = Order.customerKey(warehouseId, districtId, customerId);
        return orderDao.find(customerKey).orElse(null);
    }

    public Order save(Order order) {
        return orderDao.save(order);
    }

}
