package graph.algorithms;

import graph.IGraph;
import graph.model.Edge;
import graph.model.Vertex;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Jakub Fortunka on 17.10.2016.
 *
 *
 *
 * implemented in form of edmonds-karp algorithm
 *
 *
 *
 */
public class FordFulkerson<E,V> implements IGraphAlgorithm<E,V> {

    private Vertex<V> source;
    private Vertex<V> sink;

    private int maximumFlow;

    private int[][] legalFlows;

    private Integer[] parentTable;

    @Override
    public long executeAlgorithm(IGraph<E, V> graph) {
        return computerMaximumFlow(graph, source, sink);
    }

    private long computerMaximumFlow(IGraph<E, V> graph, Vertex<V> source, Vertex<V> sink) {
        long startTime = System.nanoTime();
        maximumFlow = 0;
        legalFlows = new int[graph.verticesSize()][graph.verticesSize()];
//        legalFlows = (Integer[][]) Array.newInstance(Integer.class, graph.verticesSize(), graph.verticesSize());
//        for (int i=0;i<legalFlows.length;i++) {
//            for (int j=0;j<legalFlows[i].length;j++) {
//                legalFlows[i][j] = 0;
//            }
//        }
        while (true) {
            int m = BFS(graph, source, sink, legalFlows);
            if (m == 0) {
                break;
            }
            maximumFlow += m;
            Integer v = sink.getId();
            while (v != source.getId()) {
                Integer u = parentTable[v];
                legalFlows[u][v] += m;
                legalFlows[v][u] -= m;
                v = u;
            }
        }
        return System.nanoTime() - startTime;
    }

    private int BFS(IGraph<E,V> graph, Vertex<V> source, Vertex<V> sink, int[][] flows) {
        parentTable = (Integer[]) Array.newInstance(Integer.class, graph.verticesSize());
        for (int i=0;i<parentTable.length;i++) {
            parentTable[i] = -1;
        }
        parentTable[source.getId()] = -2;
        Integer[] M = (Integer[]) Array.newInstance(Integer.class, graph.verticesSize());
        M[source.getId()] = INF;
        Queue<Vertex<V>> Q = new LinkedList<>();
        Q.offer(source);
        while (Q.size() > 0) {
            Vertex<V> u = Q.poll();
            for (Edge<E> edge : graph.getIncidentEdges(u)) {
                if (edge.getWeight() - flows[u.getId()][edge.getSndVertexId()] > 0 && parentTable[edge.getSndVertexId()] == -1) {
                    parentTable[edge.getSndVertexId()] = u.getId();
                    M[edge.getSndVertexId()] = Math.min(M[u.getId()], edge.getWeight()-flows[u.getId()][edge.getSndVertexId()]);
                    if (edge.getSndVertexId() != sink.getId()) {
                        Q.offer(new Vertex<V>(edge.getSndVertexId(), u.getValue()));
                    } else {
                        return M[sink.getId()];
                    }
                }
            }
        }
        return 0;
    }

    public int getMaximumFlow() {
        return maximumFlow;
    }

    public int[][] getLegalFlows() {
        return legalFlows;
    }

    public void setSource(Vertex<V> source) {
        this.source = source;
    }

    public void setSink(Vertex<V> sink) {
        this.sink = sink;
    }

    public void setUp(Vertex<V> source, Vertex<V> sink) {
        this.source = source;
        this.sink = sink;
    }
}
