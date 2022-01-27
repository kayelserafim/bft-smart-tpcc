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
public class JsonKVRepository implements KVRepository<Tuple, PRObject> {

    public static final Logger LOGGER = LoggerFactory.getLogger(JsonKVRepository.class);

    private Map<Tuple, Set<PRObject>> records = new ConcurrentHashMap<>();
    private Map<Tuple, Set<Tuple>> indexes = new ConcurrentHashMap<>();

    @Inject
    JsonKVRepository(final TPCCData tpccData) {
        LOGGER.info("Initializing KVRepository");

        tpccData.getCustomers().forEach(this::save);
        tpccData.getDistricts().forEach(this::save);
        tpccData.getHistories().forEach(this::save);
        tpccData.getItems().forEach(this::save);
        tpccData.getNewOrders().forEach(this::save);
        tpccData.getOrderLines().forEach(this::save);
        tpccData.getOrders().forEach(this::save);
        tpccData.getStocks().forEach(this::save);
        tpccData.getWarehouses().forEach(this::save);

        long totalIndexes = indexes.values().stream().flatMap(Set::stream).count();
        long totalRecords = records.values().stream().flatMap(Set::stream).count();

        LOGGER.info("KVRepository initialized");
        LOGGER.info("Total indexes: {} ", totalIndexes);
        LOGGER.info("Total values: {} ", totalRecords);
    }

    @Override
    public PRObject save(PRObject value) {
        return save(value.getKey(), value);
    }

    @Override
    public PRObject save(Tuple key, PRObject value) {
        records.computeIfAbsent(key, k -> newKeySet()).add(value);
        value.getSecondaryKeys().forEach(idx -> indexes.computeIfAbsent(idx, k -> newKeySet()).add(key));
        return value;
    }

    @Override
    public Optional<PRObject> find(Tuple key) {
        return findAll(key).stream().findFirst();
    }

    @Override
    public Set<PRObject> findAll(Tuple key) {
        Set<PRObject> objects = records.get(key);
        if (objects != null) {
            return objects;
        }
        return findAllIn(indexes.get(key));
    }

    @Override
    public Set<PRObject> findAllIn(Set<Tuple> keys) {
        return CollectionUtils.emptyIfNull(keys)
                .stream()
                .map(this::findAll)
                .flatMap(Set::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean delete(Tuple key) {
        Set<PRObject> objects = records.remove(key);

        CollectionUtils.emptyIfNull(objects)
                .stream()
                .map(PRObject::getSecondaryKeys)
                .flatMap(Set::stream)
                .map(indexes::get)
                .forEach(values -> values.removeIf(key::equals));

        return CollectionUtils.isNotEmpty(objects);
    }

}
