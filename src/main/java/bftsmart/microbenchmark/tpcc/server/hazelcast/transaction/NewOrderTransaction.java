package bftsmart.microbenchmark.tpcc.server.hazelcast.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.google.inject.Inject;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionOptions;

import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.CustomerRepository;
import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.DistrictRepository;
import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.ItemRepository;
import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.NewOrderRepository;
import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.OrderLineRepository;
import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.OrderRepository;
import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.StockRepository;
import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.WarehouseRepository;
import bftsmart.microbenchmark.tpcc.server.transaction.Transaction;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionResponse;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionType;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.request.NewOrderRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.response.NewOrderLineResponse;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.response.NewOrderResponse;
import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.Item;
import bftsmart.microbenchmark.tpcc.table.NewOrder;
import bftsmart.microbenchmark.tpcc.table.Order;
import bftsmart.microbenchmark.tpcc.table.OrderLine;
import bftsmart.microbenchmark.tpcc.table.Stock;
import bftsmart.microbenchmark.tpcc.table.Warehouse;
import bftsmart.microbenchmark.tpcc.util.Dates;

public class NewOrderTransaction implements Transaction {

    private static final TransactionResponse TRANSACTION_ABORTED =
            new TransactionResponse().withStatus(-1).withResponse("TRANSACTION_ABORTED");

    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private WarehouseRepository warehouseRepository;
    @Inject
    private DistrictRepository districtRepository;
    @Inject
    private NewOrderRepository newOrderRepository;
    @Inject
    private OrderRepository orderRepository;
    @Inject
    private ItemRepository itemRepository;
    @Inject
    private StockRepository stockRepository;
    @Inject
    private OrderLineRepository orderLineRepository;

    @Inject
    private TransactionOptions transactionOptions;
    @Inject
    private HazelcastInstance hazelcastInstance;

    @Override
    public TransactionType transactionType() {
        return TransactionType.NEW_ORDER;
    }

