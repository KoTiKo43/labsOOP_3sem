package concurrent;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class SynchronizedTabulatedFunctionTest {

    SynchronizedTabulatedFunction functionArray;
    SynchronizedTabulatedFunction functionList;

    @BeforeEach
    void init() {
        double[] xValues = new double[]{1, 2, 3, 4, 5};
        double[] yValues = new double[]{2, 4, 6, 8, 10};

        ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(xValues, yValues);
        LinkedListTabulatedFunction linkedListTabulatedFunction = new LinkedListTabulatedFunction(xValues, yValues);

        functionArray = new SynchronizedTabulatedFunction(arrayTabulatedFunction);
        functionList = new SynchronizedTabulatedFunction(linkedListTabulatedFunction);
    }

    @Test
    void testGetCountSingleThread() {
        assertEquals(5, functionArray.getCount());
        assertEquals(5, functionList.getCount());
    }

    @Test
    void testGettersSingleThread() {
        assertEquals(5, functionArray.getX(4), 1e-6);
        assertEquals(5, functionList.getX(4), 1e-6);
        assertEquals(10, functionArray.getY(4), 1e-6);
        assertEquals(10, functionList.getY(4), 1e-6);

        assertEquals(1, functionArray.leftBound(), 1e-6);
        assertEquals(1, functionList.leftBound(), 1e-6);
        assertEquals(5, functionArray.rightBound(), 1e-6);
        assertEquals(5, functionList.rightBound(), 1e-6);

        assertEquals(1, functionArray.indexOfX(2));
        assertEquals(1, functionList.indexOfX(2));
        assertEquals(-1, functionArray.indexOfX(100));
        assertEquals(-1, functionList.indexOfX(100));
        assertEquals(1, functionArray.indexOfY(4));
        assertEquals(1, functionList.indexOfY(4));
        assertEquals(-1, functionArray.indexOfY(11));
        assertEquals(-1, functionList.indexOfY(11));

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> functionArray.getX(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> functionArray.getX(5));
        assertThrows(IllegalArgumentException.class, () -> functionList.getX(-1));
        assertThrows(IllegalArgumentException.class, () -> functionList.getX(5));

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> functionArray.getY(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> functionArray.getY(5));
        assertThrows(IllegalArgumentException.class, () -> functionList.getY(-1));
        assertThrows(IllegalArgumentException.class, () -> functionList.getY(5));
    }

    @Test
    void testSettersSingleThread() {
        assertDoesNotThrow(() -> functionArray.setY(2, 1.5));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> functionArray.setY(5, 1.5));

        functionArray.setY(3, 2.5);
        assertEquals(2.5, functionArray.getY(3), 1e-6);

        functionList.setY(0, 1.5);
        assertEquals(1.5, functionList.getY(0),1e-6);

        assertThrows(IllegalArgumentException.class, () -> functionList.setY(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> functionList.setY(5, 0));
    }

    @Test
    void testApplySingleThread() {
        assertEquals(2, functionArray.apply(1), 1e-6);
        assertEquals(12, functionArray.apply(6), 1e-6);
        assertEquals(0, functionArray.apply(0), 1e-6);

        assertEquals(2, functionList.apply(1), 1e-6);
        assertEquals(12, functionList.apply(6), 1e-6);
        assertEquals(0, functionList.apply(0), 1e-6);
    }

    @Test
    void testIteratorSingleThread() {
        Iterator<Point> iterator = functionArray.iterator();
        int i = 0;

        while (iterator.hasNext()) {
            Point point = iterator.next();
            assertEquals(functionArray.getX(i), point.x, 1e-6);
            i += 1;
        }
    }
}