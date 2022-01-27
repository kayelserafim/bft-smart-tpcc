package bftsmart.microbenchmark.tpcc.io;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;
import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.History;
import bftsmart.microbenchmark.tpcc.table.Item;
import bftsmart.microbenchmark.tpcc.table.NewOrder;
import bftsmart.microbenchmark.tpcc.table.Order;
import bftsmart.microbenchmark.tpcc.table.OrderLine;
import bftsmart.microbenchmark.tpcc.table.Stock;
import bftsmart.microbenchmark.tpcc.table.Warehouse;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;
import bftsmart.microbenchmark.tpcc.util.Times;

@Singleton
public class TPCCDataGenerator {

    public static final Logger LOGGER = LoggerFactory.getLogger(TPCCDataGenerator.class);

    private TPCCRandom random = new TPCCRandom();

    private final TPCCData tpccData;
    private final TPCCJsonWriter jsonWriter;

    @Inject
    TPCCDataGenerator(WorkloadConfig workloadConfig, TPCCJsonWriter jsonWriter) {
        this.tpccData = new TPCCData(workloadConfig.getWarehouses());
        this.jsonWriter = jsonWriter;
    }

    public void start() {
        generateData();
        writeFile();
    }

    public void writeFile() {
        String info = tpccData.info();
        LOGGER.info("saving data to file. ");
        jsonWriter.writeToJsonFile(tpccData);
        LOGGER.info("data saved in file: {}", info);
    }

    public void generateData() {
        Instant start = Instant.now();
        LOGGER.info("generating data. ");
        createWarehouses();
        createDistricts();
        createCustomers();
        createItems();
        createStocks();
        createOrder();
        createHistory();
        tpccData.setcLoad(random.getCLoad());
        LOGGER.info("Data generated in {} seconds.", Duration.between(start, Instant.now()).getSeconds());
    }

    private void createWarehouses() {
        for (int w = 1; w <= tpccData.getWarehouseCount(); w++) {
            Warehouse warehouse = Warehouse.builder()
                    .warehouseId(w)
                    .name(random.getAString(6, 10))
                    .street1(random.getAString(10, 20))
                    .street2(random.getAString(10, 20))
                    .city(random.getAString(10, 20))
                    .state(random.getAString(2))
                    .zip(random.getAZip())
                    .tax(BigDecimal.valueOf(random.getAPercent(0, 0.2)))
                    .yearToDateBalance(BigDecimal.valueOf(300000.00))
                    .build();

            tpccData.getWarehouses().add(warehouse);
        }
    }

    private void createDistricts() {
        for (int w = 1; w <= tpccData.getWarehouseCount(); w++) {
            for (int d = 1; d <= TPCCConfig.DIST_PER_WHSE; d++) {
                District district = District.builder()
                        .districtId(d)
                        .warehouseId(w)
                        .name(random.getAString(6, 10))
                        .street1(random.getAString(10, 20))
                        .street2(random.getAString(10, 20))
                        .city(random.getAString(10, 20))
                        .state(random.getAString(2))
                        .zip(random.getAZip())
                        .tax(BigDecimal.valueOf(random.getAPercent(0, 0.2)))
                        .yearToDateBalance(BigDecimal.valueOf(30000))
                        .nextOrderId(3001)
                        .build();

                tpccData.getDistricts().add(district);
            }
        }
    }

    private void createCustomers() {
        for (int w = 1; w <= tpccData.getWarehouseCount(); w++) {
            for (int d = 1; d <= TPCCConfig.DIST_PER_WHSE; d++) {
                for (int c = 1; c <= TPCCConfig.CUST_PER_DIST; c++) {
                    Customer customer = Customer.builder()
                            .customerId(c)
                            .districtId(d)
                            .warehouseId(w)
                            .last(random.getCLast())
                            .middle("OE")
                            .first(random.getAString(8, 16))
                            .street1(random.getAString(10, 20))
                            .street2(random.getAString(10, 20))
                            .city(random.getAString(10, 20))
                            .state(random.getAString(2))
                            .zip(random.getAZip())
                            .phone(random.getNString(16))
                            .since(Times.currentTimeMillis())
                            .credit(random.nextInt(1, 10) == 1 ? "BC" : "GC")
                            .creditLimit(BigDecimal.valueOf(50000))
                            .discount(BigDecimal.valueOf(random.getAPercent(0, 0.5)))
                            .balance(BigDecimal.TEN.negate())
                            .yearToDateBalancePayment(BigDecimal.TEN)
                            .paymentCnt(1)
                            .deliveryCnt(0)
                            .data(random.getAString(300, 500))
                            .build();

                    tpccData.getCustomers().add(customer);
                }
            }
        }
    }

