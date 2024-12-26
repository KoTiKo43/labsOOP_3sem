package functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class UnmodifiableTabulatedFunctionTest {
    private TabulatedFunction arrayFunction;
    private TabulatedFunction linkedListFunction;
    private UnmodifiableTabulatedFunction unmodifiableArrayFunction;
    private UnmodifiableTabulatedFunction unmodifiableLinkedListFunction;

    @BeforeEach
    public void init() {
        // Создаем исходные функции
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {10, 20, 30, 40, 50};

        arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        linkedListFunction = new LinkedListTabulatedFunction(xValues, yValues);

        // Оборачиваем функции в UnmodifiableTabulatedFunction
        unmodifiableArrayFunction = new UnmodifiableTabulatedFunction(arrayFunction);
        unmodifiableLinkedListFunction = new UnmodifiableTabulatedFunction(linkedListFunction);
    }

    @Test
    public void testGetCount() {
        assertEquals(arrayFunction.getCount(), unmodifiableArrayFunction.getCount());
        assertEquals(linkedListFunction.getCount(), unmodifiableLinkedListFunction.getCount());
    }

    @Test
    public void testGetX() {
        for (int i = 0; i < arrayFunction.getCount(); i++) {
            assertEquals(arrayFunction.getX(i), unmodifiableArrayFunction.getX(i));
            assertEquals(linkedListFunction.getX(i), unmodifiableLinkedListFunction.getX(i));
        }
    }

    @Test
    public void testGetY() {
        for (int i = 0; i < arrayFunction.getCount(); i++) {
            assertEquals(arrayFunction.getY(i), unmodifiableArrayFunction.getY(i));
            assertEquals(linkedListFunction.getY(i), unmodifiableLinkedListFunction.getY(i));
        }
    }

    @Test
    public void testSetYThrowsException() {
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableArrayFunction.setY(0, 100));
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableLinkedListFunction.setY(0, 100));
    }

    @Test
    public void testIndexOfX() {
        for (int i = 0; i < arrayFunction.getCount(); i++) {
            assertEquals(arrayFunction.indexOfX(arrayFunction.getX(i)), unmodifiableArrayFunction.indexOfX(arrayFunction.getX(i)));
            assertEquals(linkedListFunction.indexOfX(linkedListFunction.getX(i)), unmodifiableLinkedListFunction.indexOfX(linkedListFunction.getX(i)));
        }
    }

    @Test
    public void testIndexOfY() {
        for (int i = 0; i < arrayFunction.getCount(); i++) {
            assertEquals(arrayFunction.indexOfY(arrayFunction.getY(i)), unmodifiableArrayFunction.indexOfY(arrayFunction.getY(i)));
            assertEquals(linkedListFunction.indexOfY(linkedListFunction.getY(i)), unmodifiableLinkedListFunction.indexOfY(linkedListFunction.getY(i)));
        }
    }

    @Test
    public void testLeftBound() {
        assertEquals(arrayFunction.leftBound(), unmodifiableArrayFunction.leftBound());
        assertEquals(linkedListFunction.leftBound(), unmodifiableLinkedListFunction.leftBound());
    }

    @Test
    public void testRightBound() {
        assertEquals(arrayFunction.rightBound(), unmodifiableArrayFunction.rightBound());
        assertEquals(linkedListFunction.rightBound(), unmodifiableLinkedListFunction.rightBound());
    }

    @Test
    public void testApply() {
        for (int i = 0; i < arrayFunction.getCount(); i++) {
            double x = arrayFunction.getX(i);
            assertEquals(arrayFunction.apply(x), unmodifiableArrayFunction.apply(x), 1e-6);
            assertEquals(linkedListFunction.apply(x), unmodifiableLinkedListFunction.apply(x), 1e-6);
        }
    }

    @Test
    public void testIterator() {
        Iterator<Point> arrayIterator = unmodifiableArrayFunction.iterator();
        Iterator<Point> linkedListIterator = unmodifiableLinkedListFunction.iterator();

        for (Point point : arrayFunction) {
            assertTrue(arrayIterator.hasNext());
            Point unmodifiablePoint = arrayIterator.next();
            assertEquals(point.x, unmodifiablePoint.x, 1e-6);
            assertEquals(point.y, unmodifiablePoint.y, 1e-6);
        }
        assertFalse(arrayIterator.hasNext());

        for (Point point : linkedListFunction) {
            assertTrue(linkedListIterator.hasNext());
            Point unmodifiablePoint = linkedListIterator.next();
            assertEquals(point.x, unmodifiablePoint.x, 1e-6);
            assertEquals(point.y, unmodifiablePoint.y, 1e-6);
        }
        assertFalse(linkedListIterator.hasNext());
    }
}