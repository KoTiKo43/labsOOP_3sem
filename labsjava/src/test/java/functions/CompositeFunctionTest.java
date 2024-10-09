package functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompositeFunctionTest {

    private static class ExponentialFunction implements MathFunction {
        @Override
        public double apply(double x) {
            return Math.exp(x);
        }
    }

    @Test
    void testCompositeOfIdentityAndSqr() {
        MathFunction linearFunction = new IdentityFunction();
        MathFunction sqr = new SqrFunction();
        CompositeFunction compositeFunction = new CompositeFunction(linearFunction, sqr);

        assertEquals(1, compositeFunction.apply(1), 1e-6);
        assertEquals(4, compositeFunction.apply(2), 1e-6);
        assertEquals(0, compositeFunction.apply(0), 1e-6);
    }

    @Test
    void testCompositeOfSqrAndExponential() {
        MathFunction sqr = new SqrFunction();
        MathFunction exponentialFunction = new ExponentialFunction();
        CompositeFunction compositeFunction = new CompositeFunction(sqr, exponentialFunction);

        assertEquals(Math.exp(1), compositeFunction.apply(1), 1e-6);
        assertEquals(Math.exp(4), compositeFunction.apply(2), 1e-6);
        assertEquals(1, compositeFunction.apply(0), 1e-6);
    }

    @Test
    void testCompositeOfExponentialAndIdentity() {
        MathFunction exponentialFunction = new ExponentialFunction();
        MathFunction linearFunction = new IdentityFunction();
        CompositeFunction compositeFunction = new CompositeFunction(exponentialFunction, linearFunction);

        assertEquals(Math.exp(1), compositeFunction.apply(1), 1e-6);
        assertEquals(Math.exp(2), compositeFunction.apply(2), 1e-6);
        assertEquals(1, compositeFunction.apply(0), 1e-6);
    }

    @Test
    void testCompositeOfCompositeFunctions() {
        MathFunction linearFunction = new IdentityFunction();
        MathFunction sqr = new SqrFunction();
        MathFunction exponentialFunction = new ExponentialFunction();

        CompositeFunction firstComposite = new CompositeFunction(linearFunction, sqr);
        CompositeFunction secondComposite = new CompositeFunction(firstComposite, exponentialFunction);

        assertEquals(Math.exp(1), secondComposite.apply(1), 1e-6);
        assertEquals(Math.exp(4), secondComposite.apply(2), 1e-6);
        assertEquals(1, secondComposite.apply(0), 1e-6);
    }

    @Test
    public void testCompositeFunctionWithTabulated() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};

        LinkedListTabulatedFunction tabulatedFunc = new LinkedListTabulatedFunction(xValues, yValues);

        MathFunction sqrFunction = new SqrFunction();

        CompositeFunction composite = new CompositeFunction(tabulatedFunc, sqrFunction);

        for (int x = 1; x < 6; x++) {
            assertEquals(composite.apply(x), Math.pow(x, 4), 1e-6);
        }
    }

    @Test
    public void testCompositeFunctionWithTwoTabulated() {
        double[] xValues1 = {1, 2, 3, 4, 5};
        double[] yValues1 = {1, 4, 9, 16, 25};

        double[] xValues2 = {1, 4, 9, 16, 25};
        double[] yValues2 = {1, 2, 3, 4, 5};

        LinkedListTabulatedFunction tabulatedFunc1 = new LinkedListTabulatedFunction(xValues1, yValues1);

        ArrayTabulatedFunction tabulatedFunc2 = new ArrayTabulatedFunction(xValues2, yValues2);

        CompositeFunction composite = new CompositeFunction(tabulatedFunc2, tabulatedFunc1);

        for (int x = 1; x < 6; x++) {
            System.out.println(composite.apply(x));
            assertEquals(composite.apply(x), x, 1e-6);
        }
    }

}