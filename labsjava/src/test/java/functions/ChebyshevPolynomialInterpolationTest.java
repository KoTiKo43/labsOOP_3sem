package functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChebyshevPolynomialInterpolationTest {

    @Test
    public void testInterpolationWithSin() {
        MathFunction function = Math::sin;
        ChebyshevPolynomialInterpolation interpolation = new ChebyshevPolynomialInterpolation(function, -Math.PI, Math.PI, 15);

        double[] testNodes = {-Math.PI, -Math.PI / 2, 0, Math.PI / 2, Math.PI};
        for (double node : testNodes) {
            assertEquals(function.apply(node), interpolation.apply(node), 1e-4);
        }
    }

    @Test
    public void testInterpolationWithCos() {
        MathFunction function = Math::cos;
        ChebyshevPolynomialInterpolation interpolation = new ChebyshevPolynomialInterpolation(function, -Math.PI, Math.PI, 15);

        double[] testNodes = {-Math.PI, -Math.PI / 2, 0, Math.PI / 2, Math.PI};
        for (double node : testNodes) {
            assertEquals(function.apply(node), interpolation.apply(node), 1e-4);
        }
    }

    @Test
    public void testInterpolationWithExponentialFunction() {
        MathFunction function = Math::exp;
        ChebyshevPolynomialInterpolation interpolation = new ChebyshevPolynomialInterpolation(function, -1, 1, 15);

        double[] testNodes = {-1, -0.5, 0, 0.5, 1};
        for (double node : testNodes) {
            assertEquals(function.apply(node), interpolation.apply(node), 1e-4);
        }
    }

    @Test
    public void testInterpolationWithSqrFunction() {
        MathFunction function = new SqrFunction();
        ChebyshevPolynomialInterpolation interpolation = new ChebyshevPolynomialInterpolation(function, -1, 1, 15);

        double[] testNodes = {-1, -0.5, 0, 0.5, 1};
        for (double node : testNodes) {
            assertEquals(function.apply(node), interpolation.apply(node), 1e-4);
        }
    }

    @Test
    public void testInterpolationAtSmallValue() {
        MathFunction function = new SqrFunction();
        ChebyshevPolynomialInterpolation interpolation = new ChebyshevPolynomialInterpolation(function, -1, 1, 15);

        double smallValue = 1e-10;
        assertEquals(function.apply(smallValue), interpolation.apply(smallValue), 1e-4);
    }

    @Test
    public void testInterpolationAtLargeValue() {
        MathFunction function = new SqrFunction();
        ChebyshevPolynomialInterpolation interpolation = new ChebyshevPolynomialInterpolation(function, -1, 1, 15);

        double largeValue = 1e10;
        assertNotEquals(function.apply(largeValue), interpolation.apply(largeValue));

        assertNotEquals(function.apply(-largeValue), interpolation.apply(largeValue), 1e-4);

    }
}