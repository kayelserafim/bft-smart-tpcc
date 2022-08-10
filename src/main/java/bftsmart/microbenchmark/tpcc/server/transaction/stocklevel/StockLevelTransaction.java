package bftsmart.microbenchmark.tpcc.server.transaction.stocklevel;

import java.util.Set;
import java.util.stream.Collectors;

import org.javatuples.Tuple;

import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.domain.CommandRequest;
import bftsmart.microbenchmark.tpcc.domain.CommandResponse;
import bftsmart.microbenchmark.tpcc.domain.TransactionType;
import bftsmart.microbenchmark.tpcc.server.repository.DistrictRepository;
import bftsmart.microbenchmark.tpcc.server.repository.OrderLineRepository;
import bftsmart.microbenchmark.tpcc.server.repository.StockRepository;
import bftsmart.microbenchmark.tpcc.server.transaction.Transaction;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.input.StockLevelInput;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.output.StockLevelOutput;
import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.OrderLine;
import bftsmart.microbenchmark.tpcc.table.Stock;

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
    public CommandResponse process(final CommandRequest command) {
        StockLevelInput input = (StockLevelInput) command;
        StockLevelOutput output = new StockLevelOutput();

        Integer warehouseId = input.getWarehouseId();
        Integer districtId = input.getDistrictId();
        Integer threshold = input.getThreshold();

        District district = districtRepository.find(districtId, warehouseId);
        Set<Tuple> stockIds = orderLineRepository.find(district, warehouseId)
                .stream()
                .map(OrderLine::getItemId)
                .map(itemId -> Stock.key(warehouseId, itemId))
                .collect(Collectors.toSet());

        long stockCount = stockRepository.count(stockIds, threshold);

        output.withWarehouseId(warehouseId)
                .withDistrictId(districtId)
                .withThreshold(threshold)
                .withStockCount(stockCount);

        return new CommandResponse().withStatus(0).withResponse(outputScreen(output));
    }

    private String outputScreen(StockLevelOutput stockLevel) {
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
