package graph.algorithms;

import graph.IGraph;
import graph.model.Edge;
import graph.model.Vertex;

import java.util.List;
import java.util.function.Function;

/**
 * Created by Jakub Fortunka on 17.10.2016.
 */
public class FordBellman<E,V> implements IGraphAlgorithm<E,V> {

    private Integer[] predecessors;

    private Integer[] d;
    private Vertex<V> vertex;

    @Override
    public long executeAlgorithm(IGraph<E, V> graph) {
        return getShortestPathsFromVertex(graph, vertex);
    }

    public void setVertex(Vertex<V> vertex) {
        this.vertex = vertex;
    }

    private long getShortestPathsFromVertex(IGraph<E,V> graph, Function<Edge<E>, Integer> weightFunction, Vertex<V> vertex) {
        List<Vertex<V>> vertices = graph.getAllVertices();
        d = new Integer[graph.verticesSize()];
        predecessors = new Integer[graph.verticesSize()];
        for (Vertex<V> v : vertices) {
            d[v.getId()] = INF;
            predecessors[v.getId()] = null;
        }
        d[vertex.getId()] = 0;
        long startTime = System.nanoTime();
        for (int i = 0;i<vertices.size()-1; i++) {
            for (Edge<E> e : graph.getAllEdges()) {
                if (d[e.getSndVertexId()] > d[e.getFstVertexId()] + weightFunction.apply(e)) {
                    d[e.getSndVertexId()] = d[e.getFstVertexId()] + weightFunction.apply(e);
                    predecessors[e.getSndVertexId()] = e.getFstVertexId();
                }
            }
        }
        return System.nanoTime() - startTime;
    }

    private long getShortestPathsFromVertex(IGraph<E,V> graph, Vertex<V> vertex) {
        return getShortestPathsFromVertex(graph, e -> e.getWeight(), vertex);
    }

    public Integer[] getPredecessors() {
        return predecessors;
    }

    public Integer[] getShortestPaths() {
        return d;
    }

}
