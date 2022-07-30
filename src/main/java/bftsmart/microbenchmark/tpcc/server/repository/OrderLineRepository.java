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

    /**
     * Examining the level of stock for items on the last 20 orders is done in one or more database transactions
     * 
     * @param district
     *            The district table
     * @param warehouseId
     *            The warehouse id
     * @see clause 2.8.2.1
     * @return all instances of the type {@link OrderLine}.
     */
    public List<OrderLine> find(District district, Integer warehouseId) {
        return orderLineDao.findAll(OrderLine.districtKey(warehouseId, district.getDistrictId()))
                .stream()
                .filter(ol -> ol.getOrderId() >= district.getNextOrderId() - 20)
                .filter(ol -> ol.getOrderId() < district.getNextOrderId())
                .collect(Collectors.toList());
    }

}
