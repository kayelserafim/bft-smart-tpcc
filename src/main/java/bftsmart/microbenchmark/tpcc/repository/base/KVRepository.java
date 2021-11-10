package bftsmart.microbenchmark.tpcc.repository.base;

import java.util.Optional;
import java.util.Set;

import com.google.inject.ImplementedBy;

@ImplementedBy(JsonKVRepository.class)
public interface KVRepository<K, T> {

    T save(T value);

    T save(K key, T value);

    Optional<T> find(K key);

    Set<T> findAll(K key);

    Set<T> findAllIn(Set<K> keys);

    boolean delete(K key);

}
