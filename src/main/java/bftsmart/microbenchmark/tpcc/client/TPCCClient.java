package bftsmart.microbenchmark.tpcc.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.Futures;
import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.client.monitor.MetricCollector;
import bftsmart.microbenchmark.tpcc.client.monitor.RawResult;
import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalFactory;
import bftsmart.microbenchmark.tpcc.config.BFTParams;
import bftsmart.microbenchmark.tpcc.config.TPCCConstants;
import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.io.Workload;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class TPCCClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TPCCClient.class);

    private static final int MAX_TERMINALS_PER_CLIENT = 1000;

    private final TPCCRandom random;
    private final ExecutorService executorService;

    private final TPCCTerminalFactory terminalFactory;
    private final MetricCollector metricCollector;
    private final TPCCConfig tpccConfig;
    private final BFTParams bftParams;

    @Inject
    TPCCClient(TPCCTerminalFactory terminalFactory, MetricCollector metricCollector, Workload workload,
            TPCCConfig tpccConfig, BFTParams bftParams) {
        this.terminalFactory = terminalFactory;
        this.metricCollector = metricCollector;
        this.tpccConfig = tpccConfig;
        this.bftParams = bftParams;
        this.random = new TPCCRandom(workload.getcLoad());
        this.executorService = Executors.newFixedThreadPool(bftParams.getNumOfThreads());

        LOGGER.info("TPCCClient Initiated.");
    }

    public void start() {
        List<Future<List<RawResult>>> tasks = new ArrayList<>();
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < bftParams.getNumOfThreads(); i++) {
            TPCCTerminalData terminalData = TPCCTerminalData.builder()
                    .terminalId(bftParams.getId() * MAX_TERMINALS_PER_CLIENT + i)
                    .warehouseId(random.nextInt(1, tpccConfig.getWarehouses()))
                    .districtId(random.nextInt(1, TPCCConstants.DIST_PER_WHSE))
                    .parallelExecution(bftParams.getParallel())
                    .workload(tpccConfig)
                    .build();

            LOGGER.info("Client {} starting terminal for warehouse ", terminalData.getTerminalName());
            tasks.add(executorService.submit(terminalFactory.create(terminalData, random)));
        }
        executorService.shutdown();

        List<RawResult> results = tasks.stream()
                .map(Futures::getUnchecked)
                .flatMap(List<RawResult>::stream)
                .sorted(Comparator.comparing(RawResult::getTransactionType))
                .collect(Collectors.toList());

        LOGGER.info("{} has finished", Thread.currentThread().getName());
        LOGGER.info("{}ms elapsed time", stopwatch.stop().elapsed().toMillis());

        metricCollector.writeResults(results);

        System.exit(0);
    }

}
