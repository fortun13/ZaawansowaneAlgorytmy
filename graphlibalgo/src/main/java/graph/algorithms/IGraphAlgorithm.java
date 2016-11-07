package graph.algorithms;

import graph.IGraph;
import graph.model.Vertex;

/**
 * Created by Jakub Fortunka on 17.10.2016.
 */
public interface IGraphAlgorithm<E,V> {

    public final static Integer INF = 100000;

    long executeAlgorithm(IGraph<E,V> graph);

}
