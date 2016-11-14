package pl.edu.agh.parallel;

import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by Kuba Fortunka on 14.11.2016.
 */
public class Main {

    private static final File MATRICES = new File("sample-matrices.txt");

    public static void main(String[] args) {
        try {
            List<Matrix> matrices = MatrixReader.readMatrices(MATRICES, 100);
            Pair<Matrix, Long> seq = doSequentially(matrices);
            Pair<Matrix, Long> par = doParallel(matrices);
            System.out.println("SEQ: " + seq.getValue() + " PAR: " + par.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Pair<Matrix, Long> doSequentially(List<Matrix> matrices) throws Exception {
        long start = System.nanoTime();
        Matrix m = matrices.get(0);
        for (int i=1;i<matrices.size();i++) {
            m = m.multiply(matrices.get(i));
        }
        return new Pair<>(m, (System.nanoTime() - start));
    }

    private static Pair<Matrix, Long> doParallel(List<Matrix> matrices) throws InterruptedException {
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(processors);
        List<MatrixWorker> workers = new LinkedList<>();
        final int N = matrices.size();
        for (int i=0;i<processors;i++) {
            int start = (N/processors)*i;
            int end = (N/processors)*i+(N/processors)-1;
            workers.add(new MatrixWorker(matrices.subList(start, end), i));
        }
        long start = System.nanoTime();
        List<Matrix> tmpList = pool.invokeAll(workers).stream().map(x -> {
            try {
                return x.get();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        })
                .sorted((left, right) -> left.getIndex().compareTo(right.getIndex()))
                .collect(Collectors.toList());
        tmpList.forEach(x -> System.out.println(x.getIndex() + " " + x.columns() + " " + x.rows()));
        Optional<Matrix> tmp = tmpList.stream()
                .reduce((x, y) -> {
                    try {
                        return x.multiply(y);
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                });
        return new Pair<>(tmp.get(), (System.nanoTime() - start));
    }
}
