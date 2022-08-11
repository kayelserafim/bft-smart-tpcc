package bftsmart.microbenchmark.tpcc.client.service;

import static parallelism.ParallelMapping.SYNC_ALL;

import java.time.Duration;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import bftsmart.microbenchmark.tpcc.client.command.CommandFactory;
import bftsmart.microbenchmark.tpcc.client.monitor.RawResult;
import bftsmart.microbenchmark.tpcc.client.terminal.TPCCTerminalData;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionRequest;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionResponse;
import bftsmart.microbenchmark.tpcc.server.transaction.TransactionType;
import bftsmart.microbenchmark.tpcc.util.TPCCRandom;
import bftsmart.tom.ParallelServiceProxy;
import bftsmart.util.MultiOperationRequest;
import bftsmart.util.MultiOperationResponse;

@Singleton
public class TPCCService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TPCCService.class);

    @Inject
    private BFTServiceProxy bftServiceProxy;
    @Inject
    private CommandFactory commandFactory;

    public RawResult process(TPCCTerminalData data, TPCCRandom random) {
        final TransactionType transactionType = data.getTransactionType(random.nextInt(1, 100));
        final TransactionRequest commandRequest = commandFactory.getFactory(transactionType).createCommand(data, random);

        final Pair<Duration, TransactionResponse> pair = process(commandRequest, data.getTerminalId(), data.getParallel());
        final Duration elapsedTime = pair.getValue0();
        final TransactionResponse commandResponse = pair.getValue1();

        return new RawResult().terminalId(data.getTerminalId())
                .terminalName(data.getTerminalName())
                .commandId(commandRequest.getCommandId())
                .transactionType(TransactionType.fromValue(commandRequest.getTransactionType()))
                .conflict(commandResponse.isConflict())
                .status(commandResponse.getStatus())
                .elapsed(elapsedTime)
                .message("Response received: " + commandResponse.getResponse());

    }

    private Pair<Duration, TransactionResponse> process(TransactionRequest commandRequest, int terminalId, boolean parallel) {
        final ParallelServiceProxy serviceProxy = bftServiceProxy.getInstance(terminalId);
        try {
            final byte[] req = commandRequest.serialize(new MultiOperationRequest(1));
            final byte[] resp;
            final Stopwatch stopwatch = Stopwatch.createStarted();
            if (parallel) {
                resp = serviceProxy.invokeParallel(req, SYNC_ALL);
            } else {
                resp = serviceProxy.invokeOrdered(req);
            }
            stopwatch.stop();
            return Pair.with(stopwatch.elapsed(), TransactionResponse.deserialize(new MultiOperationResponse(resp)));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Pair.with(Duration.ZERO, new TransactionResponse().withStatus(-1).withResponse(e.getMessage()));
        }
    }

}
