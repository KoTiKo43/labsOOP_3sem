package operations;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.InconsistentFunctionsException;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import org.junit.jupiter.api.Test;
import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.Point;

class TabulatedFunctionOperationServiceTest {
    @Test
    public void testAsPointsArrayTabulatedFunction() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(
                new double[]{1, 2, 3},
                new double[]{10, 20, 30}
        );

        Point[] points = TabulatedFunctionOperationService.asPoints(function);

        assertEquals(function.getCount(), points.length);
        assertEquals(function.getX(0), points[0].x);
        assertEquals(function.getX(1), points[1].x);
        assertEquals(function.getX(2), points[2].x);
        assertEquals(function.getY(0), points[0].y);
        assertEquals(function.getY(1), points[1].y);
        assertEquals(function.getY(2), points[2].y);
    }

    @Test
    public void testAsPointsLinkedListTabulatedFunction() {
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(
                new double[]{1, 2, 3},
                new double[]{10, 20, 30}
        );

        Point[] points = TabulatedFunctionOperationService.asPoints(function);

        assertEquals(function.getCount(), points.length);
        assertEquals(function.getX(0), points[0].x);
        assertEquals(function.getX(1), points[1].x);
        assertEquals(function.getX(2), points[2].x);
        assertEquals(function.getY(0), points[0].y);
        assertEquals(function.getY(1), points[1].y);
        assertEquals(function.getY(2), points[2].y);
    }

    @Test
    public void testInconsistentFunctionSizes() {
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();

        ArrayTabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{10, 20, 30});
        ArrayTabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1, 2}, new double[]{1, 2});

        Exception exception = assertThrows(InconsistentFunctionsException.class, () -> {
            service.add(function1, function2);
        });

        assertEquals("Functions have different number of points", exception.getMessage());
    }

    @Test
    public void testInconsistentXValues() {
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();

        ArrayTabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{10, 20, 30});
        LinkedListTabulatedFunction function2 = new LinkedListTabulatedFunction(new double[]{1, 2, 4}, new double[]{1, 2, 3});

        Exception exception = assertThrows(InconsistentFunctionsException.class, () -> {
            service.add(function1, function2);
        });

        assertEquals("X values of the functions do not match", exception.getMessage());
    }

    @Test
    public void testConstructor() {
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        assertInstanceOf(ArrayTabulatedFunctionFactory.class, service.getFactory(),  "Factory should be ArrayTabulatedFunctionFactory by default");
    }

    @Test
    public void testGetAndSetFactory() {
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        ArrayTabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        LinkedListTabulatedFunctionFactory linkedListFactory = new LinkedListTabulatedFunctionFactory();

        service.setFactory(arrayFactory);
        assertSame(arrayFactory, service.getFactory(), "Factory should be ArrayTabulatedFunctionFactory");

        service.setFactory(linkedListFactory);
        assertSame(linkedListFactory, service.getFactory(), "Factory should be LinkedListTabulatedFunctionFactory");
    }

    @Test
    public void testAddArrayFunctions() {
        ArrayTabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        ArrayTabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{10, 20, 30});
        ArrayTabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3});

        TabulatedFunction result = service.add(function1, function2);

        assertEquals(function1.getY(0) + function2.getY(0), result.getY(0), 1e-5);
        assertEquals(function1.getY(1) + function2.getY(1), result.getY(1), 1e-5);
        assertEquals(function1.getY(2) + function2.getY(2), result.getY(2), 1e-5);
    }

    @Test
    public void testSubtractLinkedListFunctions() {
        LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        LinkedListTabulatedFunction function1 = new LinkedListTabulatedFunction(new double[]{1, 2, 3}, new double[]{10, 20, 30});
        LinkedListTabulatedFunction function2 = new LinkedListTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3});

        TabulatedFunction result = service.subtract(function1, function2);

        assertEquals(function1.getY(0) - function2.getY(0), result.getY(0), 1e-5);
        assertEquals(function1.getY(1) - function2.getY(1), result.getY(1), 1e-5);
        assertEquals(function1.getY(2) - function2.getY(2), result.getY(2), 1e-5);
    }

    @Test
    public void testAddSubtractDifferentFunctions() {
        LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        LinkedListTabulatedFunction function1 = new LinkedListTabulatedFunction(new double[]{1, 2, 3}, new double[]{10, 20, 30});
        ArrayTabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3});

        TabulatedFunction result1 = service.add(function1, function2);
        TabulatedFunction result2 = service.subtract(function1, function2);

        assertEquals(function1.getY(0) + function2.getY(0), result1.getY(0), 1e-5);
        assertEquals(function1.getY(1) + function2.getY(1), result1.getY(1), 1e-5);
        assertEquals(function1.getY(2) + function2.getY(2), result1.getY(2), 1e-5);
        assertEquals(function1.getY(0) - function2.getY(0), result2.getY(0), 1e-5);
        assertEquals(function1.getY(1) - function2.getY(1), result2.getY(1), 1e-5);
        assertEquals(function1.getY(2) - function2.getY(2), result2.getY(2), 1e-5);
    }
}