package bftsmart.microbenchmark.tpcc.domain;

import java.util.Set;

import org.javatuples.Tuple;

public interface Persistable {

    Tuple getKey();

    Set<Tuple> getSecondaryKeys();

}