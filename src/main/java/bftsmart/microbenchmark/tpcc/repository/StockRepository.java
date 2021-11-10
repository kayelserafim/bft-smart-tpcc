package bftsmart.microbenchmark.tpcc.repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.repository.base.KVRepository;
import bftsmart.microbenchmark.tpcc.table.Stock;

@Singleton
public class StockRepository {

    @Inject
    private KVRepository<Tuple, Stock> stockDao;

    public Stock findBy(int itemId, int warehouseId) {
        return stockDao.find(Stock.key(warehouseId, itemId)).orElse(null);
    }

    public Stock save(Stock stock) {
        return stockDao.save(stock);
    }

    public List<Stock> findBy(List<Integer> orderLineIds, int warehouseId, int threshold) {
        return orderLineIds.stream()
                .map(itemId -> Stock.key(warehouseId, itemId))
                .map(stockDao::findAll)
                .flatMap(Set::stream)
                .filter(stock -> stock.getQuantity() < threshold)
                .collect(Collectors.toList());
    }

    public long countBy(List<Integer> orderLineIds, int warehouseId, int threshold) {
        return findBy(orderLineIds, warehouseId, threshold).parallelStream().map(Stock::getItemId).distinct().count();
    }

}
