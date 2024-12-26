package functions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SimpleIterationsTest {
    private static class GFunction implements MathFunction {
        @Override
        public double apply(double x) {
            return Math.cos(x);
        }
    }

    @Test
    public void testCorrectRoot() {
        MathFunction gFunction = new GFunction();
        SimpleIterations simpleIterations = new SimpleIterations(gFunction, 100, 1e-5);

        double root = simpleIterations.findRoot(0.5);
        assertEquals(0.739085, root, 1e-5); // Ожидаемый корень cos(x) = x
    }

    @Test
    public void testMaxIterations() {
        MathFunction gFunction = new GFunction();
        SimpleIterations simpleIterations = new SimpleIterations(gFunction, 5, 1e-5);

        double root = simpleIterations.findRoot(0.5);
        assertEquals(Double.NaN, root); // Должен вернуть NaN, если достигнуто максимум итераций
    }

    @Test
    public void testCorrectWithErrorRate() {
        MathFunction gFunction = new GFunction();
        SimpleIterations simpleIterations = new SimpleIterations(gFunction, 100, 1e-5);

        double root = simpleIterations.findRoot(0.5);
        assertTrue(Math.abs(root - 0.739085) <= 1e-5); // Проверка на допустимую погрешность
    }

    @Test
    public void testNoRoot() {
        MathFunction gFunction = x -> x + 1; // Функция, у которой нет корней, g(x) = x + 1
        SimpleIterations simpleIterations = new SimpleIterations(gFunction, 100, 1e-5);

        double root = simpleIterations.findRoot(0);
        assertEquals(Double.NaN, root);
    }

    @Test
    public void testLargeMaxIterations() {
        MathFunction gFunction = x -> 1 + x / 2;
        SimpleIterations simpleIterations = new SimpleIterations(gFunction, 1000, 1e-6);

        double root = simpleIterations.findRoot(0);
        assertEquals(2, root, 1e-6);
    }
}