package RangeQueries;

import java.util.List;

/**
 * Range map interface
 *
 * @param <K> key
 * @param <V> value
 */
public interface RangeMap<K, V> {
    int size();

    boolean isEmpty();

    void add(K key, V value);

    boolean contains(K key);

    V lookup(K key);

    List<V> lookupRange(K from, K to);
}
