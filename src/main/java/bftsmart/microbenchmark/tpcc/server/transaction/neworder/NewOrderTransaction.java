package bftsmart.microbenchmark.tpcc.server.transaction.neworder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;
import bftsmart.microbenchmark.tpcc.repository.CustomerRepository;
import bftsmart.microbenchmark.tpcc.repository.DistrictRepository;
import bftsmart.microbenchmark.tpcc.repository.ItemRepository;
import bftsmart.microbenchmark.tpcc.repository.NewOrderRepository;
import bftsmart.microbenchmark.tpcc.repository.OrderLineRepository;
import bftsmart.microbenchmark.tpcc.repository.OrderRepository;
import bftsmart.microbenchmark.tpcc.repository.StockRepository;
import bftsmart.microbenchmark.tpcc.repository.WarehouseRepository;
import bftsmart.microbenchmark.tpcc.server.transaction.Transaction;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.input.NewOrderInput;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.output.NewOrderOutput;
import bftsmart.microbenchmark.tpcc.server.transaction.neworder.output.OrderLineOutput;
import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.Item;
import bftsmart.microbenchmark.tpcc.table.NewOrder;
import bftsmart.microbenchmark.tpcc.table.Order;
import bftsmart.microbenchmark.tpcc.table.OrderLine;
import bftsmart.microbenchmark.tpcc.table.Stock;
import bftsmart.microbenchmark.tpcc.table.Warehouse;
import bftsmart.microbenchmark.tpcc.util.Times;

public class NewOrderTransaction implements Transaction {

    private static final String TRANSACTION_ABORTED = "TRANSACTION_ABORTED";

    @Inject
    private ObjectMapper objectMapper;
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

    @Override
    public TPCCCommandType commandType() {
        return TPCCCommandType.NEW_ORDER;
    }