    private void createItems() {
        for (int i = 1; i <= TPCCConfig.NB_MAX_ITEM; i++) {
            Item item = Item.builder()
                    .itemId(i)
                    .imageId(random.nextInt(1, 10000))
                    .name(random.getAString(14, 24))
                    .price(BigDecimal.valueOf(random.nextDouble(1, 100)))
                    .data(random.getData())
                    .build();

            tpccData.getItems().add(item);
        }
    }

    private void createStocks() {
        for (int w = 1; w <= tpccData.getWarehouseCount(); w++) {
            for (int i = 1; i <= TPCCConfig.NB_MAX_ITEM; i++) {
                Stock stock = Stock.builder()
                        .itemId(i)
                        .warehouseId(w)
                        .quantity(random.nextInt(10, 100))
                        .yearToDate(0L)
                        .orderCount(0)
                        .remoteCount(0)
                        .district01(random.getAString(24))
                        .district02(random.getAString(24))
                        .district03(random.getAString(24))
                        .district04(random.getAString(24))
                        .district05(random.getAString(24))
                        .district06(random.getAString(24))
                        .district07(random.getAString(24))
                        .district08(random.getAString(24))
                        .district09(random.getAString(24))
                        .district10(random.getAString(24))
                        .data(random.getData())
                        .build();

                tpccData.getStocks().add(stock);
            }
        }
    }

    private void createHistory() {
        for (int w = 1; w <= tpccData.getWarehouseCount(); w++) {
            for (int d = 1; d <= TPCCConfig.DIST_PER_WHSE; d++) {
                for (Customer customer : tpccData.getCustomers()) {
                    History history = History.builder()
                            .customerId(customer.getCustomerId())
                            .districtId(d)
                            .warehouseId(w)
                            .customerDistrictId(customer.getDistrictId())
                            .customerWarehouseId(customer.getWarehouseId())
                            .date(Times.currentTimeMillis())
                            .amount(BigDecimal.TEN)
                            .data(random.getAString(12, 24))
                            .build();

                    tpccData.getHistories().add(history);
                }
            }
        }
    }

    /**
     * For the ORDER rows the TPC-C specification demands that they are
     * generated using a random permutation of all 3,000 customers
     */
    private void createOrder() {
        for (int w = 1; w <= tpccData.getWarehouseCount(); w++) {
            for (int d = 1; d <= TPCCConfig.DIST_PER_WHSE; d++) {
                for (int o = 1; o <= TPCCConfig.CUST_PER_DIST; o++) {
                    int c = random.nextInt(1, TPCCConfig.CUST_PER_DIST);

                    // random within [1 .. 10] if O_ID < 2,101, null otherwise
                    Order order = Order.builder()
                            .orderId(o)
                            .districtId(d)
                            .warehouseId(w)
                            .customerId(c)
                            .entryDate(Times.currentTimeMillis())
                            .carrierId(o < TPCCConfig.LIMIT_ORDER ? random.nextInt(1, 10) : null)
                            .orderLineCounter(random.nextInt(5, 15))
                            .allLocal(1)
                            .build();

                    tpccData.getOrders().add(order);

                    // 900 rows in the NEW-ORDER table corresponding to the last
                    // 900 rows in the ORDER table for that district (i.e., with
                    // NO_O_ID between 2,101 and 3,000) (70%)
                    if (o >= TPCCConfig.LIMIT_ORDER) {
                        NewOrder newOrder = NewOrder.builder().orderId(o).districtId(d).warehouseId(w).build();

                        tpccData.getNewOrders().add(newOrder);
                    }

                    createOrderLine(w, d, order);
                }
            }
        }
    }

    private void createOrderLine(int w, int d, Order order) {
        for (int ol = 1; ol <= order.getOrderLineCounter(); ol++) {
            OrderLine orderLine = OrderLine.builder()
                    .orderId(order.getOrderId())
                    .districtId(d)
                    .warehouseId(w)
                    .number(ol)
                    .supplyWarehouseId(w)
                    .quantity(5)
                    .itemId(random.getItemID())
                    .deliveryDateTime(ol < TPCCConfig.LIMIT_ORDER ? order.getEntryDate() : null)
                    .amount(ol < TPCCConfig.LIMIT_ORDER ? BigDecimal.ZERO : random.nextBigDecimal(0.01, 9999.99))
                    .districtInfo(random.getAString(24))
                    .build();

            tpccData.getOrderLines().add(orderLine);
        }
    }

}
