package Queue;

/**
 * Implementation of Circular Bounded Queue using primitive arrays
 *
 * @param <T> Type of stored values
 * @author Egor Kuziakov
 */
public class ArrayCircularBoundedQueue<T> implements ICircularBoundedQueue<T> {
    private final int capacity;
    private int front = -1;
    private int rear = -1;
    private int size = 0;
    private final T[] items;

    /**
     * Initializes queue
     *
     * @param capacity Max size of queue
     */
    @SuppressWarnings("unchecked")
    public ArrayCircularBoundedQueue(int capacity) {
        this.capacity = capacity;
        items = (T[]) new Object[capacity];
    }


    /**
     * Inserts an element to the rear of queue or overwrites the oldest if queue is full
     *
     * @param value The element to be added to the queue
     */
    @Override
    public void offer(T value) {
        if (isEmpty()) {
            front = 0;
        } else if (isFull()) {
            front = (front + 1) % capacity;
        }
        if (!isFull()) {
            size++;
        }
        rear = (rear + 1) % capacity;
        items[rear] = value;
    }

    /**
     * Removes an element from the front of queue
     *
     * @return Removed element
     */
    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        size--;
        T result = items[front];
        if (front == rear) {
            front = -1;
            rear = -1;
        } else {
            front = (front + 1) % capacity;
        }
        return result;
    }

    /**
     * Returns at element at the front of queue
     *
     * @return Element at the front of queue
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return items[front];
    }

    /**
     * Removes all elements from queue
     */
    @Override
    public void flush() {
        size = 0;
        front = -1;
        rear = -1;
    }

    /**
     * Checks if queue is empty
     *
     * @return true if empty, false if not empty
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if queue is full
     *
     * @return true if full, false if not full
     */
    @Override
    public boolean isFull() {
        return size == capacity;
    }

    /**
     * Returns number of elements in queue
     *
     * @return number of elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns capacity of queue
     *
     * @return capacity
     */
    @Override
    public int capacity() {
        return capacity;
    }
}
