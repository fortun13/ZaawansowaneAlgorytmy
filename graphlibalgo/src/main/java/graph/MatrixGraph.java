package graph;

import graph.enums.GraphImplementation;
import graph.enums.GraphType;
import graph.model.Edge;
import graph.model.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by Kuba Fortunka on 10.10.2016.
 */
public class MatrixGraph<E,V> implements IGraph<E,V> {

    private GraphType graphType;

    Edge[][] graph = new Edge[10][10];

    Vertex[] nodes =new Vertex[10];

    public MatrixGraph(GraphType graphType) {
        this.graphType = graphType;
    }

    public void addVertex(Vertex<V> vertex) {
        if (vertex.getId() > graph.length || nodes[vertex.getId()] == null) {
            if (vertex.getId() > graph.length) {
                Edge[][] newGraph = new Edge[vertex.getId()+1][vertex.getId()+1];
                for (int i = 0; i < graph.length; i++) {
                    System.arraycopy(graph[i], 0, newGraph[i], 0, graph.length);
                }
                graph = newGraph;
                Vertex[] newNodes = new Vertex[vertex.getId()+1];
                System.arraycopy(nodes, 0, newNodes, 0, nodes.length);
                nodes = newNodes;
            }
            nodes[vertex.getId()] = vertex;
        }
    }

    public void removeVertex(Vertex<V> vertex) {
        //FIXME
        nodes[vertex.getId()] = null;
        //TODO remove all edges from graph
    }

    public void addEdge(Edge<E> edge) {
        if (graphType == GraphType.UNDIRECTED) {
            graph[edge.getSndVertexId()][edge.getFstVertexId()] = edge;
        }
        graph[edge.getFstVertexId()][edge.getSndVertexId()] = edge;
    }

    public void removeEdge(Edge<E> edge) {
        graph[edge.getFstVertexId()][edge.getSndVertexId()] = null;
    }

    public List<Vertex<V>> getNeighbours(Vertex<V> vertex) {
        List<Vertex<V>> neighbours = new ArrayList<>();
        for (int i=0;i<graph[0].length;i++) {
            if (graph[vertex.getId()][i] != null) {
               neighbours.add(nodes[i]);
            }
        }
        return neighbours;
    }

    @SuppressWarnings("unchecked")
    public List<Edge<E>> getIncidentEdges(Vertex<V> vertex) {
        //TODO i want to use this one liner ._.
//        return Arrays.stream(graph[vertex.getId()-1]).filter(Objects::nonNull).collect(Collectors.toList());

        List<Edge<E>> edges = new ArrayList<>();
        for (int i=0;i<graph[0].length;i++) {
            if (graph[vertex.getId()][i] != null) {
                edges.add(graph[vertex.getId()][i]);
            }
        }
        return edges;
    }

    public int verticesCount() {
        return Math.toIntExact(Arrays.stream(nodes).filter(Objects::nonNull).count());
    }

    public int edgesCount() {
        int count=0;
        for (Edge[] aGraph : graph) {
            for (Edge anAGraph : aGraph) {
                if (anAGraph != null) {
                    count++;
                }
            }
        }
        return (graphType == GraphType.UNDIRECTED) ? count/2 : count;
    }

    public boolean edgeBetweenVerticesExists(Vertex first, Vertex second) {
        return graph[first.getId()][second.getId()] != null;
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
        for (int i=0;i<graph.length;i++) {
            for (int j=0;j<graph[i].length;j++) {
                if (graph[i][j] != null && graph[i][j].getFstVertexId() == i) {
                    ans.add(graph[i][j]);
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
        return GraphImplementation.MATRIX;
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (Edge[] aGraph : graph) {
            for (Edge anAGraph : aGraph) {
                if (anAGraph != null) {
                    ans.append(anAGraph.toString()).append(", ");
                }
            }
            ans.append("\n");
        }
        return ans.toString();
    }
}
