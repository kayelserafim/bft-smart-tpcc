package bftsmart.microbenchmark.tpcc.probject;

import java.io.Serializable;
import java.util.Set;

import org.javatuples.Tuple;

public interface PRObject extends Serializable {

    Tuple getKey();

    Set<Tuple> getSecondaryKeys();

}