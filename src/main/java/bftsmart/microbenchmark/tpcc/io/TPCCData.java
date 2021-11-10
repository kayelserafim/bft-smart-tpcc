package bftsmart.microbenchmark.tpcc.io;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.History;
import bftsmart.microbenchmark.tpcc.table.Item;
import bftsmart.microbenchmark.tpcc.table.NewOrder;
import bftsmart.microbenchmark.tpcc.table.Order;
import bftsmart.microbenchmark.tpcc.table.OrderLine;
import bftsmart.microbenchmark.tpcc.table.Stock;
import bftsmart.microbenchmark.tpcc.table.Warehouse;

public class TPCCData {

    private Long cLoad;
    private Integer warehouseCount;

    private Set<Warehouse> warehouses;
    private Set<Item> items;
    private Set<Stock> stocks;
    private Set<District> districts;
    private Set<Customer> customers;
    private Set<Order> orders;
    private Set<NewOrder> newOrders;
    private Set<OrderLine> orderLines;
    private Set<History> histories;

    public TPCCData() {
        super();
        this.warehouses = new HashSet<>();
        this.items = new HashSet<>(TPCCConfig.NB_MAX_ITEM);
        this.stocks = new HashSet<>(TPCCConfig.NB_MAX_ITEM);
        this.districts = new HashSet<>(TPCCConfig.DIST_PER_WHSE);
        this.customers = new HashSet<>(TPCCConfig.CUST_PER_DIST);
        this.orders = new HashSet<>(TPCCConfig.CUST_PER_DIST);
        this.newOrders = new HashSet<>();
        this.orderLines = new HashSet<>();
        this.histories = new HashSet<>();
    }

    public TPCCData(final Integer warehouseCount) {
        this();
        this.warehouseCount = warehouseCount;
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

    public void setWarehouses(Set<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    public Set<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Set<District> districts) {
        this.districts = districts;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<NewOrder> getNewOrders() {
        return newOrders;
    }

    public void setNewOrders(Set<NewOrder> newOrders) {
        this.newOrders = newOrders;
    }

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Set<History> getHistories() {
        return histories;
    }

    public void setHistories(Set<History> histories) {
        this.histories = histories;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String info() {
        StringBuilder sb = new StringBuilder(12);

        sb.append("CLoad ").append(getcLoad()).append(".\n");
        sb.append("Total of Warehouses ").append(getWarehouses().size()).append(".\n");
        sb.append("Total of Districts ").append(getDistricts().size()).append(".\n");
        sb.append("Total of Customer ").append(getCustomers().size()).append(".\n");
        sb.append("Total of Stocks ").append(getStocks().size()).append(".\n");
        sb.append("Total of Histories ").append(getHistories().size()).append(".\n");
        sb.append("Total of Items ").append(getItems().size()).append(".\n");
        sb.append("Total of NewOrders ").append(getNewOrders().size()).append(".\n");
        sb.append("Total of OrderLines ").append(getOrderLines().size()).append(".\n");
        sb.append("Total of Orders ").append(getOrders().size()).append(".\n");

        return sb.toString();
    }

}
