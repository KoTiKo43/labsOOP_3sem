package functions;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import exceptions.InterpolationException;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTabulatedFunctionTest {

    @Test
    public void testConstructorWithArrays() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertDoesNotThrow(()->{
            new LinkedListTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{2.0, 4.0, 6.0});
        });
        assertThrows(DifferentLengthOfArraysException.class, ()->{
            new LinkedListTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{2.0, 4.0});
        });
        assertThrows(DifferentLengthOfArraysException.class, ()->{
            new LinkedListTabulatedFunction(new double[]{1.0, 2.0}, new double[]{2.0, 4.0, 6.0});
        });

        assertThrows(ArrayIsNotSortedException.class, ()->{
            new LinkedListTabulatedFunction(new double[]{2.0, 1.0, 3.0}, new double[]{2.0, 4.0, 6.0});
        });

        assertEquals(5, function.getCount());
        assertEquals(1, function.getX(0));
        assertEquals(25, function.getY(4));
        assertThrows(IllegalArgumentException.class, () -> new LinkedListTabulatedFunction(new double[]{1}, new double[]{1}));
    }

    @Test
    public void testConstructorWithMathFunction() {
        MathFunction sqrFunction = new SqrFunction();
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(sqrFunction, 1, 5, 5);

        assertEquals(5, function.getCount());
        assertEquals(1, function.getX(0));
        assertEquals(25, function.getY(4));
        assertThrows(IllegalArgumentException.class, () -> new LinkedListTabulatedFunction(sqrFunction, 0, 5, 1));
    }

    @Test
    public void testGetX() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(3, function.getX(2));
        assertThrows(IllegalArgumentException.class, () -> function.getX(-1));
    }

    @Test
    public void testGetY() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(16, function.getY(3));
        assertThrows(IllegalArgumentException.class, () -> function.getX(-1));
    }

    @Test
    public void testSetY() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.setY(2, 10);
        assertEquals(10, function.getY(2));
        assertThrows(IllegalArgumentException.class, () -> function.getX(-1));
    }

    @Test
    public void testIndexOfX() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(2, function.indexOfX(3));
        assertEquals(-1, function.indexOfX(6));
    }

    @Test
    public void testIndexOfY() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(3, function.indexOfY(16));
        assertEquals(-1, function.indexOfY(30));
    }

    @Test
    public void testFloorIndexOfX() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(2, function.floorIndexOfX(3.5));
        assertThrows(IllegalArgumentException.class, () -> function.floorIndexOfX(0.5));
    }

    @Test
    public void testInterpolate() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertThrows(InterpolationException.class, ()->{
            function.interpolate(5.0, 4);
        });

        assertThrows(InterpolationException.class, ()->{
            function.interpolate(1.0, 0);
        });

        assertEquals(6.5, function.interpolate(2.5, 2, 3, 4, 9), 1e-6);
    }

    @Test
    public void testExtrapolateLeft() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(-2, function.extrapolateLeft(0), 1e-6);
    }

    @Test
    public void testExtrapolateRight() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(34, function.extrapolateRight(6), 1e-6);
    }

    @Test
    public void testApply() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(9, function.apply(3), 1e-6);
        assertEquals(6.5, function.apply(2.5), 1e-6);
        assertEquals(-2, function.apply(0), 1e-6);
        assertEquals(34, function.apply(6), 1e-6);
    }

    @Test
    public void testInsertionReplace() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.insert(3, 10);
        assertEquals(10, function.getY(function.indexOfX(3)), 1e-6);
    }

    @Test
    public void testInsertionBetween() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.insert(3.5, 12.25);
        assertEquals(3.5, function.getX(3), 1e-6);
        assertEquals(12.25, function.getY(3), 1e-6);
        assertEquals(6, function.getCount());
    }

    @Test
    public void testInsertionFirst() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.insert(0, 0);
        assertEquals(0, function.getX(0), 1e-6);
        assertEquals(0, function.getY(0), 1e-6);
        assertEquals(6, function.getCount());
    }

    @Test
    public void testInsertionLast() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.insert(6, 36);
        assertEquals(6, function.getX(function.getCount() - 1), 1e-6);
        assertEquals(36, function.getY(function.getCount() - 1), 1e-6);
        assertEquals(6, function.getCount());
    }

    @Test
    public void testRemove() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.remove(2);
        assertEquals(4, function.getCount());
        assertEquals(4, function.getX(2), 1e-6);

        assertThrows(IllegalArgumentException.class, () -> function.remove(-1));

        function.remove(0);
        assertEquals(3, function.getCount());
        assertEquals(2, function.getX(0), 1e-6);

        function.remove(2);
        assertEquals(2, function.getCount());
        assertEquals(4, function.getX(1), 1e-6);
        function.remove(1);
        function.remove(0);
        assertThrows(IllegalArgumentException.class, () -> function.remove(0));
    }

    @Test
    public void testIteratorForEach() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);
        int i = 0;
        for (Point point : function) {
            assertEquals(xValues[i], point.x, 1e-6);
            assertEquals(yValues[i], point.y, 1e-6);
            i++;
        }
    }

    @Test
    public void testIteratorWhile() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);
        Iterator<Point> iterator = function.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            assertEquals(xValues[i], point.x, 1e-6);
            assertEquals(yValues[i], point.y, 1e-6);
            i++;
        }
        assertThrows(NoSuchElementException.class, iterator::next);
    }

}