    @Override
    public TransactionResponse process(final TransactionRequest command) {
        TransactionContext txCxt = hazelcastInstance.newTransactionContext(transactionOptions);
        txCxt.beginTransaction();

        NewOrderRequest input = (NewOrderRequest) command;
        NewOrderResponse orderResponse = new NewOrderResponse().withDateTime(LocalDateTime.now())
                .withOrderLines(new NewOrderLineResponse[input.getOrderLineCnt()]);

        Integer warehouseId = input.getWarehouseId();
        Integer districtId = input.getDistrictId();
        Integer customerId = input.getCustomerId();

        // The row in the WAREHOUSE table with matching W_ID is selected and
        // W_TAX, the warehouse tax rate, is retrieved.
        Warehouse warehouse = warehouseRepository.find(warehouseId);
        orderResponse.withWarehouseId(warehouse.getWarehouseId()).withWarehouseTax(warehouse.getTax());

        // The row in the DISTRICT table with matching D_W_ID and D_ ID is
        // selected, D_TAX, the district tax rate, is retrieved, and
        // D_NEXT_O_ID, the next available order number for
        // the district, is retrieved and incremented by one.
        District district = districtRepository.find(districtId, warehouseId);
        Integer nextOrderId = district.getNextOrderId();
        orderResponse.withDistrictId(district.getDistrictId()).withDistrictTax(district.getTax());

        // The row in the CUSTOMER table with matching C_W_ID, C_D_ID, and C_ID
        // is selected and C_DISCOUNT, the customer's discount rate, C_LAST, the
        // customer's last name, and C_CREDIT, the customer's credit status, are
        // retrieved.
        Customer customer = customerRepository.find(customerId, districtId, warehouseId);
        orderResponse.withCustomerId(customer.getCustomerId())
                .withCustomerLast(customer.getLast())
                .withCustomerCredit(customer.getCredit())
                .withDiscount(customer.getDiscount());

        // A new row is inserted into both the NEW-ORDER table and the ORDER
        // table to reflect the creation of the new order. O_CARRIER_ID is set
        // to a null value. If the order includes only home order-lines, then
        // O_ALL_LOCAL is set to 1, otherwise O_ALL_LOCAL is set to 0.
        NewOrder newOrder =
                new NewOrder().withOrderId(nextOrderId).withDistrictId(districtId).withWarehouseId(warehouseId);
        newOrderRepository.save(txCxt, newOrder);
        orderResponse.withOrderId(newOrder.getOrderId());

        districtRepository.save(txCxt, district.nextOrderIdIncrement());

        // The number of items, O_OL_CNT, is computed to match ol_cnt
        Order order = new Order().withOrderId(nextOrderId)
                .withDistrictId(input.getDistrictId())
                .withWarehouseId(input.getWarehouseId())
                .withCustomerId(input.getCustomerId())
                .withEntryDate(Dates.now())
                .withOrderLineCounter(input.getOrderLineCnt())
                .withAllLocal(input.getOrderAllLocal());

        orderRepository.save(txCxt, order);
        // For each O_OL_CNT item on the order: see clause 2.4.2.2 (dot 8)
        for (int index = 1; index <= order.getOrderLineCounter(); index++) {
            NewOrderLineResponse newOrderLineResponse = new NewOrderLineResponse();
            int supplyWarehouseId = input.getSupplierWarehouseIds()[index - 1];
            int itemId = input.getItemIds()[index - 1];
            int quantity = input.getOrderQuantities()[index - 1];

            newOrderLineResponse.withSupplierWarehouseId(supplyWarehouseId)
                    .withItemId(itemId)
                    .withOrderQuantities(quantity);
            // If I_ID has an unused value (see Clause 2.4.1.5), a "not-found"
            // condition is signaled, resulting in a rollback of the database
            // transaction (see Clause 2.4.2.3).
            if (itemId == -12345) {
                // an expected condition generated 1% of the time in the test
                // data...
                // we throw an illegal access exception and the transaction gets
                // rolled back later on
                txCxt.rollbackTransaction();
                return TRANSACTION_ABORTED;
            }

            // The row in the ITEM table with matching I_ID (equals OL_I_ID) is
            // selected and I_PRICE, the price of the item, I_NAME, the name of
            // the item, and I_DATA are retrieved.
            Item item = itemRepository.find(itemId);
            if (item == null) {
                txCxt.rollbackTransaction();
                return TRANSACTION_ABORTED;
            }
            newOrderLineResponse.withItemId(item.getItemId())
                    .withItemName(item.getName())
                    .withItemPrice(item.getPrice());

            // clause 2.4.2.2 (dot 8.2)
            Stock stock = stockRepository.find(itemId, supplyWarehouseId);
            if (stock == null) {
                txCxt.rollbackTransaction();
                return TRANSACTION_ABORTED;
            }

            newOrderLineResponse.withStockQuantities(stock.getQuantity());

            stock.addQuantity(quantity)
                    .remoteCntIncrement(supplyWarehouseId != warehouseId)
                    .addYearToDate(quantity)
                    .orderCountIncrement();

            stockRepository.save(txCxt, stock);

            // clause 2.4.2.2 (dot 8.3)
            BigDecimal amount = item.getPrice().multiply(BigDecimal.valueOf(quantity));
            Character brandGeneric;
            // clause 2.4.2.2 (dot 8.4)
            if (item.getData().indexOf("GENERIC") != -1 && stock.getData().indexOf("GENERIC") != -1) {
                brandGeneric = 'B';
            } else {
                brandGeneric = 'G';
            }

            newOrderLineResponse.withOrderLineAmounts(amount).withBrandGeneric(brandGeneric);

            orderResponse.withOrderLine(index - 1, newOrderLineResponse);

            // clause 2.4.2.2 (dot 9)
            orderResponse.withTotalAmount(amount.multiply(BigDecimal.ONE.subtract(customer.getDiscount())
                    .multiply(BigDecimal.ONE.add(warehouse.getTax()).add(district.getTax()))));

            String districtInfo = getDistrictInfo(district, stock);

            OrderLine orderLine = new OrderLine().withOrderId(nextOrderId)
                    .withDistrictId(districtId)
                    .withWarehouseId(warehouseId)
                    .withNumber(index)
                    .withItemId(itemId)
                    .withSupplyWarehouseId(supplyWarehouseId)
                    .withQuantity(quantity)
                    .withAmount(amount)
                    .withDistrictInfo(districtInfo);

            orderLineRepository.save(txCxt, orderLine);
        }
        txCxt.commitTransaction();
        return new TransactionResponse().withStatus(0).withResponse(outputScreen(orderResponse));
    }

