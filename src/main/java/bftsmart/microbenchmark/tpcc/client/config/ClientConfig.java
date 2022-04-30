package bftsmart.microbenchmark.tpcc.client.config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class ClientConfig {

    @Inject
    @Named("bft.num-of-threads")
    private Integer numOfThreads;
    @Inject
    @Named("bft.parallel-smr")
    private Boolean parallelSmr;
    @Inject
    @Named("tpcc.terminal-id")
    private Integer terminalId;

    public Integer getNumOfThreads() {
        return numOfThreads;
    }

    public Boolean getParallelSmr() {
        return parallelSmr;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ClientConfig [numOfThreads=")
                .append(numOfThreads)
                .append(", parallelSmr=")
                .append(parallelSmr)
                .append(", terminalId=")
                .append(terminalId)
                .append(']');
        return builder.toString();
    }

}
