package operations;

import exceptions.InconsistentFunctionsException;
import functions.TabulatedFunction;
import functions.Point;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.function.BinaryOperator;

@AllArgsConstructor
@Getter
@Setter
public class TabulatedFunctionOperationService {
    private TabulatedFunctionFactory factory;

    public TabulatedFunctionOperationService() {
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    @SuppressWarnings("Convert2MethodRef")
    public TabulatedFunction add(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (u,v) -> u + v);
    }

    public TabulatedFunction subtract(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (u,v) -> u - v);
    }

    public TabulatedFunction multiply(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (u,v) -> u * v);
    }

    public TabulatedFunction divide(TabulatedFunction a, TabulatedFunction b){
        return doOperation(a, b, (u, v) -> u / v);
    }

    private TabulatedFunction doOperation(TabulatedFunction a, TabulatedFunction b, BinaryOperator<Double> operation) {
        if (a.getCount() != b.getCount()) {
            throw new InconsistentFunctionsException("Functions have different number of points");
        }

        Point[] pointsA = asPoints(a);
        Point[] pointsB = asPoints(b);

        double[] xValues = new double[a.getCount()];
        double[] yValues = new double[a.getCount()];

        for (int i = 0; i < a.getCount(); i++) {
            if (pointsA[i].x != pointsB[i].x) {
                throw new InconsistentFunctionsException("X values of the functions do not match");
            }

            xValues[i] = pointsA[i].x;
            yValues[i] = operation.apply(pointsA[i].y, pointsB[i].y);
        }

        return factory.create(xValues, yValues);
    }

    public static Point[] asPoints(TabulatedFunction tabulatedFunction) {
        Point[] points = new Point[tabulatedFunction.getCount()];
        int i = 0;

        for (Point point: tabulatedFunction) {
            points[i] = point;
            i++;
        }
        return points;
    }
}
