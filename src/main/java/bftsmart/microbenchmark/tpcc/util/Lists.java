package bftsmart.microbenchmark.tpcc.util;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

/**
 * Convenience class that provides a clearer API for obtaining list elements.
 * 
 * @author Kayel Serafim <kayel.serafim@softdesign.com.br>
 *
 */
public final class Lists {

    private Lists() {
        super();
    }

    /**
     * Returns the first item in the given list, or null if not found.
     *
     * @param <T>
     *            The generic list type.
     * @param list
     *            The list that may have a first item.
     *
     * @return null if the list is null or there is no first item.
     */
    public static <T> T getFirst(final Collection<T> list) {
        return getFirst(list, null);
    }

    /**
     * Returns the first item in the given list, or t if not found.
     *
     * @param <T>
     *            The generic list type.
     * @param list
     *            The list that may have a first item.
     * @param t
     *            The default return value.
     *
     * @return null if the list is null or there is no first item.
     */
    public static <T> T getFirst(final Collection<T> list, final T t) {
        return CollectionUtils.isEmpty(list) ? t : list.iterator().next();
    }

}