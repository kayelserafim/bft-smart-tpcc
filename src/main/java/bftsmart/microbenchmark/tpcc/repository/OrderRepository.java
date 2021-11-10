package bftsmart.microbenchmark.tpcc.repository;

import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.repository.base.KVRepository;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.input.NewOrderInput;
import bftsmart.microbenchmark.tpcc.table.Order;
import bftsmart.microbenchmark.tpcc.util.Times;

@Singleton
public class OrderRepository {

    @Inject
    private KVRepository<Tuple, Order> orderDao;

    public Order findByOrderId(Integer orderId, Integer districtId, Integer warehouseId) {
        return orderDao.find(Order.key(warehouseId, districtId, orderId)).orElse(null);
    }

    public Order findByCustomerId(Integer customerId, Integer districtId, Integer warehouseId) {
        return orderDao.find(Order.customerKey(warehouseId, districtId, customerId)).orElse(null);
    }

    public Order save(int nextOrderId, NewOrderInput input) {
        // The number of items, O_OL_CNT, is computed to match ol_cnt
        Order order = Order.builder()
                .orderId(nextOrderId)
                .districtId(input.getDistrictId())
                .warehouseId(input.getWarehouseId())
                .customerId(input.getCustomerId())
                .entryDate(Times.currentTimeMillis())
                .orderLineCounter(input.getOrderLineCnt())
                .allLocal(input.getOrderAllLocal())
                .build();

        return orderDao.save(order);
    }

    public Order save(Order order) {
        return orderDao.save(order);
    }

}
