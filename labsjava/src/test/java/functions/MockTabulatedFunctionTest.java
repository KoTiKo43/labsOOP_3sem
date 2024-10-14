package functions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MockTabulatedFunctionTest {
    MockTabulatedFunction mockTabulatedFunction = new MockTabulatedFunction(1.0, 5.0, 2.0, 10.0);

    @Test
    public void testApply() {
        assertEquals(2.0, mockTabulatedFunction.apply(1), 1e-5);
        assertEquals(10.0, mockTabulatedFunction.apply(5), 1e-5);
        assertEquals(6.0, mockTabulatedFunction.apply(3), 1e-5);
    }

    @Test
    public void testApplyExtrapolationLeft() {
        assertEquals(0.0, mockTabulatedFunction.apply(0.0), 1e-5);
    }

    @Test
    public void testApplyExtrapolationRight() {
        assertEquals(12.0, mockTabulatedFunction.apply(6.0), 1e-5);
    }

    @Test
    public void testInterpolate() {
        double result = mockTabulatedFunction.interpolate(3.0, 1.0, 5.0, 2.0, 10.0);

        assertEquals(6.0, result, 1e-5);
    }

    @Test
    public void testSetY() {
        double expected = 100;
        mockTabulatedFunction.setY(0, expected);

        assertEquals(expected, mockTabulatedFunction.getY(0), 1e-5);

        double expected2 = 101;
        mockTabulatedFunction.setY(2, expected2);

        assertEquals(expected2, mockTabulatedFunction.getY(1), 1e-5);
    }

    @Test
    public void testIndexOfY() {
        int result1 = mockTabulatedFunction.indexOfY(2.0);
        int result2 = mockTabulatedFunction.indexOfY(10.0);
        int result3 = mockTabulatedFunction.indexOfY(5.0);

        assertEquals(0, result1);
        assertEquals(1, result2);
        assertEquals(-1, result3);
    }

    @Test
    public void testGetY() {
        double expected1 = 2.0;
        double expected2 = 10.0;
        double expected3 = -1;

        assertEquals(expected1, mockTabulatedFunction.getY(0));
        assertEquals(expected2, mockTabulatedFunction.getY(1));
        assertEquals(expected3, mockTabulatedFunction.getY(3));
    }

    @Test
    public void testGetX() {
        double expected1 = 1.0;
        double expected2 = 5.0;
        double expected3 = -1;

        assertEquals(expected1, mockTabulatedFunction.getX(0));
        assertEquals(expected2, mockTabulatedFunction.getX(1));
        assertEquals(expected3, mockTabulatedFunction.getX(3));
    }

    @Test
    public void testGetCount() {
        assertEquals(2, mockTabulatedFunction.getCount());
    }
}