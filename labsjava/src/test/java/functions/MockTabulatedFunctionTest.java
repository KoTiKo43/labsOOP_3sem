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
}