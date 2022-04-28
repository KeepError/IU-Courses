package Queue;

public interface ICircularBoundedQueue<T> {
    void offer(T value);

    T poll();

    T peek();

    void flush();

    boolean isEmpty();

    boolean isFull();

    int size();

    int capacity();
}
