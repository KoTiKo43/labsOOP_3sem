package functions;

public class SimpleIterations implements MathFunction {
    // Выбранная для метода функция g(x)
    private MathFunction gFunction;

    // Переменная, устанавливающая максимальное количество итераций, чтобы избежать бесконечного цикла
    private int maxIterations;

    // Допустимая погрешность
    private double errorRate;

    // Конструктор класса
    public SimpleIterations(MathFunction gFunction, int maxIterations, double errorRate) {
        this.gFunction = gFunction;
        this.maxIterations = maxIterations;
        this.errorRate = errorRate;
    }

    // Реализация метода apply из интерфейса. В данном случае он будет возвращать результат применения функции g(x)
    @Override
    public double apply(double x) {
        return gFunction.apply(x);
    }

    // Метод findRoot выполняет сам процесс итераций для нахождения корня уравнения
    public double findRoot(double initialGuess) {
        double xOld = initialGuess; // Присваиваем заданное начальное приближение
        double xNew = apply(xOld); // n+1-й икс, равный g(x n-го)
        int iteration = 1; // Номер итерации

        while(Math.abs(xNew - xOld) > errorRate && iteration < maxIterations) {
            xOld = xNew;
            xNew = apply(xOld);
            iteration++;
        }

        if (Math.abs(xNew-xOld) <= errorRate) {
            return xNew; // Возвращаем найденный корень
        } else {
            return Double.NaN; // Если корень не найден, возвращаем NaN
        }
    }
}
