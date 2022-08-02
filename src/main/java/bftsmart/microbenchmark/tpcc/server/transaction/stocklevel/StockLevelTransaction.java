package bftsmart.microbenchmark.tpcc.server.transaction.stocklevel;

import java.util.Set;
import java.util.stream.Collectors;

import org.javatuples.Tuple;

import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TransactionType;
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
    public TPCCCommand process(final TPCCCommand command) {
        StockLevelInput input = (StockLevelInput) command.getRequest();
        StockLevelOutput.Builder stockBuilder = StockLevelOutput.builder();

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

        stockBuilder.warehouseId(warehouseId).districtId(districtId).threshold(threshold).stockCount(stockCount);

        return TPCCCommand.from(command).status(0).response(outputScreen(stockBuilder.build())).build();
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
