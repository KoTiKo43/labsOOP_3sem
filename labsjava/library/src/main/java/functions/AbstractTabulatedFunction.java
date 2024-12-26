package functions;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;

import java.io.Serial;
import java.io.Serializable;

abstract public class AbstractTabulatedFunction implements TabulatedFunction, Serializable {
    @Serial
    private static final long serialVersionUID = -6984715236368532189L;
    protected int count;

    protected abstract int floorIndexOfX(double x);

    protected abstract double extrapolateLeft(double x);

    protected abstract double extrapolateRight(double x);

    protected abstract double interpolate(double x, int floorIndex);

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (rightY - leftY) / (rightX - leftX) * (x - leftX);
    }

    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        } else if (x > rightBound()) {
            return extrapolateRight(x);
        } else {
            int indexX = indexOfX(x);
            if (indexX != -1) {
                return getY(indexX);
            } else {
                return interpolate(x, floorIndexOfX(x));
            }
        }
    }

    public static void checkLengthIsTheSame(double[] xValues, double[] yValues) {
        if (xValues.length != yValues.length) throw new DifferentLengthOfArraysException("The lengths of the arrays are different");
    }

    public static void checkSorted(double[] xValues) {
        for (int i = 1; i < xValues.length; i++) {
            if(xValues[i] <= xValues[i - 1]) throw new ArrayIsNotSortedException("Array is not sorted");
        }
    }

    @Override
public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getClass().getSimpleName()).append(" size = ").append(getCount());
    for (Point point : this) {
        builder.append("\n[").append(point.x).append("; ").append(point.y).append("]");
    }
    return builder.toString();
}
}
