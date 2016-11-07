package graph;

import graph.enums.GraphImplementation;
import graph.enums.GraphType;
import graph.model.Edge;
import graph.model.Vertex;

import java.util.List;

/**
 * Created by Kuba Fortunka on 10.10.2016.
 */
public interface IGraph<E,V> {

    void addVertex(Vertex<V> vertex);
    void removeVertex(Vertex<V> vertex);

    void addEdge(Edge<E> edge);
    void removeEdge(Edge<E> edge);

    List<Vertex<V>> getNeighbours(Vertex<V> vertex);

    List<Edge<E>> getIncidentEdges(Vertex<V> vertex);

    int verticesCount();
    int edgesCount();

    boolean edgeBetweenVerticesExists(Vertex first, Vertex second);

    List<Vertex<V>> getAllVertices();

    List<Edge<E>> getAllEdges();

    int verticesSize();

    GraphImplementation getType();
}
