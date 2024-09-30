package functions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UnitFunctionTest {
    ConstantFunction unitFunction = new UnitFunction();

    @Test
    public void testPositiveNumber() {
        double input = 678.0;
        double result = unitFunction.apply(input);

        assertEquals(1, result);
    }

    @Test
    public void testNegativeNumber() {
        double input = -789.0;
        double result = unitFunction.apply(input);

        assertEquals(1, result);
    }

    @Test
    public void testDecimalNumber() {
        double input = 543.21;
        double result = unitFunction.apply(input);

        assertEquals(1, result);
    }

    @Test
    public void testZero() {
        double input = 0;
        double result = unitFunction.apply(input);

        assertEquals(1, result);
    }

    @Test
    public void testNaN() {
        double input = Double.NaN;
        double result = unitFunction.apply(input);

        assertEquals(1, result);
    }
}