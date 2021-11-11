package bftsmart.microbenchmark.tpcc.repository.base;

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

import bftsmart.microbenchmark.tpcc.io.TPCCData;
import bftsmart.microbenchmark.tpcc.probject.PRObject;

@Singleton
public class JsonKVRepository<T extends PRObject> implements KVRepository<Tuple, T> {

    public static final Logger LOGGER = LoggerFactory.getLogger(JsonKVRepository.class);

    private Map<Tuple, Set<T>> records = new ConcurrentHashMap<>();
    private Map<Tuple, Set<Tuple>> indexes = new ConcurrentHashMap<>();

    @Inject
    @SuppressWarnings("unchecked")
    JsonKVRepository(final TPCCData tpccData) {
        LOGGER.info("Initializing KVRepository");

        tpccData.getCustomers().parallelStream().forEach(value -> save((T) value));
        tpccData.getDistricts().parallelStream().forEach(value -> save((T) value));
        tpccData.getHistories().parallelStream().forEach(value -> save((T) value));
        tpccData.getItems().parallelStream().forEach(value -> save((T) value));
        tpccData.getNewOrders().parallelStream().forEach(value -> save((T) value));
        tpccData.getOrderLines().parallelStream().forEach(value -> save((T) value));
        tpccData.getOrders().parallelStream().forEach(value -> save((T) value));
        tpccData.getStocks().parallelStream().forEach(value -> save((T) value));
        tpccData.getWarehouses().parallelStream().forEach(value -> save((T) value));

        LOGGER.info("KVRepository initialized. Values: {} ", records.values().stream().flatMap(Set::stream).count());
        LOGGER.info("KVRepository initialized. Indexes: {} ", indexes.values().stream().flatMap(Set::stream).count());
    }

    @Override
    public T save(T value) {
        return save(value.getKey(), value);
    }

    @Override
    public T save(Tuple key, T value) {
        records.computeIfAbsent(key, k -> newKeySet()).add(value);
        value.getSecondaryKeys().forEach(idx -> indexes.computeIfAbsent(idx, k -> newKeySet()).add(key));
        return value;
    }

    @Override
    public Optional<T> find(Tuple key) {
        return findAll(key).stream().findFirst();
    }

    @Override
    public Set<T> findAll(Tuple key) {
        Set<T> objects = records.get(key);
        if (objects != null) {
            return objects;
        }
        return findAllIn(indexes.get(key));
    }

    @Override
    public Set<T> findAllIn(Set<Tuple> keys) {
        return CollectionUtils.emptyIfNull(keys)
                .stream()
                .map(this::findAll)
                .flatMap(Set::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean delete(Tuple key) {
        Set<T> objects = records.remove(key);

        CollectionUtils.emptyIfNull(objects)
                .stream()
                .map(PRObject::getSecondaryKeys)
                .flatMap(Set::stream)
                .map(indexes::get)
                .forEach(values -> values.removeIf(key::equals));

        return CollectionUtils.isNotEmpty(objects);
    }

}
