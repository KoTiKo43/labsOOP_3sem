package operations;

import static org.junit.jupiter.api.Assertions.*;

import functions.MathFunction;
import functions.SqrFunction;
import org.junit.jupiter.api.Test;

class LeftSteppingDifferentialOperatorTest {
    @Test
    public void testLeftSteppingDifferentialOperator() {
        SqrFunction function = new SqrFunction();
        LeftSteppingDifferentialOperator operator = new LeftSteppingDifferentialOperator(0.001);

        MathFunction derivedFunction = operator.derive(function);

        assertEquals(2 * 3, derivedFunction.apply(3), 0.01);
        assertEquals(2 * 5, derivedFunction.apply(5), 0.01);
    }

    @Test
    public void testInvalidStep() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new LeftSteppingDifferentialOperator(-1);
        });

        assertEquals("Step must be a positive real number", exception.getMessage());
    }
}
