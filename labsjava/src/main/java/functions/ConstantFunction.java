package functions;

public class ConstantFunction implements MathFunction {
    private final double constNum;

    public ConstantFunction(double constNum) {
        this.constNum = constNum;
    }

    @Override
    public double apply(double x) {
        return constNum;
    }

    public double getConstNum() {
        return constNum;
    }
}
