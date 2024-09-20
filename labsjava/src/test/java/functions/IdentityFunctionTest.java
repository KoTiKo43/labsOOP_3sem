package functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IdentityFunctionTest {

    private final IdentityFunction identityFunction = new IdentityFunction();

    @Test
    void testApplyWithZero() {
        assertEquals(0, identityFunction.apply(0), "Функция тождественного преобразования должна возвращать 0 для входного значения 0");
    }

    @Test
    void testApplyWithPositiveNumber() {
        assertEquals(5, identityFunction.apply(5), "Функция тождественного преобразования должна возвращать 5 для входного значения 5");
    }

    @Test
    void testApplyWithNegativeNumber() {
        assertEquals(-3.5, identityFunction.apply(-3.5), "Функция тождественного преобразования должна возвращать -3.5 для входного значения -3.5");
    }

    @Test
    void testApplyWithLargeNumber() {
        double largeNumber = 1e15;
        assertEquals(largeNumber, identityFunction.apply(largeNumber), "Функция тождественного преобразования должна работать с большими числами");
    }

    @Test
    void testApplyWithSmallNumber() {
        double smallNumber = 1e-15;
        assertEquals(smallNumber, identityFunction.apply(smallNumber), "Функция тождественного преобразования должна работать с малыми числами");
    }
}