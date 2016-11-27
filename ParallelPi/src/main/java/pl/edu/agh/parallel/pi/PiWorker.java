package pl.edu.agh.parallel.pi;

import java.util.concurrent.Callable;

/**
 * Created by Jakub Fortunka on 13.11.2016.
 */
class PiWorker implements Callable<Double> {

    private double n;
    private int start;
    private int end;

    PiWorker(int start, int end, double n) {
        this.n = n;
        this.start = start;
        this.end = end;
    }

    public Double call() throws Exception {
        double ans = 0;
        for (double i = start;i<=end; i++) {
            ans += (1 / (1 + Math.pow(((2*i + 1)/(2*n)),2)));
        }
        return ans;
    }

}
