package concurrent;

import functions.TabulatedFunction;

import java.io.Serial;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ComputeIntegral {
    private final int threshold;

    public ComputeIntegral() {
        this.threshold = 10;
    }

    public ComputeIntegral(int threshold) {
        this.threshold = threshold;
    }

    public double calculateIntegral(TabulatedFunction function) {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            IntegralTask task = new IntegralTask(function, 0, function.getCount() - 1, threshold);
            return pool.invoke(task);
        } finally {
            pool.shutdown();
        }
    }

    private static class IntegralTask extends RecursiveTask<Double> {
        @Serial
        private static final long serialVersionUID = 3053017612122027078L;
        private final TabulatedFunction function;
        private final int start;
        private final int end;
        private final int threshold;

        IntegralTask(TabulatedFunction function, int start, int end, int threshold) {
            this.function = function;
            this.start = start;
            this.end = end;
            this.threshold = threshold;
        }

        @Override
        protected Double compute() {
            if (end - start <= threshold) {
                return computeIntegral();
            } else {
                int middle = (start + end) / 2;
                IntegralTask leftTask = new IntegralTask(function, start, middle, threshold);
                IntegralTask rightTask = new IntegralTask(function, middle, end, threshold);

                leftTask.fork();
                double rightResult = rightTask.compute();
                double leftResult = leftTask.join();

                return leftResult + rightResult;
            }
        }

        private double computeIntegral() {
            double sum = 0.0;
            for (int i = start; i < end; i++) {
                double x0 = function.getX(i);
                double x1 = function.getX(i + 1);
                double y0 = function.getY(i);
                double y1 = function.getY(i + 1);
                sum += (x1 - x0) * (y0 + y1) / 2;
            }
            return sum;
        }
    }
}
