package bftsmart.microbenchmark.tpcc.config;

import org.apache.commons.lang3.BooleanUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class BFTParams {

    @Inject
    @Named("bft.id")
    private Integer id;
    @Inject
    @Named("bft.num-of-threads")
    private Integer numOfThreads;
    @Inject
    @Named("bft.parallel-smr")
    private Boolean parallel;

    public Integer getId() {
        return id;
    }

    public Integer getNumOfThreads() {
        return numOfThreads;
    }

    public Boolean getParallel() {
        return parallel;
    }

    public String getParallelDescription() {
        return BooleanUtils.isTrue(parallel) ? "Parallel" : "Sequential";
    }

    @Override
    public java.lang.String toString() {
        return new java.util.StringJoiner(", ", BFTParams.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("numOfThreads=" + numOfThreads)
                .add("parallel=" + parallel)
                .toString();
    }
}
