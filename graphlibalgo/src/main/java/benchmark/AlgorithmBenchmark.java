package benchmark;

import graph.IGraph;
import graph.algorithms.IGraphAlgorithm;
import graph.enums.GraphImplementation;
import graph.enums.GraphType;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * Created by Jakub Fortunka on 17.10.2016.
 */
public class AlgorithmBenchmark<E,V> {

    private static final int ITERATION_COUNT = 30;

    public StringBuilder testAlgorithm(Collection<IGraph<E,V>> testGraphs,
                                       BiConsumer<Map<GraphImplementation, Long>, StringBuilder> additionalInfoConsumer,
                                       IGraphAlgorithm<E,V> preparedAlgorithm,
                              BiConsumer<IGraphAlgorithm<E,V>, StringBuilder> additionalInfoFromAlgorithm) {
        StringBuilder timesStr = new StringBuilder();
        List<Map<GraphImplementation, Long>> times = new ArrayList<>();
        for (int i=0;i<ITERATION_COUNT;i++) {
            System.out.println("LOOP: " + (i+1) + " OUT OF " + ITERATION_COUNT);
            Map<GraphImplementation, Long> graphsTimes = new HashMap<>(testGraphs.size());
            for (IGraph<E,V> graph : testGraphs) {
                long executionTime = preparedAlgorithm.executeAlgorithm(graph);
                timesStr.append(graph.getClass().getSimpleName()).append(" time: ").append(TimeUnit.NANOSECONDS.toMillis(executionTime));
                graphsTimes.put(graph.getType(), executionTime);
                additionalInfoFromAlgorithm.accept(preparedAlgorithm, timesStr);
            }
            additionalInfoConsumer.accept(graphsTimes, timesStr);

            timesStr.append("\n");
            times.add(graphsTimes);
        }
        if (testGraphs.size() == 2) {
            Pair<Long, Long> avg = times.stream()
                    .map(x -> new Pair<>(x.get(GraphImplementation.MATRIX), x.get(GraphImplementation.ADJACENCY)))
                    .reduce(new Pair<>(0L, 0L),
                            (acc, p) -> new Pair<>(acc.getKey() + p.getKey(), acc.getValue() + p.getValue()));
            timesStr.append("\n\n");
            timesStr.append("AVERAGE R: ").append(((double) avg.getValue()) / ((double) avg.getKey()));
        }
        return timesStr;
    }

}