    private String getDistrictInfo(District district, Stock stock) {
        String districtInfo;
        switch (district.getDistrictId()) {
        case 1:
            districtInfo = stock.getDistrict01();
            break;
        case 2:
            districtInfo = stock.getDistrict02();
            break;
        case 3:
            districtInfo = stock.getDistrict03();
            break;
        case 4:
            districtInfo = stock.getDistrict04();
            break;
        case 5:
            districtInfo = stock.getDistrict05();
            break;
        case 6:
            districtInfo = stock.getDistrict06();
            break;
        case 7:
            districtInfo = stock.getDistrict07();
            break;
        case 8:
            districtInfo = stock.getDistrict08();
            break;
        case 9:
            districtInfo = stock.getDistrict09();
            break;
        case 10:
        default:
            districtInfo = stock.getDistrict10();
        }
        return districtInfo;
    }

    private String outputScreen(NewOrderResponse newOrder) {
        // clause 2.4.3.3
        StringBuilder message = new StringBuilder();
        message.append("\n+--------------------------- NEW-ORDER ---------------------------+\n");
        message.append(" Date: ");
        message.append(Dates.format(newOrder.getDateTime(), Dates.DATE_TIME_FORMAT));
        message.append("\n\n Warehouse: ");
        message.append(newOrder.getWarehouseId());
        message.append("\n   Tax:     ");
        message.append(newOrder.getWarehouseTax());
        message.append("\n District:  ");
        message.append(newOrder.getDistrictId());
        message.append("\n   Tax:     ");
        message.append(newOrder.getDistrictTax());
        message.append("\n Order:     ");
        message.append(newOrder.getOrderId());
        message.append("\n   Lines:   ");
        message.append(newOrder.getOrderLines().length);
        message.append("\n\n Customer:  ");
        message.append(newOrder.getCustomerId());
        message.append("\n   Name:    ");
        message.append(newOrder.getCustomerLast());
        message.append("\n   Credit:  ");
        message.append(newOrder.getCustomerCredit());
        message.append("\n   %Disc:   ");
        message.append(newOrder.getDiscount());
        message.append("\n\n Order-Line List [Supp_W - Item_ID - Item Name - Qty - Stock - B/G - Price - Amount]\n");
        for (NewOrderLineResponse orderLine : newOrder.getOrderLines()) {
            message.append("                 [");
            message.append(orderLine.getSupplierWarehouseId());
            message.append(" - ");
            message.append(orderLine.getItemId());
            message.append(" - ");
            message.append(orderLine.getItemName());
            message.append(" - ");
            message.append(orderLine.getOrderQuantities());
            message.append(" - ");
            message.append(orderLine.getStockQuantities());
            message.append(" - ");
            message.append(orderLine.getBrandGeneric());
            message.append(" - ");
            message.append(orderLine.getItemPrice());
            message.append(" - ");
            message.append(orderLine.getOrderLineAmounts());
            message.append("]\n");
        }
        message.append("\n\n Total Amount: ");
        message.append(newOrder.getTotalAmount());
        message.append("\n\n Execution Status: New order placed!\n");
        message.append("+-----------------------------------------------------------------+\n\n");
        return message.toString();
    }

}
