package functions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AndThenMethodTest {

    @Test
    public void testSimpleChain() {
        MathFunction f = x -> 2 * x + 3;
        MathFunction g = x -> 3 * x;
        MathFunction h = x -> x - 3;

        MathFunction compositeFunc = f.andThen(g).andThen(h);
        double result = compositeFunc.apply(5);
        double expected = 36;
        assertEquals(expected, result, 0.0001);
    }

    @Test
    public void testSingleFunction() {
        MathFunction f = x -> x * x; // f(x) = x^2

        MathFunction compositeFunc = f.andThen(f); // f(f(x)) = (x^2)^2 = x^4
        double result = compositeFunc.apply(5);
        double expected = 625;
        assertEquals(expected, result, 0.0001);
    }

    @Test
    public void testMultipleFunction() {
        MathFunction f = x -> 2 * x + 3;
        MathFunction g = x -> 3 * x;
        MathFunction h = x -> x - 3;

        MathFunction compositeFunc1 = f.andThen(g);
        double result1 = compositeFunc1.apply(3);
        double expected1 = 27;
        assertEquals(expected1, result1, 0.0001);

        MathFunction compositeFunc2 = g.andThen(h);
        double result2 = compositeFunc2.apply(5);
        double expected2 = 12;
        assertEquals(expected2, result2, 0.0001);
    }
}
