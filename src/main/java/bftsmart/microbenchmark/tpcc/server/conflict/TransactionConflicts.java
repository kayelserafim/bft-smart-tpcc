package bftsmart.microbenchmark.tpcc.server.conflict;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import bftsmart.microbenchmark.tpcc.domain.TransactionType;

/**
 * Contains static utility methods belonging to transactions and their conflicting types.
 */
public class TransactionConflicts {

    private static final Map<Integer, Collection<Integer>> CONFLICTING_TRANSACTIONS;
    private static final Map<Integer, Collection<Integer>> NOT_CONFLICTING_TRANSACTIONS;
    private static final Set<Integer> PESSIMISTIC_MODEL =
            Sets.newHashSet(TransactionType.STOCK_LEVEL.getClassId(), TransactionType.DELIVERY.getClassId());

    private TransactionConflicts() {
        super();
    }

    static {
        Multimap<Integer, Integer> conflicting = ArrayListMultimap.create();
        Multimap<Integer, Integer> notConflicting = ArrayListMultimap.create();

        conflicting.put(TransactionType.ORDER_STATUS.getClassId(), TransactionType.NEW_ORDER.getClassId());
        conflicting.put(TransactionType.ORDER_STATUS.getClassId(), TransactionType.PAYMENT.getClassId());
        conflicting.put(TransactionType.ORDER_STATUS.getClassId(), TransactionType.DELIVERY.getClassId());
        notConflicting.put(TransactionType.ORDER_STATUS.getClassId(), TransactionType.ORDER_STATUS.getClassId());
        notConflicting.put(TransactionType.ORDER_STATUS.getClassId(), TransactionType.STOCK_LEVEL.getClassId());

        conflicting.put(TransactionType.NEW_ORDER.getClassId(), TransactionType.NEW_ORDER.getClassId());
        conflicting.put(TransactionType.NEW_ORDER.getClassId(), TransactionType.ORDER_STATUS.getClassId());
        conflicting.put(TransactionType.NEW_ORDER.getClassId(), TransactionType.STOCK_LEVEL.getClassId());
        notConflicting.put(TransactionType.NEW_ORDER.getClassId(), TransactionType.PAYMENT.getClassId());
        notConflicting.put(TransactionType.NEW_ORDER.getClassId(), TransactionType.DELIVERY.getClassId());

        conflicting.put(TransactionType.STOCK_LEVEL.getClassId(), TransactionType.NEW_ORDER.getClassId());
        notConflicting.put(TransactionType.STOCK_LEVEL.getClassId(), TransactionType.STOCK_LEVEL.getClassId());
        notConflicting.put(TransactionType.STOCK_LEVEL.getClassId(), TransactionType.ORDER_STATUS.getClassId());
        notConflicting.put(TransactionType.STOCK_LEVEL.getClassId(), TransactionType.PAYMENT.getClassId());
        notConflicting.put(TransactionType.STOCK_LEVEL.getClassId(), TransactionType.DELIVERY.getClassId());

        conflicting.put(TransactionType.PAYMENT.getClassId(), TransactionType.PAYMENT.getClassId());
        conflicting.put(TransactionType.PAYMENT.getClassId(), TransactionType.DELIVERY.getClassId());
        conflicting.put(TransactionType.PAYMENT.getClassId(), TransactionType.ORDER_STATUS.getClassId());
        notConflicting.put(TransactionType.PAYMENT.getClassId(), TransactionType.NEW_ORDER.getClassId());
        notConflicting.put(TransactionType.PAYMENT.getClassId(), TransactionType.STOCK_LEVEL.getClassId());

        conflicting.put(TransactionType.DELIVERY.getClassId(), TransactionType.DELIVERY.getClassId());
        conflicting.put(TransactionType.DELIVERY.getClassId(), TransactionType.ORDER_STATUS.getClassId());
        conflicting.put(TransactionType.DELIVERY.getClassId(), TransactionType.PAYMENT.getClassId());
        notConflicting.put(TransactionType.DELIVERY.getClassId(), TransactionType.NEW_ORDER.getClassId());
        notConflicting.put(TransactionType.DELIVERY.getClassId(), TransactionType.STOCK_LEVEL.getClassId());

        CONFLICTING_TRANSACTIONS = Collections.unmodifiableMap(conflicting.asMap());
        NOT_CONFLICTING_TRANSACTIONS = Collections.unmodifiableMap(notConflicting.asMap());
    }

    public static boolean hasConflict(final int t1, final int t2) {
        return CONFLICTING_TRANSACTIONS.get(t1).contains(t2);
    }

    public static boolean hasNotConflict(int t1, int t2) {
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
    public static boolean isPessimistic(int t1, int t2) {
        return PESSIMISTIC_MODEL.contains(t1) || PESSIMISTIC_MODEL.contains(t2);
    }

}
