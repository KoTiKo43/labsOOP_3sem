package functions.factory;

import org.junit.jupiter.api.Test;
import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import static org.junit.jupiter.api.Assertions.*;

public class ArrayTabulatedFunctionFactoryTest {
    @Test
    public void testCreate() {
        ArrayTabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        TabulatedFunction function = factory.create(xValues, yValues);

        assertInstanceOf(ArrayTabulatedFunction.class, function);
    }
}