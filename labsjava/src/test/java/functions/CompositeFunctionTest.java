package functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompositeFunctionTest {

    private static class LinearFunction implements MathFunction {
        @Override
        public double apply(double x) {
            return x;
        }
    }

    private static class QuadraticFunction implements MathFunction {
        @Override
        public double apply(double x) {
            return x * x;
        }
    }

    private static class ExponentialFunction implements MathFunction {
        @Override
        public double apply(double x) {
            return Math.exp(x);
        }
    }

    @Test
    void testCompositeOfLinearAndQuadratic() {
        MathFunction linearFunction = new LinearFunction();
        MathFunction quadraticFunction = new QuadraticFunction();
        CompositeFunction compositeFunction = new CompositeFunction(linearFunction, quadraticFunction);

        assertEquals(1, compositeFunction.apply(1), 1e-6);
        assertEquals(4, compositeFunction.apply(2), 1e-6);
        assertEquals(0, compositeFunction.apply(0), 1e-6);
    }

    @Test
    void testCompositeOfQuadraticAndExponential() {
        MathFunction quadraticFunction = new QuadraticFunction();
        MathFunction exponentialFunction = new ExponentialFunction();
        CompositeFunction compositeFunction = new CompositeFunction(quadraticFunction, exponentialFunction);

        assertEquals(Math.exp(1), compositeFunction.apply(1), 1e-6);
        assertEquals(Math.exp(4), compositeFunction.apply(2), 1e-6);
        assertEquals(1, compositeFunction.apply(0), 1e-6);
    }

    @Test
    void testCompositeOfExponentialAndLinear() {
        MathFunction exponentialFunction = new ExponentialFunction();
        MathFunction linearFunction = new LinearFunction();
        CompositeFunction compositeFunction = new CompositeFunction(exponentialFunction, linearFunction);

        assertEquals(Math.exp(1), compositeFunction.apply(1), 1e-6);
        assertEquals(Math.exp(2), compositeFunction.apply(2), 1e-6);
        assertEquals(1, compositeFunction.apply(0), 1e-6);
    }

    @Test
    void testCompositeOfCompositeFunctions() {
        MathFunction linearFunction = new LinearFunction();
        MathFunction quadraticFunction = new QuadraticFunction();
        MathFunction exponentialFunction = new ExponentialFunction();

        CompositeFunction firstComposite = new CompositeFunction(linearFunction, quadraticFunction);
        CompositeFunction secondComposite = new CompositeFunction(firstComposite, exponentialFunction);

        assertEquals(Math.exp(1), secondComposite.apply(1), 1e-6);
        assertEquals(Math.exp(4), secondComposite.apply(2), 1e-6);
        assertEquals(1, secondComposite.apply(0), 1e-6);
    }
}