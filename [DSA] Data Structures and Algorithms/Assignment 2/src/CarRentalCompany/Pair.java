package CarRentalCompany;

import java.util.Objects;

/**
 * Class with two variables
 *
 * @param <K> key type
 * @param <V> value type
 * @author Egor Kuziakov
 */
public class Pair<K, V> {
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return pair key
     */
    public K getKey() {
        return key;
    }

    /**
     * @return pair value
     */
    public V getValue() {
        return value;
    }

    /**
     * @param o object
     * @return true if object is equal to pair, false if not
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<K, V> pair = (Pair<K, V>) o;
        return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);
    }

    /**
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
