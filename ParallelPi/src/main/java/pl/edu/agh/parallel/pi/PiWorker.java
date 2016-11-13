package pl.edu.agh.parallel.pi;

import java.util.concurrent.Callable;

/**
 * Created by Jakub Fortunka on 13.11.2016.
 */
public class PiWorker implements Callable<Double> {

    private int n;
    private int h;
    private int start;
    private int end;

    public PiWorker(int start, int end, int h, int n) {
        this.n = n;
        this.h = h;
        this.start = start;
        this.end = end;
    }

    public Double call() throws Exception {
        double ans = 0;
        for (double i = start;i<=end; i++) {
            ans += (h / (1 + Math.pow(((2*i + 1)/(2*n)),2)));
        }
        return ans;
    }

}
