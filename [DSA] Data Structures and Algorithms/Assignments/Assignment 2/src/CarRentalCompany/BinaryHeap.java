package CarRentalCompany;

/**
 * Priority queue implementation using Binary heap
 *
 * @param <K>
 * @param <V>
 * @author Egor Kuziakov
 */
public class BinaryHeap<K extends Comparable<K>, V> implements IPriorityQueue<K, V> {
    private final HashArrayList<Pair<K, V>> pairs;
    private int size = 0;

    public BinaryHeap() {
        this.pairs = new HashArrayList<>();
    }

    /**
     * @param item item to insert in heap
     */
    @Override
    public void insert(Pair<K, V> item) {
        pairs.add(item);
        size++;
        decreaseKey(size - 1, item.getKey());
    }

    /**
     * get min item
     *
     * @return min item
     */
    @Override
    public Pair<K, V> findMin() {
        if (size == 0) return null;
        return pairs.get(0);
    }

    /**
     * get min item and delete it from heap
     *
     * @return min item
     */
    @Override
    public Pair<K, V> extractMin() {
        if (size == 0) return null;
        Pair<K, V> minPair = findMin();

        Pair<K, V> lastPair = pairs.get(size - 1);
        pairs.remove(size - 1);
        if (!pairs.isEmpty()) {
            pairs.set(0, lastPair);
        }
        size--;
        repairHeap(0);
        return minPair;
    }

    /**
     * decrease item key to new key by item
     *
     * @param item   item
     * @param newKey new key
     */
    @Override
    public void decreaseKey(Pair<K, V> item, K newKey) {
        decreaseKey(getIndexByPair(item), newKey);
    }

    /**
     * decrease item key to new key by index
     *
     * @param itemIndex item
     * @param newKey    new key
     */
    private void decreaseKey(int itemIndex, K newKey) {
        Pair<K, V> pair = pairs.get(itemIndex);
        pairs.set(itemIndex, new Pair<>(newKey, pair.getValue()));
        while (itemIndex > 0 && pairs.get((itemIndex - 1) / 2).getKey().compareTo(pairs.get(itemIndex).getKey()) > 0) {
            pairs.swap(itemIndex, (itemIndex - 1) / 2);
            itemIndex = (itemIndex - 1) / 2;
        }
    }

    /**
     * @param item item to remove from heap
     */
    @Override
    public void delete(Pair<K, V> item) {
        int itemIndex = getIndexByPair(item);
        if (itemIndex == -1) return;

        pairs.set(itemIndex, pairs.get(size - 1));
        pairs.remove(size - 1);
        size--;

        repairHeap(itemIndex);
    }

    /**
     * @param anotherQueue queue to be merged with current queue
     */
    @Override
    public void union(IPriorityQueue<K, V> anotherQueue) {
        Pair<K, V> queueMin;
        while (anotherQueue.findMin() != null) {
            queueMin = anotherQueue.extractMin();
            insert(queueMin);
        }
    }

    /**
     * @return true if heap is empty, false if not
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @param index index which is root for subtree to be repaired
     */
    private void repairHeap(int index) {
        int left_child = 2 * index + 1;
        int right_child = 2 * index + 2;
        int newIndex = index;
        if (left_child < size && pairs.get(left_child).getKey().compareTo(pairs.get(newIndex).getKey()) < 0) {
            newIndex = left_child;
        }
        if (right_child < size && pairs.get(right_child).getKey().compareTo(pairs.get(newIndex).getKey()) < 0) {
            newIndex = right_child;
        }
        if (newIndex != index) {
            pairs.swap(index, newIndex);
            repairHeap(newIndex);
        }
    }

    /**
     * @param item item
     * @return index on which the item is located
     */
    private int getIndexByPair(Pair<K, V> item) {
        return pairs.indexOf(item);
    }
}
