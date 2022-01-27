package bftsmart.microbenchmark.tpcc.repository.base;

import java.util.Optional;
import java.util.Set;

import com.google.inject.ImplementedBy;

@ImplementedBy(JsonKVRepository.class)
public interface KVRepository<K, V> {

    V save(V value);

    V save(K key, V value);

    Optional<V> find(K key);

    Set<V> findAll(K key);

    Set<V> findAllIn(Set<K> keys);

    boolean delete(K key);

}
