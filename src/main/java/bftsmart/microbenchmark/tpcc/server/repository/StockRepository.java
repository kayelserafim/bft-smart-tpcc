package bftsmart.microbenchmark.tpcc.server.repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.exception.NotFoundException;
import bftsmart.microbenchmark.tpcc.server.repository.base.KVRepository;
import bftsmart.microbenchmark.tpcc.table.Stock;

@Singleton
public class StockRepository {

    @Inject
    private KVRepository<Tuple, Stock> stockDao;

    public Stock find(int itemId, int warehouseId) {
        Tuple key = Stock.key(warehouseId, itemId);
        return stockDao.find(key).orElseThrow(() -> new NotFoundException("Stock [%s] not found", key));
    }

    public Stock save(Stock stock) {
        return stockDao.save(stock);
    }

    public List<Stock> find(List<Integer> orderLineIds, int warehouseId, int threshold) {
        return orderLineIds.stream()
                .map(itemId -> Stock.key(warehouseId, itemId))
                .map(stockDao::findAll)
                .flatMap(Set::stream)
                .filter(stock -> stock.getQuantity() < threshold)
                .collect(Collectors.toList());
    }

    public long count(List<Integer> orderLineIds, int warehouseId, int threshold) {
        return find(orderLineIds, warehouseId, threshold).parallelStream().map(Stock::getItemId).distinct().count();
    }

}
