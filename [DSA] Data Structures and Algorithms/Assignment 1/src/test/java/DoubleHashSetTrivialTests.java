import Set.DoubleHashSet;
import Set.ISet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DoubleHashSetTrivialTests {
    @Test
    public void dataStoreAndChecking(){
        DoubleHashSet<Integer> set = new DoubleHashSet<>(17);

        assertTrue(set.isEmpty());

        Object[] arr = set.toArray();

        assertEquals(0, arr.length);

        set.add(56);
        set.add(74);
        set.add(32);
        set.add(11);

        assertFalse(set.isEmpty());
        arr = set.toArray();

        assertEquals(4, arr.length);

        List<Object> list = List.of(arr);
        assertTrue(set.contains(56));
        assertTrue(list.contains(56));

        assertTrue(set.contains(74));
        assertTrue(list.contains(74));

        assertTrue(set.contains(32));
        assertTrue(list.contains(32));

        assertTrue(set.contains(11));
        assertTrue(list.contains(11));

        assertFalse(set.contains(22));
        assertFalse(list.contains(22));

        set.remove(56);
        set.remove(22);

        arr = set.toArray();

        assertEquals(3, arr.length);
        list = List.of(arr);

        assertFalse(set.contains(22));
        assertFalse(list.contains(22));

        assertFalse(set.contains(56));
        assertFalse(list.contains(56));

        assertTrue(set.contains(74));
        assertTrue(list.contains(74));

        assertTrue(set.contains(32));
        assertTrue(list.contains(32));

        assertTrue(set.contains(11));
        assertTrue(list.contains(11));
    }

    @Test
    public void sizeTest() {
        ISet<Integer> set = new DoubleHashSet<>(5);

        assertEquals(set.size(), 0);

        set.add(1);
        set.add(2);
        assertEquals(set.size(), 2);
        set.add(3);
        set.add(4);
        set.add(5);

        assertEquals(set.size(), 5);

        set.contains(5);
        set.remove(5);
        set.contains(5);
        assertEquals(set.size(), 4);
    }


    @Test
    public void isEmptyTest() {
        ISet <Integer> set = new DoubleHashSet<>(5);

        assertTrue(set.isEmpty());
        set.add(1);
        set.add(2);
        assertFalse(set.isEmpty());
        set.remove(1);
        assertFalse(set.isEmpty());
        set.remove(2);
        assertTrue(set.isEmpty());
    }

    @Test
    public void collisionTest() {
        ISet <Integer> set = new DoubleHashSet<>(5);

        assertFalse(set.contains(1));
        set.add(1);
        set.add(1);

        assertEquals(set.size(), 1);
        assertTrue(set.contains(1));

        set.remove(1);
        assertFalse(set.contains(1));
        assertEquals(set.size(), 0);
    }

    @Test
    public void noElementTest() {
        ISet <Integer> set = new DoubleHashSet<>(5);

        assertFalse(set.contains(1));
        set.add(1);
        set.add(1);

        assertTrue(set.contains(1));

        set.remove(1);
        assertFalse(set.contains(1));
    }

    @Test
    public void removingNotExistingElement() {
        ISet <Integer> set = new DoubleHashSet<>(5);
        set.remove(5);
        assertEquals(set.size(), 0);
    }

    @Test
    public void overflowTest() {
        ISet <Integer> set = new DoubleHashSet<>(5);

        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);
        assertThrows(IllegalStateException.class, () -> set.add(6));
    }

    @Test
    public void emptySetTest() {
        ISet <Integer> set = new DoubleHashSet<>(17);
        assertFalse(set.contains(56));
    }
}