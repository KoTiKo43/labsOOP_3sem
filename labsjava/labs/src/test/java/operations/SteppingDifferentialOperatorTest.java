package operations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SteppingDifferentialOperatorTest {
    @Test
    void testGetSet() {
        SteppingDifferentialOperator operator = new RightSteppingDifferentialOperator(1e-5);

        assertEquals(1e-5, operator.getStep(), 1e-10);

        operator.setStep(1e-6);
        assertEquals(1e-6, operator.getStep(), 1e-10);

        assertThrows(IllegalArgumentException.class, () -> operator.setStep(-1));
        assertThrows(IllegalArgumentException.class, () -> operator.setStep(Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> operator.setStep(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> {
            new RightSteppingDifferentialOperator(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new RightSteppingDifferentialOperator(Double.POSITIVE_INFINITY);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new RightSteppingDifferentialOperator(Double.NaN);
        });
    }
}