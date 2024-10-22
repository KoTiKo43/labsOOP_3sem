package io;

import functions.Point;
import functions.TabulatedFunction;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

final public class FunctionsIO {
    private FunctionsIO() {
        throw new UnsupportedOperationException("Class is final");
    }

    static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        PrintWriter printWriter = new PrintWriter(writer);

        printWriter.println(function.getCount());

        for (Point point: function) {
            printWriter.printf("%f %f\n", point.x, point.y);
        }

        dataOutputStream.writeInt(function.getCount());
        for (Point point : function) {
            dataOutputStream.writeDouble(point.x);
            dataOutputStream.writeDouble(point.y);
        }

        dataOutputStream.flush();
        printWriter.flush();
    }
}
