package RangeQueries;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for B-Tree nodes
 *
 * @param <K> key
 * @param <V> value
 * @author Egor Kuziakov
 */
public class Node<K extends Comparable<K>, V> {
    private final int maxSize = 2;
    private Node<K, V> parent;
    private final List<Pair<K, V>> pairs;
    private final List<Node<K, V>> children;

    public Node() {
        this.pairs = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public Node(Node<K, V> parent) {
        this();
        this.parent = parent;
    }

    /**
     * @return node's children
     */
    public List<Node<K, V>> getChildren() {
        return children;
    }

    /**
     * @return node's pairs
     */
    public List<Pair<K, V>> getPairs() {
        return pairs;
    }

    /**
     * @param key element key
     * @return value in node by key
     */
    public V getValueByKey(K key) {
        for (Pair<K, V> pair : pairs) {
            if (pair.getKey().equals(key)) {
                return pair.getValue();
            }
        }
        return null;
    }

    /**
     * @param key element key
     * @return true if key presents in node, false if not
     */
    public boolean contains(K key) {
        for (Pair<K, V> pair : pairs) {
            if (pair.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param key element key
     * @return -1 if node doesn't contain key or index of key if not
     */
    public int indexOf(K key) {
        for (int i = 0; i < pairs.size(); i++) {
            K pairKey = pairs.get(i).getKey();
            if (pairKey.equals(key)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Adding pair without sorting
     *
     * @param pair pair which should be added in node
     */
    public void addPair(Pair<K, V> pair) {
        pairs.add(pair);
    }

    /**
     * Adding pair with sorting
     *
     * @param pair pair which should be added in node
     */
    public void addPairOptimized(Pair<K, V> pair) {
        for (int i = 0; i < pairs.size(); i++) {
            if (pair.getKey().compareTo(pairs.get(i).getKey()) < 0) {
                pairs.add(i, pair);
                return;
            }
        }
        pairs.add(pair);
    }

    /**
     * @param parent new node's parent
     */
    public void setParent(Node<K, V> parent) {
        this.parent = parent;
    }

    /**
     * @return true if number of pairs in node more than max, false if not
     */
    public boolean moreThanMax() {
        return pairs.size() > maxSize;
    }

    /**
     * @return node's parent
     */
    public Node<K, V> getParent() {
        return parent;
    }

    /**
     * @param child child which should be added to node
     */
    public void addChild(Node<K, V> child) {
        children.add(child);
    }
}
