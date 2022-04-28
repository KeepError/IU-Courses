import Queue.ArrayCircularBoundedQueue;
import Queue.ICircularBoundedQueue;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class ArrayCircularBoundedQueueTest {
    @Test
    public void When_EmptyQueue_Expect_CorrectBehavior() {
        ICircularBoundedQueue<Object> queue = new ArrayCircularBoundedQueue<>(4);

        assertFalse(queue.isFull());
        assertTrue(queue.isEmpty());

        assertNull(queue.poll());
        assertNull(queue.peek());

        assertEquals(0, queue.size());
        assertEquals(4, queue.capacity());
    }

    @Test
    public void When_SingleElementPopped_Expect_CorrectBehavior() {
        ICircularBoundedQueue<Integer> queue = new ArrayCircularBoundedQueue<>(4);

        queue.offer(2);

        assertFalse(queue.isEmpty());
        assertFalse(queue.isFull());

        assertEquals(1, queue.size());
        assertEquals(2, queue.poll());
        assertEquals(0, queue.size());

        assertTrue(queue.isEmpty());
        assertFalse(queue.isFull());
    }

    @Test
    public void When_CapacityReached_Expect_CorrectBehavior() {
        ICircularBoundedQueue<Integer> queue = new ArrayCircularBoundedQueue<>(4);

        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);

        assertTrue(queue.isFull());
        assertFalse(queue.isEmpty());
        assertEquals(4, queue.size());

        assertEquals(1, queue.poll());

        assertFalse(queue.isFull());
        assertFalse(queue.isEmpty());
        assertEquals(3, queue.size());

        assertEquals(2, queue.poll());
        assertEquals(3, queue.poll());
        assertEquals(4, queue.poll());
        assertNull(queue.poll());

        assertTrue(queue.isEmpty());
        assertFalse(queue.isFull());
    }
}