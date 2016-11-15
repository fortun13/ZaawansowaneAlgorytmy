package pl.edu.agh.parallel.matrices.workers;

import pl.edu.agh.parallel.matrices.model.Matrix;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Kuba Fortunka on 14.11.2016.
 */
public class MatrixWorker implements Callable<Matrix> {

    private List<Matrix> matrices;

    public MatrixWorker(List<Matrix> matrices, int index) {
        this.matrices = matrices;
        this.matrices.forEach(x -> x.setIndex(index));
    }

    @Override
    public Matrix call() throws Exception {
        Matrix m = matrices.get(0);
        for (int i=1;i<matrices.size();i++) {
            m = m.multiply(matrices.get(i));
        }
        return m;
    }
}
