package bftsmart.microbenchmark.tpcc.server.repository;

import java.util.Set;

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

    public long count(Set<Tuple> stockIds, int threshold) {
        return stockDao.findAllIn(stockIds).stream().filter(stock -> stock.getQuantity() < threshold).count();
    }

}
