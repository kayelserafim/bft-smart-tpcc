package bftsmart.microbenchmark.tpcc.config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class ParamConfig {

    @Inject
    @Named("bft.id")
    private Integer id;
    @Inject
    @Named("bft.num-of-threads")
    private Integer numOfThreads;
    @Inject
    @Named("bft.parallel-smr")
    private Boolean parallelSmr;

    public Integer getId() {
        return id;
    }

    public Integer getNumOfThreads() {
        return numOfThreads;
    }

    public Boolean getParallelSmr() {
        return parallelSmr;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ParamConfig [id=")
                .append(id)
                .append(", numOfThreads=")
                .append(numOfThreads)
                .append(", parallelSmr=")
                .append(parallelSmr)
                .append(']');
        return builder.toString();
    }

}
