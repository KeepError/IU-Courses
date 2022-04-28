package Stack;

public interface IBoundedStack<T> {
    void push(T value);

    T pop();

    T top();

    void flush();

    boolean isEmpty();

    boolean isFull();

    int size();

    int capacity();
}