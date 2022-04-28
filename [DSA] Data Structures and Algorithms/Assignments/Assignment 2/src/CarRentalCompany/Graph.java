package CarRentalCompany;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Graph based on adjacency matrix
 *
 * @param <V> vertex
 * @param <E> edge
 * @author Egor Kuziakov
 */
public class Graph<V, E extends Comparable<E>> implements IGraph<V, E> {
    private final Map<V, Map<V, E>> adjacencyMatrix;

    public Graph() {
        adjacencyMatrix = new HashMap<>();
    }

    /**
     * @param v vertex to insert
     */
    @Override
    public void insertVertex(V v) {
        adjacencyMatrix.put(v, new HashMap<>());
    }

    /**
     * @param from vertex connected to edge
     * @param to   vertex connected to edge
     * @param w    edge to insert
     */
    @Override
    public void insertEdge(V from, V to, E w) {
        adjacencyMatrix.get(from).put(to, w);
        adjacencyMatrix.get(to).put(from, w);
    }

    /**
     * @param v vertex to remove
     */
    @Override
    public void removeVertex(V v) {
        for (V connectedVertex : adjacencyMatrix.get(v).keySet()) {
            adjacencyMatrix.get(connectedVertex).remove(v);
        }
        adjacencyMatrix.remove(v);
    }

    /**
     * @param e edge to remove
     */
    @Override
    public void removeEdge(E e) {
        for (V firstVertex : adjacencyMatrix.keySet()) {
            Map<V, E> adjacentVertices = adjacencyMatrix.get(firstVertex);
            for (V secondVertex : adjacentVertices.keySet()) {
                if (adjacentVertices.get(secondVertex).equals(e)) {
                    adjacentVertices.remove(secondVertex);
                }
            }
        }
    }

    /**
     * @param v first vertex
     * @param u second vertex
     * @return true if vertices are adjacent, false if not
     */
    @Override
    public boolean areAdjacent(V v, V u) {
        return adjacencyMatrix.get(v).containsKey(u);
    }

    /**
     * @param v vertex
     * @return degree if vertex
     */
    @Override
    public int degree(V v) {
        return adjacencyMatrix.get(v).size();
    }

    /**
     * @return all vertices in graph
     */
    @Override
    public List<V> getVertices() {
        return new ArrayList<>(adjacencyMatrix.keySet());
    }

    /**
     * @param vertex vertex
     * @return connections (vertex and edge) for vertex
     */
    @Override
    public Map<V, E> getConnections(V vertex) {
        return adjacencyMatrix.get(vertex);
    }
}
