package pl.edu.agh.parallel.matrices;

import javafx.util.Pair;
import pl.edu.agh.parallel.matrices.exceptions.BadMatricesDimensionsException;
import pl.edu.agh.parallel.matrices.model.Matrix;
import pl.edu.agh.parallel.matrices.utils.LambdaExceptionUtils;
import pl.edu.agh.parallel.matrices.utils.MatrixReader;
import pl.edu.agh.parallel.matrices.workers.MatrixWorker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * Created by Kuba Fortunka on 14.11.2016.
 */
public class Main {

    private static final File MATRICES = new File("sample-matrices.txt");

    public static void main(String[] args) {
        try {
            List<Matrix> matrices = MatrixReader.readMatrices(MATRICES, 4000);
            Pair<Matrix, Long> seq = doSequentially(matrices);
            Pair<Matrix, Long> par = doParallel(matrices);
            System.out.println("Matrices equals: " + seq.getKey().equals(par.getKey()));
            System.out.println("Sequential: " + TimeUnit.NANOSECONDS.toMillis(seq.getValue()) + " Parallel: " + TimeUnit.NANOSECONDS.toMillis(par.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Pair<Matrix, Long> doSequentially(List<Matrix> matrices) throws BadMatricesDimensionsException {
        long start = System.nanoTime();
        Matrix m = matrices.get(0);
        for (int i=1;i<matrices.size();i++) {
            m = m.multiply(matrices.get(i));
        }
        return new Pair<>(m, (System.nanoTime() - start));
    }

    private static Pair<Matrix, Long> doParallel(List<Matrix> matrices) throws Exception {
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(processors);
        List<MatrixWorker> workers = new LinkedList<>();
        final int N = matrices.size();
        for (int i=0;i<processors;i++) {
            Double start =  (((double)N / processors) * i);
            Double end = (((double)N / processors) * (i + 1));
            workers.add(new MatrixWorker(matrices.subList(start.intValue(), end.intValue()), i));
        }
        long start = System.nanoTime();

        Optional<Matrix> tmp = pool.invokeAll(workers).stream()
                .map(LambdaExceptionUtils.rethrowFunction(Future::get))
                .sorted((left, right) -> left.getIndex().compareTo(right.getIndex()))
                .reduce(LambdaExceptionUtils.rethrowBinaryOperator(Matrix::multiply));

        pool.shutdown();
        if (tmp.isPresent()) {
            return new Pair<>(tmp.get(), (System.nanoTime() - start));
        } else {
            return new Pair<>(new Matrix(new ArrayList<>()), (System.nanoTime() - start));
        }
    }
}
