package bftsmart.microbenchmark.tpcc.server.repository.base;

import static java.util.concurrent.ConcurrentHashMap.newKeySet;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.javatuples.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.domain.Persistable;
import bftsmart.microbenchmark.tpcc.io.Workload;

@Singleton
public class JsonKVRepository implements KVRepository<Tuple, Persistable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKVRepository.class);

    private final Map<Tuple, Set<Persistable>> records = new ConcurrentHashMap<>();
    private final Map<Tuple, Set<Tuple>> indexes = new ConcurrentHashMap<>();

    @Inject
    JsonKVRepository(final Workload workload) {
        LOGGER.info("Initializing KVRepository");

        workload.getCustomers().forEach(this::save);
        workload.getDistricts().forEach(this::save);
        workload.getHistories().forEach(this::save);
        workload.getItems().forEach(this::save);
        workload.getNewOrders().forEach(this::save);
        workload.getOrderLines().forEach(this::save);
        workload.getOrders().forEach(this::save);
        workload.getStocks().forEach(this::save);
        workload.getWarehouses().forEach(this::save);

        long totalIndexes = indexes.values().stream().mapToLong(Set::size).sum();
        long totalRecords = records.values().stream().mapToLong(Set::size).sum();

        LOGGER.info("KVRepository initialized");
        LOGGER.info("Total indexes: {} ", totalIndexes);
        LOGGER.info("Total values: {} ", totalRecords);
    }

    @Override
    public Persistable save(Persistable value) {
        return save(value.getKey(), value);
    }

    @Override
    public Persistable save(Tuple key, Persistable value) {
        records.computeIfAbsent(key, k -> newKeySet()).add(value);
        value.getSecondaryKeys().forEach(idx -> indexes.computeIfAbsent(idx, k -> newKeySet()).add(key));
        return value;
    }

    @Override
    public Optional<Persistable> find(Tuple key) {
        return findAll(key).stream().findFirst();
    }

    @Override
    public Set<Persistable> findAll(Tuple key) {
        Set<Persistable> objects = records.get(key);
        if (objects != null) {
            return objects;
        }
        return findAllIn(indexes.get(key));
    }

    @Override
    public Set<Persistable> findAllIn(Set<Tuple> keys) {
        return CollectionUtils.emptyIfNull(keys)
                .stream()
                .map(this::findAll)
                .flatMap(Set::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean delete(Tuple key) {
        Set<Persistable> objects = records.remove(key);

        CollectionUtils.emptyIfNull(objects)
                .stream()
                .map(Persistable::getSecondaryKeys)
                .flatMap(Set::stream)
                .map(indexes::get)
                .forEach(values -> values.removeIf(key::equals));

        return CollectionUtils.isNotEmpty(objects);
    }

}
