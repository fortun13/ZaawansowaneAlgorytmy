package graph.readers;

import graph.IGraph;
import graph.enums.GraphImplementation;
import graph.enums.GraphType;

import java.io.File;
import java.io.IOException;

/**
 * Created by Jakub Fortunka on 12.10.2016.
 */
public interface IGraphReader<E,V> {

    IGraph<E, V> readGraphFile(File graphFile, GraphImplementation graphImplementation, GraphType graphType) throws IOException;

}
