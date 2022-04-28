package Stack;

import Queue.ArrayCircularBoundedQueue;

/**
 * Implementation of Bounded Stack using Circular Bounded Queue ADT
 *
 * @param <T> Type of stored values
 * @author Egor Kuziakov
 */
public class QueuedBoundedStack<T> implements IBoundedStack<T> {
    private final int capacity;
    private int activeQueue = 0;
    private int notActiveQueue = 1;
    private final ArrayCircularBoundedQueue[] queues;

    /**
     * Initialization of stack
     *
     * @param capacity max size of stack
     */
    public QueuedBoundedStack(int capacity) {
        queues = new ArrayCircularBoundedQueue[]{new ArrayCircularBoundedQueue<T>(capacity), new ArrayCircularBoundedQueue<T>(capacity)};
        this.capacity = capacity;
    }

    /**
     * Pushes an element onto the stack or removes the oldest if the stack is full
     *
     * @param value An element to be added to the stack
     */
    @Override
    @SuppressWarnings("unchecked")
    public void push(T value) {
        queues[activeQueue].offer(value);
    }

    /**
     * Removes an element from the top of the stack
     *
     * @return removed element
     */
    @Override
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            return null;
        }

        while (queues[activeQueue].size() > 1) {
            queues[notActiveQueue].offer(queues[activeQueue].poll());
        }

        swapActiveQueue();
        return (T) queues[notActiveQueue].poll();
    }

    /**
     * Returns an element from the top of the stack
     *
     * @return element from the top of the stack
     */
    @Override
    @SuppressWarnings("unchecked")
    public T top() {
        if (isEmpty()) {
            return null;
        }

        T value = pop();
        queues[activeQueue].offer(value);
        return value;
    }

    /**
     * Removes all elements from the stack
     */
    @Override
    public void flush() {
        queues[activeQueue].flush();
    }

    /**
     * Checks if stack is empty
     *
     * @return true if empty, false if not empty
     */
    @Override
    public boolean isEmpty() {
        return queues[activeQueue].isEmpty();
    }

    /**
     * Checks if stack is full
     *
     * @return true if full, false if not full
     */
    @Override
    public boolean isFull() {
        return queues[activeQueue].isFull();
    }

    /**
     * Returns number of elements
     *
     * @return size
     */
    @Override
    public int size() {
        return queues[activeQueue].size();
    }

    /**
     * Returns maximum size of stack
     *
     * @return capacity
     */
    @Override
    public int capacity() {
        return capacity;
    }

    private void swapActiveQueue() {
        if (activeQueue == 0) {
            activeQueue = 1;
            notActiveQueue = 0;
        } else {
            activeQueue = 0;
            notActiveQueue = 1;
        }
    }
}
