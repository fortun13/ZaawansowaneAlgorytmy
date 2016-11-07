package graph.algorithms;

import graph.IGraph;
import graph.model.Edge;
import graph.model.Vertex;

import java.lang.reflect.Array;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by Jakub Fortunka on 16.10.2016.
 */
public class FloydWarshall<E,V> implements IGraphAlgorithm<E,V> {

    private Integer[][] predecessors;
    private Integer[][] shortestPaths;

    @Override
    public long executeAlgorithm(IGraph<E, V> graph) {
        return computeShortestPaths(graph);
    }

    private long computeShortestPaths(IGraph<E,V> graph, Function<Edge<E>, Integer> weightFunction) {
        List<Vertex<V>> vertices = graph.getAllVertices();
        List<Edge<E>> edges = graph.getAllEdges();
        shortestPaths = (Integer[][]) Array.newInstance(Integer.class, graph.verticesSize(), graph.verticesSize());
        predecessors = (Integer[][]) Array.newInstance(Integer.class, graph.verticesSize(), graph.verticesSize());
        long startTime = System.nanoTime();
        for (Vertex<V> v1 : vertices) {
            for (Vertex<V> v2 : vertices) {
                shortestPaths[v1.getId()][v2.getId()] = INF;
                predecessors[v1.getId()][v2.getId()] = null;
            }
            shortestPaths[v1.getId()][v1.getId()] = 0;
        }
        for (Edge<E> e : edges) {
            shortestPaths[e.getFstVertexId()][e.getSndVertexId()] = weightFunction.apply(e);
            predecessors[e.getFstVertexId()][e.getSndVertexId()] = e.getFstVertexId();
        }
        for (Vertex<V> u : vertices) {
            for (Vertex<V> v1 : vertices) {
                for (Vertex<V> v2 : vertices) {
                    if (shortestPaths[v1.getId()][v2.getId()] > shortestPaths[v1.getId()][u.getId()] + shortestPaths[u.getId()][v2.getId()]) {
                        shortestPaths[v1.getId()][v2.getId()] = shortestPaths[v1.getId()][u.getId()] + shortestPaths[u.getId()][v2.getId()];
                        predecessors[v1.getId()][v2.getId()] = predecessors[u.getId()][v2.getId()];
                    }
                }
            }
        }
        return System.nanoTime() - startTime;
    }

    private long computeShortestPaths(IGraph<E,V> graph) {
        return computeShortestPaths(graph, e -> e.getWeight());
    }

    public Integer[][] getPredecessors() {
        return predecessors;
    }

    public Integer[][] getShortestPaths() {
        return shortestPaths;
    }


}
