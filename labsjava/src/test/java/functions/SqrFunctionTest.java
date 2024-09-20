package functions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SqrFunctionTest {
    SqrFunction function = new SqrFunction();

    @Test
    public void testPositiveNumber() {
        double input = 5.0;
        double expected = 25.0;
        assertEquals(expected, function.apply(input), "Квадрат положительного числа вычислен неправильно");
    }

    @Test
    public void testNegativeNumber() {
        double input = -4.0;
        double expected = 16.0;
        assertEquals(expected, function.apply(input), "Квадрат отрицательного числа вычислен неправильно");
    }

    @Test
    public void testZeroNumber() {
        double input = 0.0;
        double expected = 0.0;
        assertEquals(expected, function.apply(input), "Квадрат нуля вычислен неправильно");
    }

    @Test
    public void testDecimalNumber() {
        double input = 1.5;
        double expected = 2.25;
        assertEquals(expected, function.apply(input), 0.0001, "Квадрат дробного числа вычислен неправильно");
    }

}