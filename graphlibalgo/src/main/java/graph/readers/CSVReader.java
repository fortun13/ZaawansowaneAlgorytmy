package graph.readers;

import graph.AdjacencyGraph;
import graph.IGraph;
import graph.MatrixGraph;
import graph.enums.GraphImplementation;
import graph.enums.GraphType;
import graph.model.Edge;
import graph.model.Vertex;

import java.io.*;

/**
 * Created by Jakub Fortunka on 12.10.2016.
 */
public class CSVReader<E,V> implements IGraphReader<E,V> {

    @Override
    public IGraph<E, V> readGraphFile(File graphFile, GraphImplementation graphImplementation, GraphType graphType) throws IOException {
        IGraph<E,V> graph;
        switch(graphImplementation) {
            case MATRIX: graph = new MatrixGraph<>(graphType); break;
            case ADJACENCY: graph = new AdjacencyGraph<>(graphType); break;
            default: graph = new MatrixGraph<>(graphType);
        }
        BufferedReader br = new BufferedReader(new FileReader(graphFile));
        String line;
        while ((line = br.readLine()) != null) {
            parseLineAndAddToGraph(line, graph);
        }
        return graph;
    }

    private <E,V> void parseLineAndAddToGraph(String line, IGraph<E,V> graph) {
        String[] elements = line.split(";");
        String fstVertexId = elements[0].trim();
        String sndVertexId = elements[1].trim();
        Vertex fstVertex = new Vertex<>(Integer.valueOf(fstVertexId), fstVertexId);
        Vertex sndVertex = new Vertex<>(Integer.valueOf(sndVertexId), sndVertexId);
        Edge edge = new Edge<>(elements[2].trim(), fstVertex.getId(), sndVertex.getId(), Integer.valueOf(elements[2].trim()));
        graph.addVertex(fstVertex);
        graph.addVertex(sndVertex);
        graph.addEdge(edge);
    }
}
