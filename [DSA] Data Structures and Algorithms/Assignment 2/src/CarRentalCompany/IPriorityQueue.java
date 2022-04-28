package CarRentalCompany;

public interface IPriorityQueue<K extends Comparable<K>, V> {
    void insert(Pair<K, V> item);

    Pair<K, V> findMin();

    Pair<K, V> extractMin();

    void decreaseKey(Pair<K, V> item, K newKey);

    void delete(Pair<K, V> item);

    void union(IPriorityQueue<K, V> anotherQueue);

    boolean isEmpty();
}
