package bftsmart.microbenchmark.tpcc.repository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.javatuples.Tuple;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.exception.NotFoundException;
import bftsmart.microbenchmark.tpcc.repository.base.KVRepository;
import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.util.Numbers;

@Singleton
public class CustomerRepository {

    @Inject
    private KVRepository<Tuple, Customer> customerDao;

    public Optional<Customer> find(int customerId, int districtId, int warehouseId) {
        return customerDao.find(Customer.key(warehouseId, districtId, customerId));
    }

    public Customer findBy(int customerId, int districtId, int warehouseId) {
        return find(customerId, districtId, warehouseId)
                .orElseThrow(() -> new NotFoundException("Customer %s not found", customerId));
    }

    public Customer save(Customer customer) {
        return customerDao.save(customer);
    }

    /**
     * Finds customers by last name.
     * 
     * @see clause 2.6.2.2 (dot 3, Case 2)
     * @param lastName
     *            The last name to find
     * @return The list of customers that match with the last name
     */
    public Customer findBy(String lastName, int districtId, int warehouseId) {
        List<Customer> customers = customerDao.findAll(Customer.districtKey(warehouseId, districtId))
                .stream()
                .filter(customer -> Objects.equals(customer.getLast(), lastName))
                .sorted(Comparator.comparing(Customer::getFirst))
                .collect(Collectors.toList());

        if (customers.isEmpty()) {
            return null;
        }

        int index = Numbers.roundEven(customers.size()) / 2 - 1;
        return customers.get(index);
    }

}
