package concurrent;

import functions.TabulatedFunction;
import java.io.Serial;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ComputeIntegral {

    public double calculateIntegral(TabulatedFunction function) {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            IntegralTask task = new IntegralTask(function, 0, function.getCount() - 1);
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

        IntegralTask(TabulatedFunction function, int start, int end) {
            this.function = function;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Double compute() {
            if (end - start == 1) {
                double x0 = function.getX(start);
                double x1 = function.getX(end);
                double y0 = function.getY(start);
                double y1 = function.getY(end);
                return (x1 - x0) * (y0 + y1) / 2;
            } else {
                int middle = (start + end) / 2;
                IntegralTask leftTask = new IntegralTask(function, start, middle);
                IntegralTask rightTask = new IntegralTask(function, middle, end);

                leftTask.fork();
                double rightResult = rightTask.compute();
                double leftResult = leftTask.join();

                return leftResult + rightResult;
            }
        }
    }
}
