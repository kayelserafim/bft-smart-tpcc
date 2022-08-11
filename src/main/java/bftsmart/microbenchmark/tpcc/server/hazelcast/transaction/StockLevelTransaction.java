package bftsmart.microbenchmark.tpcc.server.hazelcast.transaction;

import java.util.Set;
import java.util.stream.Collectors;

import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.DistrictRepository;
import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.OrderLineRepository;
import bftsmart.microbenchmark.tpcc.server.hazelcast.repository.StockRepository;
import bftsmart.microbenchmark.tpcc.server.transaction.Transaction;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionResponse;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionType;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.request.StockLevelRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.response.StockLevelResponse;
import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.OrderLine;
import bftsmart.microbenchmark.tpcc.table.Stock.StockKey;

public class StockLevelTransaction implements Transaction {

    @Inject
    private DistrictRepository districtRepository;
    @Inject
    private OrderLineRepository orderLineRepository;
    @Inject
    private StockRepository stockRepository;

    @Override
    public TransactionType transactionType() {
        return TransactionType.STOCK_LEVEL;
    }

    @Override
    public TransactionResponse process(final TransactionRequest command) {
        StockLevelRequest input = (StockLevelRequest) command;
        StockLevelResponse output = new StockLevelResponse();

        Integer warehouseId = input.getWarehouseId();
        Integer districtId = input.getDistrictId();
        Integer threshold = input.getThreshold();

        District district = districtRepository.find(districtId, warehouseId);
        Set<StockKey> stockIds = orderLineRepository.find(district, warehouseId)
                .stream()
                .map(OrderLine::getItemId)
                .map(itemId -> new StockKey(warehouseId, itemId))
                .collect(Collectors.toSet());

        long stockCount = stockRepository.count(stockIds, threshold);

        output.withWarehouseId(warehouseId)
                .withDistrictId(districtId)
                .withThreshold(threshold)
                .withStockCount(stockCount);

        return new TransactionResponse().withStatus(0).withResponse(outputScreen(output));
    }

    private String outputScreen(StockLevelResponse stockLevel) {
        StringBuilder message = new StringBuilder();
        message.append("\n+-------------------------- STOCK-LEVEL --------------------------+");
        message.append("\n Warehouse: ");
        message.append(stockLevel.getWarehouseId());
        message.append("\n District:  ");
        message.append(stockLevel.getDistrictId());
        message.append("\n\n Stock Level Threshold: ");
        message.append(stockLevel.getThreshold());
        message.append("\n Low Stock Count:       ");
        message.append(stockLevel.getStockCount());
        message.append("\n+-----------------------------------------------------------------+\n\n");
        return message.toString();
    }

}
