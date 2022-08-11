package bftsmart.microbenchmark.tpcc.workload;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
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
import bftsmart.microbenchmark.tpcc.util.Dates;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

@Singleton
public class WorkloadGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkloadGenerator.class);

    private final TPCCRandom random = new TPCCRandom();

    private final Workload workload;
    private final TPCCConfig tpccConfig;
    private final WorkloadFile workloadFile;

    @Inject
    WorkloadGenerator(TPCCConfig tpccConfig, WorkloadFile workloadFile) {
        this.workload = new Workload();
        this.tpccConfig = tpccConfig;
        this.workloadFile = workloadFile;
    }

    public void start() {
        generateData();
        writeFile();
    }

    public void writeFile() {
        String info = workload.toString();
        LOGGER.info("saving data to file. ");
        workloadFile.writeWorkloadToFile(workload);
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
        workload.setcLoad(random.getCLoad());
        LOGGER.info("Data generated in {} seconds.", Duration.between(start, Instant.now()).getSeconds());
    }

    private void createWarehouses() {
        for (int w = 1; w <= tpccConfig.getWarehouses(); w++) {
            Warehouse warehouse = new Warehouse().withWarehouseId(w)
                    .withName(random.getAString(6, 10))
                    .withStreet1(random.getAString(10, 20))
                    .withStreet2(random.getAString(10, 20))
                    .withCity(random.getAString(10, 20))
                    .withState(random.getAString(2))
                    .withZip(random.getAZip())
                    .withTax(BigDecimal.valueOf(random.getAPercent(0, 0.2)))
                    .withYearToDateBalance(BigDecimal.valueOf(300000.00));

            workload.getWarehouses().add(warehouse);
        }
    }

    private void createDistricts() {
        for (int w = 1; w <= tpccConfig.getWarehouses(); w++) {
            for (int d = 1; d <= TPCCConstants.DIST_PER_WHSE; d++) {
                District district = new District().withDistrictId(d)
                        .withWarehouseId(w)
                        .withName(random.getAString(6, 10))
                        .withStreet1(random.getAString(10, 20))
                        .withStreet2(random.getAString(10, 20))
                        .withCity(random.getAString(10, 20))
                        .withState(random.getAString(2))
                        .withZip(random.getAZip())
                        .withTax(BigDecimal.valueOf(random.getAPercent(0, 0.2)))
                        .withYearToDateBalance(BigDecimal.valueOf(30000))
                        .withNextOrderId(3001);

                workload.getDistricts().add(district);
            }
        }
    }

    private void createCustomers() {
        for (int w = 1; w <= tpccConfig.getWarehouses(); w++) {
            for (int d = 1; d <= TPCCConstants.DIST_PER_WHSE; d++) {
                for (int c = 1; c <= TPCCConstants.CUST_PER_DIST; c++) {
                    Customer customer = new Customer().withCustomerId(c)
                            .withDistrictId(d)
                            .withWarehouseId(w)
                            .withLast(random.getCLast())
                            .withMiddle("OE")
                            .withFirst(random.getAString(8, 16))
                            .withStreet1(random.getAString(10, 20))
                            .withStreet2(random.getAString(10, 20))
                            .withCity(random.getAString(10, 20))
                            .withState(random.getAString(2))
                            .withZip(random.getAZip())
                            .withPhone(random.getNString(16))
                            .withSince(Dates.now())
                            .withCredit(random.nextInt(1, 10) == 1 ? "BC" : "GC")
                            .withCreditLimit(BigDecimal.valueOf(50000))
                            .withDiscount(BigDecimal.valueOf(random.getAPercent(0, 0.5)))
                            .withBalance(BigDecimal.TEN.negate())
                            .withYearToDateBalancePayment(BigDecimal.TEN)
                            .withPaymentCnt(1)
                            .withDeliveryCnt(0)
                            .withData(random.getAString(300, 500));

                    workload.getCustomers().add(customer);
                }
            }
        }
    }

    private void createItems() {
        for (int i = 1; i <= TPCCConstants.NB_MAX_ITEM; i++) {
            Item item = new Item().withItemId(i)
                    .withImageId(random.nextInt(1, 10000))
                    .withName(random.getAString(14, 24))
                    .withPrice(BigDecimal.valueOf(random.nextDouble(1, 100)))
                    .withData(random.getData());

            workload.getItems().add(item);
        }
    }

    private void createStocks() {
        for (int w = 1; w <= tpccConfig.getWarehouses(); w++) {
            for (int i = 1; i <= TPCCConstants.NB_MAX_ITEM; i++) {
                Stock stock = new Stock().withItemId(i)
                        .withWarehouseId(w)
                        .withQuantity(random.nextInt(10, 100))
                        .withYearToDate(0L)
                        .withOrderCount(0)
                        .withRemoteCount(0)
                        .withDistrict01(random.getAString(24))
                        .withDistrict02(random.getAString(24))
                        .withDistrict03(random.getAString(24))
                        .withDistrict04(random.getAString(24))
                        .withDistrict05(random.getAString(24))
                        .withDistrict06(random.getAString(24))
                        .withDistrict07(random.getAString(24))
                        .withDistrict08(random.getAString(24))
                        .withDistrict09(random.getAString(24))
                        .withDistrict10(random.getAString(24))
                        .withData(random.getData());

                workload.getStocks().add(stock);
            }
        }
    }

    private void createHistory() {
        for (int w = 1; w <= tpccConfig.getWarehouses(); w++) {
            for (int d = 1; d <= TPCCConstants.DIST_PER_WHSE; d++) {
                for (Customer customer : workload.getCustomers()) {
                    History history = new History().withCustomerId(customer.getCustomerId())
                            .withDistrictId(d)
                            .withWarehouseId(w)
                            .withCustomerDistrictId(customer.getDistrictId())
                            .withCustomerWarehouseId(customer.getWarehouseId())
                            .withDate(Dates.now())
                            .withAmount(BigDecimal.TEN)
                            .withData(random.getAString(12, 24));

                    workload.getHistories().add(history);
                }
            }
        }
    }

    /**
     * For the ORDER rows the TPC-C specification demands that they are
     * generated using a random permutation of all 3,000 customers
     */
    private void createOrder() {
        for (int w = 1; w <= tpccConfig.getWarehouses(); w++) {
            for (int d = 1; d <= TPCCConstants.DIST_PER_WHSE; d++) {
                for (int o = 1; o <= TPCCConstants.CUST_PER_DIST; o++) {
                    int c = random.nextInt(1, TPCCConstants.CUST_PER_DIST);

                    // carrierId: random within [1 .. 10] if O_ID < 2,101, null otherwise
                    Order order = new Order().withOrderId(o)
                            .withDistrictId(d)
                            .withWarehouseId(w)
                            .withCustomerId(c)
                            .withEntryDate(Dates.now())
                            .withCarrierId(o < TPCCConstants.LIMIT_ORDER ? random.nextInt(1, 10) : -1)
                            .withOrderLineCounter(random.nextInt(5, 15))
                            .withAllLocal(1);

                    workload.getOrders().add(order);

                    // 900 rows in the NEW-ORDER table corresponding to the last
                    // 900 rows in the ORDER table for that district (i.e., with
                    // NO_O_ID between 2,101 and 3,000) (70%)
                    if (o >= TPCCConstants.LIMIT_ORDER) {
                        NewOrder newOrder = new NewOrder().withOrderId(o).withDistrictId(d).withWarehouseId(w);

                        workload.getNewOrders().add(newOrder);
                    }

                    createOrderLine(w, d, order);
                }
            }
        }
    }

    private void createOrderLine(int w, int d, Order order) {
        for (int ol = 1; ol <= order.getOrderLineCounter(); ol++) {
            OrderLine orderLine = new OrderLine().withOrderId(order.getOrderId())
                    .withDistrictId(d)
                    .withWarehouseId(w)
                    .withNumber(ol)
                    .withSupplyWarehouseId(w)
                    .withQuantity(5)
                    .withItemId(random.getItemID())
                    .withDeliveryDateTime(ol < TPCCConstants.LIMIT_ORDER ? order.getEntryDate() : -1)
                    .withAmount(ol < TPCCConstants.LIMIT_ORDER ? BigDecimal.ZERO : random.nextBigDecimal(0.01, 9999.99))
                    .withDistrictInfo(random.getAString(24));

            workload.getOrderLines().add(orderLine);
        }
    }

}
