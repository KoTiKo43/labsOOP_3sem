package functions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ConstantFunctionTest {
    double constNum = 20;
    ConstantFunction constantFunction = new ConstantFunction(constNum);

    @Test
    public void testPositiveNumber() {
        double input = 678.0;
        double result = constantFunction.apply(input);

        assertEquals(constNum, result);
    }

    @Test
    public void testNegativeNumber() {
        double input = -789.0;
        double result = constantFunction.apply(input);

        assertEquals(constNum, result);
    }

    @Test
    public void testDecimalNumber() {
        double input = 543.21;
        double result = constantFunction.apply(input);

        assertEquals(constNum, result);
    }

    @Test
    public void testZero() {
        double input = 0;
        double result = constantFunction.apply(input);

        assertEquals(constNum, result);
    }

    @Test
    public void testNaN() {
        double input = Double.NaN;
        double result = constantFunction.apply(input);

        assertEquals(constNum, result);
    }

    @Test
    public void testGetNum() {
        double num;
        num = constantFunction.getConstNum();

        assertEquals(constNum, num);
    }
}