package functions.factory;

import org.junit.jupiter.api.Test;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTabulatedFunctionFactoryTest {
    @Test
    public void testCreate() {
        LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};
        TabulatedFunction function = factory.create(xValues, yValues);

        assertInstanceOf(LinkedListTabulatedFunction.class, function);
    }
}