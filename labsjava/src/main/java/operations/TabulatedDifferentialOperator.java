package operations;

import functions.Point;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import functions.TabulatedFunction;

public class TabulatedDifferentialOperator implements DifferentialOperator<TabulatedFunction> {
    private TabulatedFunctionFactory factory;

    public TabulatedDifferentialOperator(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedDifferentialOperator(){
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    @Override
    public TabulatedFunction derive(TabulatedFunction function) {
        Point[] points = TabulatedFunctionOperationService.asPoints(function);

        int n = points.length;
        double[] xValues = new double[n];
        double[] yValues = new double[n];

        for (int k = 0; k < n - 1; k++) {
            xValues[k] = points[k].x;
            yValues[k] = (points[k + 1].y - points[k].y) / (points[k + 1].x - points[k].x);
        }
        xValues[n - 1] = points[n - 1].x;
        yValues[n - 1] = yValues[n - 2];

        return factory.create(xValues, yValues);
    }
}
