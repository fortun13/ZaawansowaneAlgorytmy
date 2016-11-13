package pl.edu.agh.parallel.pi;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jakub Fortunka on 13.11.2016.
 */
public class Main {

    private static int N = 1000000000;
    private static int H = 4;

    public static void main(String[] args) throws InterruptedException {
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(processors);
        List<PiWorker> workers = new LinkedList<>();
        for (int i=0;i<processors;i++) {
            int start = (N/processors)*i;
            int end = (N/processors)*i+(N/processors)-1;
            workers.add(new PiWorker(start, end, H, N));
        }
        long start = System.nanoTime();
        Optional<Double> tmp = pool.invokeAll(workers).stream().map(x -> {
            try {
                return x.get();
            } catch (Exception e){
                throw new IllegalStateException(e);
            }
        }).reduce((x,y) -> x+y);
        long finish = System.nanoTime();

        if (tmp.isPresent()) {
            System.out.println("Processors used: " + processors);
            System.out.println("PI: " + tmp.get());
            System.out.println("Time: " + TimeUnit.NANOSECONDS.toMillis(finish - start));
        } else {
            System.out.println("YABAIDE");
        }
        pool.shutdown();

        start = System.nanoTime();
        double ans = 0;
        for (double i = 0;i<N; i++) {
            ans += (H / (1 + Math.pow(((2*i + 1)/(2*N)),2)));
        }
        finish = System.nanoTime();

        System.out.println("Processors used: 1");
        System.out.println("PI: " + ans);
        System.out.println("Time: " + TimeUnit.NANOSECONDS.toMillis(finish - start));
    }
}
