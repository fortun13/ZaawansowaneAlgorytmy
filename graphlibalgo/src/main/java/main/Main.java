package main;

import benchmark.AlgorithmBenchmark;
import graph.IGraph;
import graph.algorithms.FloydWarshall;
import graph.algorithms.FordBellman;
import graph.algorithms.FordFulkerson;
import graph.algorithms.IGraphAlgorithm;
import graph.enums.GraphImplementation;
import graph.enums.GraphType;
import graph.model.Vertex;
import graph.readers.CSVReader;
import graph.readers.IGraphReader;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * Created by Kuba Fortunka on 10.10.2016.
 */
public class Main {

    private static final File GRAPH_FILE = new File("duzy_graf.txt");

    private static final Vertex<String> START_NODE_VER = new Vertex<>(109,"");
    private static final Vertex<String> END_NODE_VER = new Vertex<>(609, "");

    private static final int START_NODE = 1;
    private static final int END_NODE = 20;

    public static void main(String[] args) {
        try {
            IGraphReader<String, String> graphReader = new CSVReader<>();
            List<IGraph<String, String>> graphs = new ArrayList<>(2);
            IGraph<String, String> matrixGraph = graphReader.readGraphFile(GRAPH_FILE, GraphImplementation.MATRIX, GraphType.DIRECTED);
            IGraph<String, String> adjacencyGraph = graphReader.readGraphFile(GRAPH_FILE, GraphImplementation.ADJACENCY, GraphType.DIRECTED);
            graphs.add(matrixGraph);
            graphs.add(adjacencyGraph);
            AlgorithmBenchmark<String, String> benchmark = new AlgorithmBenchmark<>();
            /*IGraphAlgorithm<String, String> algo = new FloydWarshall<>();
            StringBuilder tmp = benchmark.testAlgorithm(graphs, (map, builder) -> {
                builder.append("; R: ").append(((double) map.get(GraphImplementation.ADJACENCY)) / ((double) map.get(GraphImplementation.MATRIX)));
            }, algo, (algorithm, builder) -> {
                FloydWarshall<String, String> algoCast = (FloydWarshall<String, String>) algorithm;
                builder.append("; shortest path size: ").append(algoCast.getShortestPaths()[START_NODE][END_NODE]);
                builder.append("; shortest path: ").append(getPath(START_NODE, END_NODE, algoCast.getPredecessors()));
                builder.append("; ");
            });
            System.out.println(tmp.toString());

            FordBellman<String, String> fb = new FordBellman<>();
            fb.setVertex(new Vertex<>(START_NODE, String.valueOf(START_NODE)));

            StringBuilder fbStr = benchmark.testAlgorithm(graphs, (map, builder) -> {
                builder.append("; R: ").append(((double) map.get(GraphImplementation.ADJACENCY)) / ((double) map.get(GraphImplementation.MATRIX)));
            }, fb, (algorithm, builder) -> {
               FordBellman<String, String> algoCast = (FordBellman<String, String>) algorithm;
                builder.append("; shortest path from 1 to 20 size: ").append(algoCast.getShortestPaths()[END_NODE]);
                builder.append("; shortest path from 1 to 20: ").append(getPath(START_NODE, END_NODE, algoCast.getPredecessors()));
                builder.append("; ");
            });

            System.out.println(fbStr.toString());*/

            FordFulkerson<String, String> ff= new FordFulkerson<>();
            ff.setUp(START_NODE_VER, END_NODE_VER);
            long time = ff.executeAlgorithm(matrixGraph);
            System.out.println("Time(miliseconds): " + TimeUnit.NANOSECONDS.toMillis(time) + "; maximum flow: " + ff.getMaximumFlow());
//            StringBuilder ffStr = benchmark.testAlgorithm(graphs, (map, builder) -> {
//                builder.append("; R: ").append(((double) map.get(GraphImplementation.ADJACENCY)) / ((double) map.get(GraphImplementation.MATRIX)));
//            }, ff, (algorithm, builder) -> {
//                FordFulkerson<String, String> algoCast = (FordFulkerson<String, String>) algorithm;
//                builder.append("; maximum flow: ").append(algoCast.getMaximumFlow());
//                builder.append("; ");
//            });
//
//            System.out.println(ffStr.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getPath(int start, int finish, Integer[] predecessors) {
        return getPath(start, finish, (s,f) -> predecessors[f]);
    }

    private static String getPath(int start, int finish, Integer[][] predecessors) {
        return getPath(start, finish, (s,f) -> predecessors[s][f]);
    }

    private static String getPath(int start, int finish, BiFunction<Integer, Integer, Integer> getPredecessorFunction) {
        StringBuilder ans = new StringBuilder();
        List<Integer> path = new ArrayList<>();
        path.add(finish);
        while (start != finish) {
            Integer tmp = getPredecessorFunction.apply(start, finish);
//            Integer tmp = predecessors[start][finish];
            if (tmp == null) {
                ans.append("NO PATH :(");
                break;
            } else {
                finish = tmp;
            }
            path.add(finish);
        }
        Collections.reverse(path);
        for (int i=0;i<path.size()-1;i++) {
            ans.append(path.get(i)).append(" -> ");
        }
        ans.append(path.get(path.size()-1));
        return ans.toString();
    }

}
