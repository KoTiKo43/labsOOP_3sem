package concurrent;

import functions.LinkedListTabulatedFunction;
import functions.SqrFunction;
import functions.TabulatedFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComputeIntegralTest {
    SqrFunction sqr = new SqrFunction();
    @Test
    void calculateIntegral() {
        TabulatedFunction function = new LinkedListTabulatedFunction(sqr,
                -100, 100, 100000
        );

        ComputeIntegral computeIntegralSmallThr = new ComputeIntegral();
        ComputeIntegral computeIntegralLargeThr = new ComputeIntegral(100000);
        long startTime1 = System.nanoTime();
        double result1 = computeIntegralSmallThr.calculateIntegral(function);
        long endTime1 = System.nanoTime();
        long startTime2 = System.nanoTime();
        double result2 = computeIntegralLargeThr.calculateIntegral(function);
        long endTime2 = System.nanoTime();
        System.out.println("1. Result: " + result1 + " \nTime: " + (endTime1 - startTime1) / 10e9 + "s");
        System.out.println("2. Result: " + result2 + " \nTime: " + (endTime2 - startTime2) / 10e9 + "s");
    }
}