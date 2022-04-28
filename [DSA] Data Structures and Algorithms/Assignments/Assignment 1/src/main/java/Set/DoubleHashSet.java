package Set;

/**
 * Implementation of Set using double hashing
 *
 * @param <T> Type of stored values
 * @author Egor Kuziakov
 */
public class DoubleHashSet<T> implements ISet<T> {

    int capacity;
    int size = 0;
    private final T[] items;
    private final Object deletedObject;

    /**
     * Initialization of set
     *
     * @param capacity maximum size of set
     */
    @SuppressWarnings("unchecked")
    public DoubleHashSet(int capacity) {
        if (!isPrime(capacity)) {
            throw new IllegalArgumentException("Capacity is not prime number");
        }
        this.capacity = capacity;
        items = (T[]) new Object[capacity];

        deletedObject = new Object();
    }

    /**
     * Adds an element to the set
     *
     * @param item Element to be added the set
     */
    @Override
    public void add(T item) {
        int nullIndex = getNextNullOrDeletedObjectIndex(item);
        if (nullIndex == -1) {
            throw new IllegalStateException("Set is full");
        }

        if (items[nullIndex] == null || items[nullIndex].equals(deletedObject)) size++;
        items[nullIndex] = item;
    }

    /**
     * Removes an element from the set
     *
     * @param item Removed element
     */
    @Override
    @SuppressWarnings("unchecked")
    public void remove(T item) {
        int itemIndex = getElementIndex(item);
        if (itemIndex != -1) {
            items[itemIndex] = (T) deletedObject;
            size--;
        }
    }

    /**
     * Checks if element belongs to the set
     *
     * @param item Checked element
     * @return true if belongs, false if not belongs
     */
    @Override
    public boolean contains(T item) {
        return getElementIndex(item) != -1;
    }

    /**
     * Returns number of elements in the set
     *
     * @return number of elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if set is empty
     *
     * @return true if empty, false if not empty
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns all elements of the set
     *
     * @return array of elements
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] result = (T[]) new Object[size()];
        int resultIndex = 0;

        for (T item : items) {
            if ((item != null) && (!item.equals(deletedObject))) {
                result[resultIndex] = item;
                resultIndex++;
            }
        }

        return result;
    }

    /**
     * Removes all elements in the set
     */
    public void removeAllItems() {
        for (int i = 0; i < capacity; i++) {
            items[i] = null;
        }
        size = 0;
    }

    /**
     * Adds elements to the set
     *
     * @param items elements to be added to the set
     */
    public void addItems(T[] items) {
        for (T item : items) {
            add(item);
        }
    }

    private static boolean isPrime(int n) {
        int divisorsCount = 0;
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                divisorsCount++;
            }
            if (divisorsCount > 2) {
                return false;
            }
        }
        return divisorsCount == 2;
    }

    private int normalizedHashCode(T item) {
        return Math.abs(item.hashCode()) % capacity;
    }

    private int hashCode2(T item) {
        return 1 + (Math.abs(item.hashCode()) % (capacity - 2));
    }

    private int normalizedIndex(int index) {
        return index % capacity;
    }

    private int getElementIndex(T item) {
        int startIndex = normalizedHashCode(item);
        int step = hashCode2(item);
        int currentIndex = startIndex;

        do {
            if (items[currentIndex] != null && items[currentIndex].equals(item)) {
                return currentIndex;
            }
            currentIndex = normalizedIndex(currentIndex + step);
        } while ((currentIndex != startIndex) && (items[currentIndex] != null));

        return -1;
    }

    private int getNextNullOrDeletedObjectIndex(T item) {
        int startIndex = normalizedHashCode(item);
        int step = hashCode2(item);
        int currentIndex = startIndex;

        do {
            if ((items[currentIndex] == null) || (items[currentIndex].equals(deletedObject)) || (items[currentIndex].equals(item))) {
                return currentIndex;
            }
            currentIndex = normalizedIndex(currentIndex + step);
        } while (currentIndex != startIndex);

        return -1;
    }
}