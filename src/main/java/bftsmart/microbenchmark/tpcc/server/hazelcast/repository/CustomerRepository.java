package bftsmart.microbenchmark.tpcc.server.hazelcast.repository;

import java.util.Collection;

import org.apache.commons.collections4.IterableUtils;
import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hazelcast.config.IndexConfig;
import com.hazelcast.config.IndexType;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.PredicateBuilder.EntryObject;
import com.hazelcast.query.Predicates;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionalMap;

import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.table.Customer.CustomerKey;
import bftsmart.microbenchmark.tpcc.util.Numbers;
import bftsmart.microbenchmark.tpcc.workload.Workload;

@Singleton
public class CustomerRepository {

    private static final String TABLE_NAME = Customer.TABLE_TYPE.name();

    private final HazelcastInstance hazelcastInstance;

    @Inject
    CustomerRepository(HazelcastInstance hazelcastInstance, Workload workload) {
        this.hazelcastInstance = hazelcastInstance;

        IMap<CustomerKey, Customer> customerMap = hazelcastInstance.getMap(TABLE_NAME);
        customerMap.addIndex(new IndexConfig(IndexType.SORTED, "warehouseId"));
        customerMap.addIndex(new IndexConfig(IndexType.SORTED, "districtId"));
        customerMap.addIndex(new IndexConfig(IndexType.SORTED, "first"));
        customerMap.addIndex(new IndexConfig(IndexType.SORTED, "last"));

        workload.getCustomers().forEach(customer -> customerMap.put(customer.createKey(), customer));
    }

    public Customer save(TransactionContext txCxt, Customer customer) {
        TransactionalMap<CustomerKey, Customer> customers = txCxt.getMap(TABLE_NAME);

        customers.put(customer.createKey(), customer);

        return customer;
    }

    public Customer find(Integer customerId, Integer districtId, Integer warehouseId) {
        IMap<CustomerKey, Customer> customers = hazelcastInstance.getMap(TABLE_NAME);
        return customers.get(new CustomerKey(warehouseId, districtId, customerId));
    }

    /**
     * Finds customers by last name.
     * 
     * @see clause 2.6.2.2 (dot 3, Case 2)
     * @param lastName
     *            The last name to find
     * @return The list of customers that match with the last name
     */
    public Customer find(String lastName, Integer districtId, Integer warehouseId) {
        IMap<Tuple, Customer> customerMap = hazelcastInstance.getMap(TABLE_NAME);
        EntryObject e = Predicates.newPredicateBuilder().getEntryObject();

        PredicateBuilder warehousePredicate = e.get("warehouseId").equal(warehouseId);
        PredicateBuilder districtPredicate = e.get("districtId").equal(districtId);
        PredicateBuilder lastNamePredicate = e.get("last").equal(warehouseId);

        Predicate<Tuple, Customer> predicate = Predicates.and(warehousePredicate, districtPredicate, lastNamePredicate);

        Collection<Customer> customers = customerMap.values(predicate);
        if (customers.isEmpty()) {
            return null;
        }

        int index = Numbers.roundEven(customers.size()) / 2 - 1;

        return IterableUtils.get(customers, index);
    }

}
