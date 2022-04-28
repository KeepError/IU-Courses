import Stack.QueuedBoundedStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueuedBoundedStackTest {
    @Test
    public void When_EmptyStack_Expect_CorrectBehavior() {
        QueuedBoundedStack<Integer> stack = new QueuedBoundedStack<>(4);

        assertTrue(stack.isEmpty());
        assertFalse(stack.isFull());
        assertEquals(0, stack.size());
        assertEquals(4, stack.capacity());

        assertNull(stack.pop());
        assertNull(stack.top());
    }

    @Test
    public void When_SingleElementPushed_Expect_CorrectBehaviour() {
        QueuedBoundedStack<Integer> stack = new QueuedBoundedStack<>(4);

        stack.push(0);

        assertFalse(stack.isFull());
        assertFalse(stack.isEmpty());

        assertEquals(1, stack.size());

        assertEquals(0, stack.top());
        assertEquals(0, stack.pop());

        assertTrue(stack.isEmpty());
        assertFalse(stack.isFull());
    }

    @Test
    public void When_CapacityReached_Expect_CorrectBehavior() {
        QueuedBoundedStack<Integer> stack = new QueuedBoundedStack<>(4);

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        assertFalse(stack.isEmpty());
        assertTrue(stack.isFull());
        assertEquals(4, stack.size());

        assertEquals(4, stack.top());
        assertEquals(4, stack.pop());

        assertEquals(3, stack.size());
        assertFalse(stack.isFull());

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());

        assertTrue(stack.isEmpty());

        assertNull(stack.top());
        assertNull(stack.pop());
    }

    @Test
    public void When_OverCapacity_Expect_Correct_Behavior() {
        QueuedBoundedStack<Integer> stack = new QueuedBoundedStack<>(4);

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(6);

        assertTrue(stack.isFull());

        assertEquals(4, stack.size());

        assertEquals(6, stack.top());
        assertEquals(6, stack.pop());

        assertEquals(3, stack.size());
        assertFalse(stack.isFull());

        assertEquals(5, stack.pop());
        assertEquals(4, stack.pop());
        assertEquals(3, stack.pop());

        assertTrue(stack.isEmpty());

        assertNull(stack.top());
        assertNull(stack.pop());
    }

    @Test
    public void When_ComplexInteractions_Expect_CorrectBehavior() {
        QueuedBoundedStack<Integer> stack = new QueuedBoundedStack<>(4);

        stack.push(0);
        stack.push(1);
        assertEquals(1, stack.pop());
        stack.push(2);
        stack.push(3);
        stack.push(4);

        assertTrue(stack.isFull());
        assertEquals(4, stack.pop());
        assertFalse(stack.isFull());

        stack.push(5);
        assertTrue(stack.isFull());

        assertEquals(5, stack.pop());
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(0, stack.pop());
        assertNull(stack.pop());

        assertTrue(stack.isEmpty());
    }
}