    @Override
    public TPCCCommand process(final TPCCCommand aRequest) {
        Map<String, Serializable> params = aRequest.getRequest();

        NewOrderOutput.Builder orderBuilder = NewOrderOutput.builder().dateTime(LocalDateTime.now());

        NewOrderInput input = objectMapper.convertValue(params, NewOrderInput.class);

        Integer warehouseId = input.getWarehouseId();
        Integer districtId = input.getDistrictId();
        Integer customerId = input.getCustomerId();

        // The row in the WAREHOUSE table with matching W_ID is selected and
        // W_TAX, the warehouse tax rate, is retrieved.
        Warehouse warehouse = warehouseRepository.findBy(warehouseId);
        orderBuilder.warehouseId(warehouse.getWarehouseId()).warehouseTax(warehouse.getTax());

        // The row in the DISTRICT table with matching D_W_ID and D_ ID is
        // selected, D_TAX, the district tax rate, is retrieved, and
        // D_NEXT_O_ID, the next available order number for
        // the district, is retrieved and incremented by one.
        District district = districtRepository.findBy(districtId, warehouseId);
        Integer nextOrderId = district.getNextOrderId();
        orderBuilder.districtId(district.getDistrictId()).districtTax(district.getTax());

        // The row in the CUSTOMER table with matching C_W_ID, C_D_ID, and C_ID
        // is selected and C_DISCOUNT, the customer's discount rate, C_LAST, the
        // customer's last name, and C_CREDIT, the customer's credit status, are
        // retrieved.
        Customer customer = customerRepository.findBy(customerId, districtId, warehouseId);
        orderBuilder.customerId(customer.getCustomerId())
                .customerLast(customer.getLast())
                .customerCredit(customer.getCredit())
                .discount(customer.getDiscount())
                .orderId(nextOrderId);

        // A new row is inserted into both the NEW-ORDER table and the ORDER
        // table to reflect the creation of the new order. O_CARRIER_ID is set
        // to a null value. If the order includes only home order-lines, then
        // O_ALL_LOCAL is set to 1, otherwise O_ALL_LOCAL is set to 0.
        NewOrder newOrder =
                NewOrder.builder().orderId(nextOrderId).districtId(districtId).warehouseId(warehouseId).build();

        // The number of items, O_OL_CNT, is computed to match ol_cnt
        Order order = Order.builder()
                .orderId(nextOrderId)
                .districtId(input.getDistrictId())
                .warehouseId(input.getWarehouseId())
                .customerId(input.getCustomerId())
                .entryDate(Times.currentTimeMillis())
                .orderLineCounter(input.getOrderLineCnt())
                .allLocal(input.getOrderAllLocal())
                .build();

        // For each O_OL_CNT item on the order: see clause 2.4.2.2 (dot 8)
        List<Stock> stocks = new ArrayList<>();
        List<OrderLine> orderLines = new ArrayList<>();
        for (int index = 1; index <= order.getOrderLineCounter(); index++) {
            OrderLineOutput.Builder orderLineOutput = OrderLineOutput.builder();
            int number = index;
            int supplyWarehouseId = input.getSupplierWarehouseIds()[number - 1];
            int itemId = input.getItemIds()[number - 1];
            int quantity = input.getOrderQuantities()[number - 1];

            orderLineOutput.supplierWarehouseId(supplyWarehouseId).itemId(itemId).orderQuantities(quantity);
            // If I_ID has an unused value (see Clause 2.4.1.5), a "not-found"
            // condition is signaled, resulting in a rollback of the database
            // transaction (see Clause 2.4.2.3).
            if (itemId == -12345) {
                // an expected condition generated 1% of the time in the test
                // data...
                // we throw an illegal access exception and the transaction gets
                // rolled back later on
                return TPCCCommand.newErrorMessage(aRequest, TRANSACTION_ABORTED);
            }

            // The row in the ITEM table with matching I_ID (equals OL_I_ID) is
            // selected and I_PRICE, the price of the item, I_NAME, the name of
            // the item, and I_DATA are retrieved.
            Item item = itemRepository.findBy(itemId);
            if (item == null) {
                return TPCCCommand.newErrorMessage(aRequest, TRANSACTION_ABORTED);
            }
            orderLineOutput.itemId(item.getItemId()).itemName(item.getName()).itemPrice(item.getPrice());

            // clause 2.4.2.2 (dot 8.2)
            Stock stock = stockRepository.findBy(itemId, supplyWarehouseId);
            if (stock == null) {
                return TPCCCommand.newErrorMessage(aRequest, TRANSACTION_ABORTED);
            }

            orderLineOutput.stockQuantities(stock.getQuantity());

            stock = Stock.from(stock)
                    .addQuantity(quantity)
                    .remoteCntIncrement(supplyWarehouseId != warehouseId)
                    .addYearToDate(quantity)
                    .orderCountIncrement()
                    .build();

            stocks.add(stock);

            // clause 2.4.2.2 (dot 8.3)
            BigDecimal amount = item.getPrice().multiply(BigDecimal.valueOf(quantity));
            Character brandGeneric;
            // clause 2.4.2.2 (dot 8.4)
            if (item.getData().indexOf("GENERIC") != -1 && stock.getData().indexOf("GENERIC") != -1) {
                brandGeneric = 'B';
            } else {
                brandGeneric = 'G';
            }

            orderLineOutput.orderLineAmounts(amount).brandGeneric(brandGeneric);

            orderBuilder.orderLines(orderLineOutput.build());

            // clause 2.4.2.2 (dot 9)
            orderBuilder.totalAmount(amount.multiply(BigDecimal.ONE.subtract(customer.getDiscount())
                    .multiply(BigDecimal.ONE.add(warehouse.getTax()).add(district.getTax()))));

            String districtInfo = getDistrictInfo(district, stock);

            OrderLine orderLine = OrderLine.builder()
                    .orderId(nextOrderId)
                    .districtId(districtId)
                    .warehouseId(warehouseId)
                    .number(number)
                    .itemId(itemId)
                    .supplyWarehouseId(supplyWarehouseId)
                    .quantity(quantity)
                    .amount(amount)
                    .districtInfo(districtInfo)
                    .build();

            orderLines.add(orderLine);
        }

        newOrderRepository.save(newOrder);
        districtRepository.save(District.from(district).nextOrderIdIncrement().build());
        orderRepository.save(order);
        stocks.forEach(stockRepository::save);
        orderLines.forEach(orderLineRepository::save);

        return TPCCCommand.newSuccessMessage(aRequest, outputScreen(orderBuilder.build()));
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

    private String outputScreen(NewOrderOutput newOrder) {
        // clause 2.4.3.3
        StringBuilder message = new StringBuilder();
        message.append("\n+--------------------------- NEW-ORDER ---------------------------+\n");
        message.append(" Date: ");
        message.append(newOrder.getDateTime().format(Times.DATE_TIME_FORMAT));
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
        message.append(newOrder.getOrderLines().size());
        message.append("\n\n Customer:  ");
        message.append(newOrder.getCustomerId());
        message.append("\n   Name:    ");
        message.append(newOrder.getCustomerLast());
        message.append("\n   Credit:  ");
        message.append(newOrder.getCustomerCredit());
        message.append("\n   %Disc:   ");
        message.append(newOrder.getDiscount());
        message.append("\n\n Order-Line List [Supp_W - Item_ID - Item Name - Qty - Stock - B/G - Price - Amount]\n");
        for (OrderLineOutput orderLine : newOrder.getOrderLines()) {
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
