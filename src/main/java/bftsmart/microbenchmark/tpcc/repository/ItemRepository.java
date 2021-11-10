package bftsmart.microbenchmark.tpcc.repository;

import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.repository.base.KVRepository;
import bftsmart.microbenchmark.tpcc.table.Item;

@Singleton
public class ItemRepository {

    @Inject
    private KVRepository<Tuple, Item> itemDao;

    public Item findBy(int itemId) {
        return itemDao.find(Item.key(itemId)).orElse(null);
    }

}
