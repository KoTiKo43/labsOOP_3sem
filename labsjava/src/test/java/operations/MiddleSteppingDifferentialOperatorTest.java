package operations;

import functions.MathFunction;
import functions.SqrFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MiddleSteppingDifferentialOperatorTest {
    @Test
    public void testMiddleSteppingDifferentialOperator() {
        SqrFunction function = new SqrFunction();
        MiddleSteppingDifferentialOperator operator = new MiddleSteppingDifferentialOperator(0.001);

        MathFunction derivedFunction = operator.derive(function);

        assertEquals(2 * 3, derivedFunction.apply(3), 0.01);
        assertEquals(2 * 5, derivedFunction.apply(5), 0.01);
    }

    @Test
    public void testInvalidStep() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new MiddleSteppingDifferentialOperator(Double.POSITIVE_INFINITY);
        });

        assertEquals("Step must be a positive real number", exception.getMessage());
    }
}