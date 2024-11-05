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
                -10, 10, 21
        );

        ComputeIntegral computeIntegralSmallThr = new ComputeIntegral();
        long startTime = System.nanoTime();
        double result = computeIntegralSmallThr.calculateIntegral(function);
        long endTime = System.nanoTime();
        assertEquals(670, result);
    }
}