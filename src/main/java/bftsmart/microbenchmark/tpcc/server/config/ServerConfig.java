package bftsmart.microbenchmark.tpcc.server.config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class ServerConfig {

    @Inject
    @Named("bft.num-of-threads")
    private Integer numOfThreads;
    @Inject
    @Named("bft.parallel-smr")
    private Boolean parallelSmr;
    @Inject
    @Named("bft.replica-id")
    private Integer replicaId;

    public Integer getNumOfThreads() {
        return numOfThreads;
    }

    public Boolean getParallelSmr() {
        return parallelSmr;
    }

    public Integer getReplicaId() {
        return replicaId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ServerConfig [numOfThreads=")
                .append(numOfThreads)
                .append(", parallelSmr=")
                .append(parallelSmr)
                .append(", replicaId=")
                .append(replicaId)
                .append(']');
        return builder.toString();
    }

}
