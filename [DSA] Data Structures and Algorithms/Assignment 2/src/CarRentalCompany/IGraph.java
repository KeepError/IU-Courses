package CarRentalCompany;

import java.util.List;
import java.util.Map;

/**
 * Interface class for graph
 *
 * @param <V> Vertex
 * @param <E> Edge
 */
public interface IGraph<V, E> {
    void insertVertex(V v);

    void insertEdge(V from, V to, E w);

    void removeVertex(V v);

    void removeEdge(E e);

    boolean areAdjacent(V v, V u);

    int degree(V v);

    List<V> getVertices();

    Map<V, E> getConnections(V vertex);
}
