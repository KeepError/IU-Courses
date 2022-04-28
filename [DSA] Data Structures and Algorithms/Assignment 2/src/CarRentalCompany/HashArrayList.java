package CarRentalCompany;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Array list with hashed elements for more efficient search
 *
 * @param <E> elements type
 * @author Egor Kuziakov
 */
public class HashArrayList<E> extends ArrayList<E> {
    private final Map<E, Integer> elementIndexes = new HashMap<>();

    @Override
    public E set(int index, E element) {
        elementIndexes.remove(get(index));
        elementIndexes.put(element, index);
        return super.set(index, element);
    }

    @Override
    public boolean add(E e) {
        elementIndexes.put(e, size());
        return super.add(e);
    }

    @Override
    public E remove(int index) {
        elementIndexes.remove(get(index));
        return super.remove(index);
    }

    @Override
    @SuppressWarnings("unchecked")
    public int indexOf(Object o) {
        Integer index = elementIndexes.get((E) o);
        return index != null ? index : -1;
    }

    /**
     * Swaps two elements in list
     *
     * @param i first element index
     * @param j second element index
     */
    public void swap(int i, int j) {
        elementIndexes.put(get(i), j);
        elementIndexes.put(get(j), i);

        E temp = get(i);
        super.set(i, get(j));
        super.set(j, temp);
    }
}
