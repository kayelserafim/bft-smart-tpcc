package bftsmart.microbenchmark.tpcc.io;

import java.util.HashSet;
import java.util.Set;

import bftsmart.microbenchmark.tpcc.config.TPCCConstants;
import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.History;
import bftsmart.microbenchmark.tpcc.table.Item;
import bftsmart.microbenchmark.tpcc.table.NewOrder;
import bftsmart.microbenchmark.tpcc.table.Order;
import bftsmart.microbenchmark.tpcc.table.OrderLine;
import bftsmart.microbenchmark.tpcc.table.Stock;
import bftsmart.microbenchmark.tpcc.table.Warehouse;

public class Workload {

    private Long cLoad;
    private Integer warehouseCount;

    private final Set<Warehouse> warehouses;
    private final Set<Item> items;
    private final Set<Stock> stocks;
    private final Set<District> districts;
    private final Set<Customer> customers;
    private final Set<Order> orders;
    private final Set<NewOrder> newOrders;
    private final Set<OrderLine> orderLines;
    private final Set<History> histories;

    public Workload() {
        super();
        this.warehouses = new HashSet<>();
        this.items = new HashSet<>(TPCCConstants.NB_MAX_ITEM);
        this.stocks = new HashSet<>(TPCCConstants.NB_MAX_ITEM);
        this.districts = new HashSet<>(TPCCConstants.DIST_PER_WHSE);
        this.customers = new HashSet<>(TPCCConstants.CUST_PER_DIST);
        this.orders = new HashSet<>(TPCCConstants.CUST_PER_DIST);
        this.newOrders = new HashSet<>();
        this.orderLines = new HashSet<>();
        this.histories = new HashSet<>();
    }

    public Workload(final Integer warehouseCount) {
        this();
        setWarehouseCount(warehouseCount);
    }

    public Long getcLoad() {
        return cLoad;
    }

    public void setcLoad(Long cLoad) {
        this.cLoad = cLoad;
    }

    public Integer getWarehouseCount() {
        return warehouseCount;
    }

    public void setWarehouseCount(Integer warehouseCount) {
        this.warehouseCount = warehouseCount;
    }

    public Set<Warehouse> getWarehouses() {
        return warehouses;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public Set<District> getDistricts() {
        return districts;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Set<NewOrder> getNewOrders() {
        return newOrders;
    }

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public Set<History> getHistories() {
        return histories;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("CLoad ").append(getcLoad()).append(System.lineSeparator());
        sb.append("Total of Warehouses ").append(getWarehouses().size()).append(System.lineSeparator());
        sb.append("Total of Districts ").append(getDistricts().size()).append(System.lineSeparator());
        sb.append("Total of Customer ").append(getCustomers().size()).append(System.lineSeparator());
        sb.append("Total of Stocks ").append(getStocks().size()).append(System.lineSeparator());
        sb.append("Total of Histories ").append(getHistories().size()).append(System.lineSeparator());
        sb.append("Total of Items ").append(getItems().size()).append(System.lineSeparator());
        sb.append("Total of NewOrders ").append(getNewOrders().size()).append(System.lineSeparator());
        sb.append("Total of OrderLines ").append(getOrderLines().size()).append(System.lineSeparator());
        sb.append("Total of Orders ").append(getOrders().size()).append(System.lineSeparator());

        return sb.toString();
    }

}
