package bftsmart.microbenchmark.tpcc.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.Futures;
import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalFactory;
import bftsmart.microbenchmark.tpcc.config.TPCCConfig;
import bftsmart.microbenchmark.tpcc.config.WorkloadConfig;
import bftsmart.microbenchmark.tpcc.io.TPCCData;
import bftsmart.microbenchmark.tpcc.monitor.MetricCollector;
import bftsmart.microbenchmark.tpcc.monitor.RawResult;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;

public class TPCCClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TPCCClient.class);

    private final WorkloadConfig workload;
    private final TPCCRandom random;
    private final ExecutorService executorService;
    private final TimedSemaphore sem;

    @Inject
    private TPCCTerminalFactory terminalFactory;
    @Inject
    private MetricCollector metricCollector;

    @Inject
    TPCCClient(TPCCData tpccData, WorkloadConfig workload) {
        this.workload = workload;
        this.random = new TPCCRandom(tpccData.getcLoad());
        this.executorService = Executors.newFixedThreadPool(workload.getTerminals());
        this.sem = new TimedSemaphore(1, TimeUnit.MINUTES, workload.getLimitTxnsPerMin());

        LOGGER.info("TPCCClient Initiated.");
    }

    public void start() {
        List<Future<List<RawResult>>> tasks = new ArrayList<>();
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < workload.getTerminals(); i++) {
            TPCCTerminalData terminalData = TPCCTerminalData.builder()
                    .terminalId(i)
                    .warehouseId(random.nextInt(1, workload.getWarehouses()))
                    .districtId(random.nextInt(1, TPCCConfig.DIST_PER_WHSE))
                    .workload(workload)
                    .build();

            LOGGER.info("Client {} starting terminal for warehouse ", terminalData.getTerminalName());
            tasks.add(executorService.submit(terminalFactory.create(terminalData, random, sem)));
        }
        executorService.shutdown();

        List<RawResult> results = tasks.stream()
                .map(Futures::getUnchecked)
                .flatMap(List<RawResult>::stream)
                .sorted(Comparator.comparing(RawResult::getTransactionType))
                .collect(Collectors.toList());

        LOGGER.info("{} has finished", Thread.currentThread().getName());
        LOGGER.info("{}ms elapsed time", stopwatch.stop().elapsed().toMillis());

        metricCollector.writeAllResults(results).join();

        System.exit(0);
    }

}
