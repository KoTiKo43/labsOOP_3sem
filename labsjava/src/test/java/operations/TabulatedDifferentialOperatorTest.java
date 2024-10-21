package operations;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import functions.TabulatedFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TabulatedDifferentialOperatorTest {

    @Test
    public void testConstructorWithFactory() {
        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(factory);
        assertEquals(factory, operator.getFactory());
    }

    @Test
    public void testDefaultConstructor() {
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();
        assertInstanceOf(ArrayTabulatedFunctionFactory.class, operator.getFactory());
    }


    @Test
    void testGetFactoryTest() {
        LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(factory);
        assertEquals(factory, operator.getFactory());
    }

    @Test
    void testSetFactory() {
        LinkedListTabulatedFunctionFactory factory1 = new LinkedListTabulatedFunctionFactory();
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(factory1);
        TabulatedFunctionFactory factory2 = new ArrayTabulatedFunctionFactory();
        operator.setFactory(factory2);
        assertEquals(factory2, operator.getFactory());
    }

    @Test
    public void testDeriveWithArrayFactory() {
        double[] xValues = {0, 0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75, 2, 2.25, 2.5, 2.75, 3};
        double[] yValues = {0, 0.0625, 0.25, 0.5625, 1, 1.5625, 2.25, 3.0625, 4, 5.0625, 6.25, 7.5625, 9};
        TabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();

        TabulatedFunction derivedFunction = operator.derive(function);

        assertInstanceOf(ArrayTabulatedFunction.class, derivedFunction);
        assertEquals(2.25, derivedFunction.apply(1), 1e-6);
        assertEquals(3.25, derivedFunction.apply(1.5), 1e-6);
        assertEquals(4.25, derivedFunction.apply(2), 1e-6);
        assertEquals(5.75, derivedFunction.apply(3), 1e-6);
    }

    @Test
    public void testDeriveWithLinkedListFactory() {
        double[] xValues = {0, 0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75, 2, 2.25, 2.5, 2.75, 3};
        double[] yValues = {0, 0.0625, 0.25, 0.5625, 1, 1.5625, 2.25, 3.0625, 4, 5.0625, 6.25, 7.5625, 9};
        TabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());

        TabulatedFunction derivedFunction = operator.derive(function);

        assertInstanceOf(LinkedListTabulatedFunction.class, derivedFunction);
        assertEquals(2.25, derivedFunction.apply(1), 1e-6);
        assertEquals(3.25, derivedFunction.apply(1.5), 1e-6);
        assertEquals(4.25, derivedFunction.apply(2), 1e-6);
        assertEquals(5.75, derivedFunction.apply(3), 1e-6);
    }
}