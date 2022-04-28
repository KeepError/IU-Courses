package RangeQueries;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * B-Tree with range lookup support
 *
 * @param <K> Key
 * @param <V> Value
 * @author Egor Kuziakov
 */
public class BTreeRangeMap<K extends Comparable<K>, V> implements RangeMap<K, V> {
    private int size = 0;
    private Node<K, V> root;

    public BTreeRangeMap() {
        root = new Node<>();
    }

    /**
     * @return number of elements int the map
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @return true if map contains no elements, false if not
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Insert element to the map
     *
     * @param key   element key
     * @param value element value
     */
    @Override
    public void add(K key, V value) {
        Node<K, V> predictedNode = findNode(key);

        int pairIndex = predictedNode.indexOf(key);
        if (pairIndex != -1) {
            predictedNode.getPairs().set(pairIndex, new Pair<>(key, value));
            return;
        }

        addHelper(key, value, predictedNode);

        size++;
    }

    /**
     * Recursive function for inserting element
     *
     * @param key         element key
     * @param value       element value
     * @param currentNode the node we are working on
     */
    private void addHelper(K key, V value, Node<K, V> currentNode) {
        currentNode.addPairOptimized(new Pair<>(key, value));
        if (!currentNode.moreThanMax()) {
            return;
        }

        // adding parent to root
        if (currentNode.equals(root)) {
            root = new Node<>();
            root.addChild(currentNode);
            currentNode.setParent(root);
        }

        // Finding median
        List<Pair<K, V>> pairs = currentNode.getPairs();
        int medianIndex = pairs.size() / 2;
        Pair<K, V> median = pairs.get(medianIndex);

        // Preparing to split node (generating new children)
        Node<K, V> parent = currentNode.getParent();
        Node<K, V> leftNode = new Node<>(parent);
        Node<K, V> rightNode = new Node<>(parent);
        for (int i = 0; i < pairs.size(); i++) {
            Pair<K, V> pair = pairs.get(i);
            if (i < medianIndex) {
                leftNode.addPair(pair);
            } else if (i > medianIndex) {
                rightNode.addPair(pair);
            }
        }

        // Removing the median and adding new children
        pairs.remove(medianIndex);

        List<Node<K, V>> parentChildren = parent.getChildren();
        int nodeIndex = parentChildren.indexOf(currentNode);
        parentChildren.remove(currentNode);
        parentChildren.add(nodeIndex, rightNode);
        parentChildren.add(nodeIndex, leftNode);

        // Splitting children
        List<Node<K, V>> children = currentNode.getChildren();
        if (!children.isEmpty()) {
            for (int i = 0; i < children.size(); i++) {
                Node<K, V> child = children.get(i);
                child.setParent(currentNode);
                if (i < children.size() / 2) {
                    child.setParent(leftNode);
                    leftNode.addChild(child);
                } else {
                    child.setParent(rightNode);
                    rightNode.addChild(child);
                }
            }
        }

        // Adding the median to parent
        addHelper(median.getKey(), median.getValue(), parent);
    }

    /**
     * @param key element key
     * @return true if key presents in map, false if not
     */
    @Override
    public boolean contains(K key) {
        Node<K, V> predictedNode = findNode(key);

        return predictedNode.contains(key);
    }

    /**
     * @param key element key
     * @return value by key
     */
    @Override
    public V lookup(K key) {
        Node<K, V> predictedNode = findNode(key);
        return predictedNode.getValueByKey(key);
    }

    /**
     * @param from start key
     * @param to   end key
     * @return all values in map for a range of keys
     */
    @Override
    public List<V> lookupRange(K from, K to) {
        List<V> result = new ArrayList<>();
        if (isEmpty()) {
            return result;
        }
        Node<K, V> currentNode = findNode(from);

        int i = 0;
        Pair<K, V> nextPair = currentNode.getPairs().get(i);
        while (nextPair.getKey().compareTo(to) <= 0) {
            if (nextPair.getKey().compareTo(from) >= 0) {
                result.add(nextPair.getValue());
            }
            Pair<Node<K, V>, Integer> next = getNext(currentNode, i);
            if (next == null) {
                return result;
            }
            currentNode = next.getKey();
            i = next.getValue();
            nextPair = currentNode.getPairs().get(i);
        }
        return result;
    }

    /**
     * Function for finding the smallest key in map which is bigger than given
     *
     * @param currentNode element node
     * @param i           element index in node
     * @return pair with next element's node and index
     */
    public Pair<Node<K, V>, Integer> getNext(Node<K, V> currentNode, int i) {
        while (i + 1 <= currentNode.getChildren().size() - 1) {
            currentNode = currentNode.getChildren().get(i + 1);
            i = -1;
        }
        i++;
        while (i >= currentNode.getPairs().size()) {
            if (currentNode.getParent() == null) {
                return null;
            }
            int nodeIndex = currentNode.getParent().getChildren().indexOf(currentNode);
            currentNode = currentNode.getParent();
            i = nodeIndex;
        }
        return new Pair<>(currentNode, i);
    }

    /**
     * @param key element key
     * @return node which contains the key or should contain it
     */
    private Node<K, V> findNode(K key) {
        return findNodeHelper(key, root);
    }

    /**
     * Recursive function for finding node by key
     *
     * @param key         element key
     * @param currentNode node we're in
     * @return found node
     */
    private Node<K, V> findNodeHelper(K key, Node<K, V> currentNode) {
        List<Node<K, V>> nodeChildren = currentNode.getChildren();
        if (nodeChildren.isEmpty()) {
            return currentNode;
        }

        if (currentNode.contains(key)) {
            return currentNode;
        }

        List<Pair<K, V>> pairs = currentNode.getPairs();
        for (int i = 0; i < pairs.size(); i++) {
            K pairKey = pairs.get(i).getKey();
            if (key.compareTo(pairKey) < 0) {
                return findNodeHelper(key, nodeChildren.get(i));
            }
        }
        return findNodeHelper(key, nodeChildren.get(nodeChildren.size() - 1));
    }
}