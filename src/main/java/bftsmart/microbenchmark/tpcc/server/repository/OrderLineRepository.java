package bftsmart.microbenchmark.tpcc.server.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.server.repository.base.KVRepository;
import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.OrderLine;

@Singleton
public class OrderLineRepository {

    @Inject
    private KVRepository<Tuple, OrderLine> orderLineDao;

    public OrderLine save(OrderLine orderLine) {
        return orderLineDao.save(orderLine);
    }

    public List<OrderLine> find(Integer orderId, Integer districtId, Integer warehouseId) {
        return orderLineDao.findAll(OrderLine.orderKey(warehouseId, districtId, orderId))
                .stream()
                .collect(Collectors.toList());
    }

    public List<OrderLine> find(District district, Integer warehouseId) {
        return orderLineDao.findAll(OrderLine.districtKey(warehouseId, warehouseId))
                .parallelStream()
                .filter(ol -> ol.getOrderId() < district.getNextOrderId())
                .filter(ol -> ol.getOrderId() >= district.getNextOrderId() - 20)
                .collect(Collectors.toList());
    }

}
