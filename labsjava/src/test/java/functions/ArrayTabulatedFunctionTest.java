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
        assertThrows(IllegalArgumentException.class, () -> new ArrayTabulatedFunction(new double[]{1}, new double[]{1}));

        MathFunction func = x -> x * 2;
        ArrayTabulatedFunction arrayTabulatedFunctionMath = new ArrayTabulatedFunction(func, 0, 4, 5);

        assertEquals(5, arrayTabulatedFunctionMath.getCount());
        assertEquals(0., arrayTabulatedFunctionMath.getX(0));
        assertEquals(8., arrayTabulatedFunctionMath.getY(4));
        assertThrows(IllegalArgumentException.class, () -> new ArrayTabulatedFunction(func, 0, 5, 1));
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

    @Test
    public void testApply() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0, 10.0};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(2.0, function.apply(1.0), 1e-5);
        assertEquals(4.0, function.apply(2.0), 1e-5);
        assertEquals(6.0, function.apply(3.0), 1e-5);
        assertEquals(8.0, function.apply(4.0), 1e-5);
        assertEquals(10.0, function.apply(5.0), 1e-5);

        assertEquals(3.0, function.apply(1.5), 1e-5); // Между 1 и 2
        assertEquals(7.0, function.apply(3.5), 1e-5); // Между 3 и 4

        assertEquals(0.0, function.apply(0.0), 1e-5);  // Левее всех значений x
        assertEquals(12.0, function.apply(6.0), 1e-5); // Правее всех значений x
    }

    @Test
    public void testInsertion() {
        double[] xValues = {1, 2, 4, 5};
        double[] yValues = {1, 4, 16, 25};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.insert(2, 5);
        assertEquals(5, function.getY(function.indexOfX(2)), 1e-6);

        function.insert(3, 9);
        assertEquals(3, function.getX(function.indexOfX(3)), 1e-6);
        assertEquals(9, function.getY(function.indexOfX(3)), 1e-6);
        assertEquals(5, function.getCount());

        function.insert(0, 0);
        assertEquals(0, function.getX(function.indexOfX(0)), 1e-6);
        assertEquals(0, function.getY(function.indexOfX(0)), 1e-6);
        assertEquals(6, function.getCount());

        function.insert(6, 36);
        assertEquals(6, function.getX(function.indexOfX(6)), 1e-6);
        assertEquals(36, function.getY(function.indexOfX(6)), 1e-6);
        assertEquals(7, function.getCount());
    }

    @Test
    public void testRemoveMiddle() {
        double[] xValues = {1., 2., 3.};
        double[] yValues = {2., 4., 6.};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.remove(1);
        assertEquals(3., function.getX(1), 1e-5);
        assertEquals(6., function.getY(1), 1e-5);
        assertEquals(2, function.getCount());
    }

    @Test
    public void testRemoveFirst() {
        double[] xValues = {1., 2., 3.};
        double[] yValues = {2., 4., 6.};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.remove(0);
        assertEquals(2., function.getX(0), 1e-5);
        assertEquals(4., function.getY(0), 1e-5);
        assertEquals(2, function.getCount());
    }

    @Test
    public void testRemoveLast() {
        double[] xValues = {1., 2., 3.};
        double[] yValues = {2., 4., 6.};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.remove(2);
        assertEquals(2., function.getX(function.getCount() - 1), 1e-5);
        assertEquals(4., function.getY(function.getCount() - 1), 1e-5);
        assertEquals(2, function.getCount());
    }

    @Test
    public void testRemoveInvalidIndex() {
        double[] xValues = {1., 2., 3.};
        double[] yValues = {2., 4., 6.};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        int originalCount = function.getCount();

        function.remove(-1);
        assertEquals(originalCount, function.getCount());

        function.remove(originalCount);
        assertEquals(originalCount, function.getCount());
    }
}
