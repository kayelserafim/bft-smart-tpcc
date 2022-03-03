package bftsmart.microbenchmark.tpcc.server.repository;

import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.exception.NotFoundException;
import bftsmart.microbenchmark.tpcc.server.repository.base.KVRepository;
import bftsmart.microbenchmark.tpcc.table.Item;

@Singleton
public class ItemRepository {

    @Inject
    private KVRepository<Tuple, Item> itemDao;

    public Item find(int itemId) {
        Tuple key = Item.key(itemId);
        return itemDao.find(key).orElseThrow(() -> new NotFoundException("Item %s not found", key));
    }

}
