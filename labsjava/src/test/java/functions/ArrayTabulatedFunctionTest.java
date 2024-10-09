package functions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayTabulatedFunctionTest {
    @Test
    public void testGet() {
        double[] xValues = {1., 2., 3.};
        double[] yValues = {2., 4., 6.};
        ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(1., arrayTabulatedFunction.getX(0));
        assertEquals(4., arrayTabulatedFunction.getY(1));
    }

    @Test
    public void testSetY() {
        double[] xValues = {1., 2., 3.};
        double[] yValues = {2., 4., 6.};
        ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(xValues, yValues);

        arrayTabulatedFunction.setY(0, 10.);
        assertEquals(10., arrayTabulatedFunction.getY(0));
    }

    @Test
    public void testConstructors() {
        double[] xValues = {1., 2., 3.};
        double[] yValues = {2., 4., 6.};
        ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(3, arrayTabulatedFunction.getCount());
        assertEquals(1., arrayTabulatedFunction.getX(0));
        assertEquals(2., arrayTabulatedFunction.getY(0));
        assertEquals(3., arrayTabulatedFunction.getX(2));
        assertEquals(6., arrayTabulatedFunction.getY(2));

        MathFunction func = x -> x * 2;
        ArrayTabulatedFunction arrayTabulatedFunctionMath = new ArrayTabulatedFunction(func, 0, 4, 5);

        assertEquals(5, arrayTabulatedFunctionMath.getCount());
        assertEquals(0., arrayTabulatedFunctionMath.getX(0));
        assertEquals(8., arrayTabulatedFunctionMath.getY(4));
    }

    @Test
    public void testBounds() {
        double[] xValues = {1., 2., 3.};
        double[] yValues = {2., 4., 6.};
        ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(1., arrayTabulatedFunction.leftBound());
        assertEquals(3., arrayTabulatedFunction.rightBound());
    }

    @Test
    public void testIndexOf() {
        double[] xValues = {1., 2., 3.};
        double[] yValues = {2., 4., 6.};
        ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(1, arrayTabulatedFunction.indexOfX(2.));
        assertEquals(2, arrayTabulatedFunction.indexOfY(6.));
    }

    @Test
    public void testExtrapolate() {
        MathFunction func = x -> x * 2;
        ArrayTabulatedFunction arrayTabulatedFunctionMath = new ArrayTabulatedFunction(func, 0, 4, 5);

        assertEquals(-2.0, arrayTabulatedFunctionMath.extrapolateLeft(-1));
        assertEquals(10.0, arrayTabulatedFunctionMath.extrapolateRight(5));
    }

    @Test
    public void testInterpolate() {
        MathFunction func = x -> x * 2;
        ArrayTabulatedFunction arrayTabulatedFunctionMath = new ArrayTabulatedFunction(func, 0, 4, 5);

        assertEquals(6.0, arrayTabulatedFunctionMath.interpolate(3.0, 2));
    }
}
