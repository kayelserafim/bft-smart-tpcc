package bftsmart.microbenchmark.tpcc.server.conflict;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import bftsmart.microbenchmark.tpcc.probject.TransactionType;

/**
 * Contains static utility methods belonging to transactions and their conflicting types.
 *
 */
public class TransactionConflicts {

    private static final Map<TransactionType, Collection<TransactionType>> CONFLICTING_TRANSACTIONS;
    private static final Map<TransactionType, Collection<TransactionType>> NOT_CONFLICTING_TRANSACTIONS;
    private static final Set<TransactionType> PESSIMISTIC_MODEL =
            Sets.newHashSet(TransactionType.STOCK_LEVEL, TransactionType.DELIVERY);

    private TransactionConflicts() {
        super();
    }

    static {
        Multimap<TransactionType, TransactionType> conflicting = ArrayListMultimap.create();
        Multimap<TransactionType, TransactionType> notConflicting = ArrayListMultimap.create();

        conflicting.put(TransactionType.ORDER_STATUS, TransactionType.NEW_ORDER);
        conflicting.put(TransactionType.ORDER_STATUS, TransactionType.PAYMENT);
        conflicting.put(TransactionType.ORDER_STATUS, TransactionType.DELIVERY);
        notConflicting.put(TransactionType.ORDER_STATUS, TransactionType.ORDER_STATUS);
        notConflicting.put(TransactionType.ORDER_STATUS, TransactionType.STOCK_LEVEL);

        conflicting.put(TransactionType.NEW_ORDER, TransactionType.NEW_ORDER);
        conflicting.put(TransactionType.NEW_ORDER, TransactionType.ORDER_STATUS);
        conflicting.put(TransactionType.NEW_ORDER, TransactionType.STOCK_LEVEL);
        notConflicting.put(TransactionType.NEW_ORDER, TransactionType.PAYMENT);
        notConflicting.put(TransactionType.NEW_ORDER, TransactionType.DELIVERY);

        conflicting.put(TransactionType.STOCK_LEVEL, TransactionType.NEW_ORDER);
        notConflicting.put(TransactionType.STOCK_LEVEL, TransactionType.STOCK_LEVEL);
        notConflicting.put(TransactionType.STOCK_LEVEL, TransactionType.ORDER_STATUS);
        notConflicting.put(TransactionType.STOCK_LEVEL, TransactionType.PAYMENT);
        notConflicting.put(TransactionType.STOCK_LEVEL, TransactionType.DELIVERY);

        conflicting.put(TransactionType.PAYMENT, TransactionType.PAYMENT);
        conflicting.put(TransactionType.PAYMENT, TransactionType.DELIVERY);
        conflicting.put(TransactionType.PAYMENT, TransactionType.ORDER_STATUS);
        notConflicting.put(TransactionType.PAYMENT, TransactionType.NEW_ORDER);
        notConflicting.put(TransactionType.PAYMENT, TransactionType.STOCK_LEVEL);

        conflicting.put(TransactionType.DELIVERY, TransactionType.DELIVERY);
        conflicting.put(TransactionType.DELIVERY, TransactionType.ORDER_STATUS);
        conflicting.put(TransactionType.DELIVERY, TransactionType.PAYMENT);
        notConflicting.put(TransactionType.DELIVERY, TransactionType.NEW_ORDER);
        notConflicting.put(TransactionType.DELIVERY, TransactionType.STOCK_LEVEL);

        CONFLICTING_TRANSACTIONS = Collections.unmodifiableMap(conflicting.asMap());
        NOT_CONFLICTING_TRANSACTIONS = Collections.unmodifiableMap(notConflicting.asMap());
    }

    public static boolean hasConflict(TransactionType t1, TransactionType t2) {
        return CONFLICTING_TRANSACTIONS.get(t1).contains(t2);
    }

    public static boolean hasNotConflict(TransactionType t1, TransactionType t2) {
        return NOT_CONFLICTING_TRANSACTIONS.get(t1).contains(t2);
    }

    /**
     * Transactions with the pessimistic locking model to avoid concurrent updates to the records.
     * 
     * @param t1
     *            The first transaction type
     * @param t2
     *            The second transaction type
     * @return true if it is, false otherwise
     */
    public static boolean isPessimistic(TransactionType t1, TransactionType t2) {
        return PESSIMISTIC_MODEL.contains(t1) || PESSIMISTIC_MODEL.contains(t2);
    }

}
