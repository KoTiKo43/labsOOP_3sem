package operations;

import static org.junit.jupiter.api.Assertions.*;
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
}