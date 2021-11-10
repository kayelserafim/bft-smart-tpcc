package bftsmart.microbenchmark.tpcc.repository;

import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.repository.base.KVRepository;
import bftsmart.microbenchmark.tpcc.table.History;

@Singleton
public class HistoryRepository {

    @Inject
    private KVRepository<Tuple, History> historyDao;

    public History save(History history) {
        return historyDao.save(history);
    }

}
