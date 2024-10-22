package io;

import functions.Point;
import functions.TabulatedFunction;

import java.io.BufferedWriter;
import java.io.PrintWriter;

final public class FunctionsIO {
    private FunctionsIO() {
        throw new UnsupportedOperationException("Class is final");
    }

    static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function) {
        PrintWriter printWriter = new PrintWriter(writer);

        printWriter.println(function.getCount());

        for (Point point: function) {
            printWriter.printf("%f %f\n", point.x, point.y);
        }

        printWriter.flush();
    }
}
