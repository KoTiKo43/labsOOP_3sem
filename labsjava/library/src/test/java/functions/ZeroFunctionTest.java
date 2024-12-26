package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZeroFunctionTest {
    ZeroFunction zeroFunction = new ZeroFunction();

    @Test
    public void testPositiveNumber() {
        double input = 678.0;
        double result = zeroFunction.apply(input);

        assertEquals(0, result);
    }

    @Test
    public void testNegativeNumber() {
        double input = -789.0;
        double result = zeroFunction.apply(input);

        assertEquals(0, result);
    }

    @Test
    public void testDecimalNumber() {
        double input = 543.21;
        double result = zeroFunction.apply(input);

        assertEquals(0, result);
    }

    @Test
    public void testZero() {
        double input = 0;
        double result = zeroFunction.apply(input);

        assertEquals(0, result);
    }

    @Test
    public void testNaN() {
        double input = Double.NaN;
        double result = zeroFunction.apply(input);

        assertEquals(0, result);
    }
}