package bftsmart.microbenchmark.tpcc.server.transaction.stocklevel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;
import bftsmart.microbenchmark.tpcc.repository.DistrictRepository;
import bftsmart.microbenchmark.tpcc.repository.OrderLineRepository;
import bftsmart.microbenchmark.tpcc.repository.StockRepository;
import bftsmart.microbenchmark.tpcc.server.transaction.Transaction;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.input.StockLevelInput;
import bftsmart.microbenchmark.tpcc.server.transaction.stocklevel.output.StockLevelOutput;
import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.OrderLine;

public class StockLevelTransaction implements Transaction {

    @Inject
    private ObjectMapper objectMapper;
    @Inject
    private DistrictRepository districtRepository;
    @Inject
    private OrderLineRepository orderLineRepository;
    @Inject
    private StockRepository stockRepository;

    @Override
    public TPCCCommandType commandType() {
        return TPCCCommandType.STOCK_LEVEL;
    }

    @Override
    public TPCCCommand process(final TPCCCommand aRequest) {
        Map<String, Serializable> params = aRequest.getRequest();

        StockLevelOutput.Builder stockBuilder = StockLevelOutput.builder();
        StockLevelInput input = objectMapper.convertValue(params, StockLevelInput.class);

        Integer warehouseId = input.getWarehouseId();
        Integer districtId = input.getDistrictId();
        Integer threshold = input.getThreshold();

        District district = districtRepository.find(districtId, warehouseId);
        List<Integer> orderLines = orderLineRepository.find(district, warehouseId)
                .parallelStream()
                .map(OrderLine::getItemId)
                .collect(Collectors.toList());

        long stockCount = stockRepository.count(orderLines, warehouseId, threshold);

        stockBuilder.warehouseId(warehouseId).districtId(districtId).threshold(threshold).stockCount(stockCount);

        return TPCCCommand.newSuccessMessage(aRequest, outputScreen(stockBuilder.build()));
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
