package bftsmart.microbenchmark.tpcc.server.conflict;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import bftsmart.microbenchmark.tpcc.domain.TransactionType;

/**
 * Contains static utility methods belonging to transactions and their conflicting types.
 */
public class TransactionConflicts {

    private static final Map<Integer, Collection<Integer>> CONFLICTING;
    private static final Map<Integer, Collection<Integer>> POSSIBLE_CONFLICTING;
    private static final Map<Integer, Collection<Integer>> NOT_CONFLICTING;

    private TransactionConflicts() {
        super();
    }

    static {
        Multimap<Integer, Integer> conflicting = ArrayListMultimap.create();
        Multimap<Integer, Integer> possibleConflicting = ArrayListMultimap.create();
        Multimap<Integer, Integer> notConflicting = ArrayListMultimap.create();

        notConflicting.put(TransactionType.ORDER_STATUS.getClassId(), TransactionType.ORDER_STATUS.getClassId());
        notConflicting.put(TransactionType.ORDER_STATUS.getClassId(), TransactionType.STOCK_LEVEL.getClassId());
        possibleConflicting.put(TransactionType.ORDER_STATUS.getClassId(), TransactionType.NEW_ORDER.getClassId());
        possibleConflicting.put(TransactionType.ORDER_STATUS.getClassId(), TransactionType.PAYMENT.getClassId());
        conflicting.put(TransactionType.ORDER_STATUS.getClassId(), TransactionType.DELIVERY.getClassId());

        conflicting.put(TransactionType.NEW_ORDER.getClassId(), TransactionType.NEW_ORDER.getClassId());
        possibleConflicting.put(TransactionType.NEW_ORDER.getClassId(), TransactionType.ORDER_STATUS.getClassId());
        conflicting.put(TransactionType.NEW_ORDER.getClassId(), TransactionType.STOCK_LEVEL.getClassId());
        conflicting.put(TransactionType.NEW_ORDER.getClassId(), TransactionType.PAYMENT.getClassId());
        conflicting.put(TransactionType.NEW_ORDER.getClassId(), TransactionType.DELIVERY.getClassId());

        notConflicting.put(TransactionType.STOCK_LEVEL.getClassId(), TransactionType.STOCK_LEVEL.getClassId());
        notConflicting.put(TransactionType.STOCK_LEVEL.getClassId(), TransactionType.ORDER_STATUS.getClassId());
        conflicting.put(TransactionType.STOCK_LEVEL.getClassId(), TransactionType.NEW_ORDER.getClassId());
        notConflicting.put(TransactionType.STOCK_LEVEL.getClassId(), TransactionType.PAYMENT.getClassId());
        notConflicting.put(TransactionType.STOCK_LEVEL.getClassId(), TransactionType.DELIVERY.getClassId());

        possibleConflicting.put(TransactionType.PAYMENT.getClassId(), TransactionType.PAYMENT.getClassId());
        conflicting.put(TransactionType.PAYMENT.getClassId(), TransactionType.DELIVERY.getClassId());
        conflicting.put(TransactionType.PAYMENT.getClassId(), TransactionType.NEW_ORDER.getClassId());
        notConflicting.put(TransactionType.PAYMENT.getClassId(), TransactionType.STOCK_LEVEL.getClassId());
        possibleConflicting.put(TransactionType.PAYMENT.getClassId(), TransactionType.ORDER_STATUS.getClassId());

        conflicting.put(TransactionType.DELIVERY.getClassId(), TransactionType.DELIVERY.getClassId());
        conflicting.put(TransactionType.DELIVERY.getClassId(), TransactionType.ORDER_STATUS.getClassId());
        conflicting.put(TransactionType.DELIVERY.getClassId(), TransactionType.NEW_ORDER.getClassId());
        notConflicting.put(TransactionType.DELIVERY.getClassId(), TransactionType.STOCK_LEVEL.getClassId());
        conflicting.put(TransactionType.DELIVERY.getClassId(), TransactionType.PAYMENT.getClassId());

        CONFLICTING = Collections.unmodifiableMap(conflicting.asMap());
        POSSIBLE_CONFLICTING = Collections.unmodifiableMap(possibleConflicting.asMap());
        NOT_CONFLICTING = Collections.unmodifiableMap(notConflicting.asMap());
    }

    public static boolean hasConflict(final int t1, final int t2) {
        Collection<Integer> conflictDefinition = CONFLICTING.get(t1);
        return conflictDefinition != null && conflictDefinition.contains(t2);
    }

    public static boolean hasNotConflict(int t1, int t2) {
        Collection<Integer> conflictDefinition = NOT_CONFLICTING.get(t1);
        return conflictDefinition != null && conflictDefinition.contains(t2);
    }

    public static boolean hasPossibleConflict(int t1, int t2) {
        Collection<Integer> conflictDefinition = POSSIBLE_CONFLICTING.get(t1);
        return conflictDefinition != null && conflictDefinition.contains(t2);
    }

}
