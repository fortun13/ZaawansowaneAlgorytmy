package graph;

import graph.enums.GraphImplementation;
import graph.enums.GraphType;
import graph.model.Edge;
import graph.model.Vertex;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Jakub Fortunka on 10.10.2016.
 */
public class AdjacencyGraph<E,V> implements IGraph<E,V> {

    private GraphType graphType;

    private List<Edge<E>>[] graph;

    private Vertex[] nodes = new Vertex[10];

    public AdjacencyGraph(GraphType graphType) {
        this();
        this.graphType = graphType;
    }

    public AdjacencyGraph() {
        this.graph = new List[10];
        for (int i=0;i<graph.length;i++) {
            graph[i] = new ArrayList<>();
        }
    }

    @Override
    public void addVertex(Vertex<V> vertex) {
        if (vertex.getId() > graph.length || nodes[vertex.getId()] == null) {
            if (vertex.getId() > graph.length) {
                List<Edge<E>>[] newGraph = new List[vertex.getId()+1];
                System.arraycopy(graph, 0, newGraph, 0, graph.length);
                for (int i=graph.length;i<newGraph.length;i++) {
                    newGraph[i] = new ArrayList<>();
                }
                graph = newGraph;

                Vertex[] newNodes = new Vertex[vertex.getId()+1];
                System.arraycopy(nodes, 0, newNodes, 0, nodes.length);
                nodes = newNodes;
            }
            nodes[vertex.getId()] = vertex;
        }
    }

    @Override
    public void removeVertex(Vertex<V> vertex) {
        nodes[vertex.getId()] = null;
    }

    @Override
    public void addEdge(Edge<E> edge) {
        if (graphType == GraphType.UNDIRECTED) {
            graph[edge.getSndVertexId()].add(edge);
        }
        graph[edge.getFstVertexId()].add(edge);
    }

    @Override
    public void removeEdge(Edge<E> edge) {
        //TODO remove edge in undirected graph
        graph[edge.getFstVertexId()].remove(edge);
    }

    @Override
    public List<Vertex<V>> getNeighbours(Vertex<V> vertex) {
        List<Vertex<V>> ans = new ArrayList<>();
        for (Edge e : graph[vertex.getId()]) {
            ans.add(e.getFstVertexId() == vertex.getId() ? nodes[e.getSndVertexId()] : nodes[e.getFstVertexId()]);
        }
        return ans;
    }

    @Override
    public List<Edge<E>> getIncidentEdges(Vertex<V> vertex) {
        return graph[vertex.getId()];
    }

    @Override
    public int verticesCount() {
        return Math.toIntExact(Arrays.stream(nodes).filter(Objects::nonNull).count());
    }

    @Override
    public int edgesCount() {
        int ans = Arrays.stream(graph).map(List::size).reduce(Math::addExact).get();
        return (graphType == GraphType.UNDIRECTED) ? ans/2 : ans;
    }

    @Override
    public boolean edgeBetweenVerticesExists(Vertex first, Vertex second) {
        return graph[first.getId()].stream().anyMatch(edge -> edge.getFstVertexId() == second.getId() || edge.getSndVertexId() == second.getId());
    }

    @Override
    public List<Vertex<V>> getAllVertices() {
        List<Vertex<V>> ans = new ArrayList<>();
        Arrays.stream(nodes).filter(Objects::nonNull).forEach(ans::add);
        return ans;
    }

    @Override
    public List<Edge<E>> getAllEdges() {
        List<Edge<E>> ans = new ArrayList<>();
        for (int i=0; i<graph.length; i++) {
            for (Edge<E> edge : graph[i]) {
                if (edge.getFstVertexId() == i) {
                    ans.add(edge);
                }
            }
        }
        return ans;
    }

    @Override
    public int verticesSize() {
        return graph.length;
    }

    @Override
    public GraphImplementation getType() {
        return GraphImplementation.ADJACENCY;
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (List<Edge<E>> aGraph : graph) {
            ans.append(aGraph.stream().map(Edge::toString).collect(Collectors.joining(", ")));
            ans.append("\n");
        }
        return ans.toString();
    }
}
