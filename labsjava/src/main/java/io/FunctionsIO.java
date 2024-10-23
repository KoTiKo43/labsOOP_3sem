package io;

import functions.Point;
import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public final class FunctionsIO {
    private FunctionsIO() {
        throw new UnsupportedOperationException("Class is final");
    }

    static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function) throws IOException {
        PrintWriter printWriter = new PrintWriter(writer);

        printWriter.println(function.getCount());

        for (Point point : function) {
            printWriter.printf("%f %f\n", point.x, point.y);
        }

        printWriter.flush();
    }

    static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        dataOutputStream.writeInt(function.getCount());
        for (Point point : function) {
            dataOutputStream.writeDouble(point.x);
            dataOutputStream.writeDouble(point.y);
        }

        dataOutputStream.flush();
    }

    static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException {
        String string = reader.readLine();
        int count = Integer.parseInt(string);

        double[] xValues = new double[count];
        double[] yValues = new double[count];

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.forLanguageTag("ru"));

        for (int i = 0; i < count; ++i) {
            string = reader.readLine();
            String[] numbers = string.split(" ");
            try {
                xValues[i] = numberFormat.parse(numbers[0]).doubleValue();
                yValues[i] = numberFormat.parse(numbers[1]).doubleValue();
            } catch (ParseException e) {
                throw new IOException(e);
            }
        }

        return factory.create(xValues, yValues);
    }
      
    static TabulatedFunction readTabulatedFunction(BufferedInputStream inputStream, TabulatedFunctionFactory factory) throws IOException{
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        int count = dataInputStream.readInt();
        double[] xValues = new double[count];
        double[] yValues = new double[count];
        for (int i = 0; i < count; i++) {
            xValues[i] = dataInputStream.readDouble();
            yValues[i] = dataInputStream.readDouble();
        }
        return factory.create(xValues, yValues);
    }
  
        public static void serialize(BufferedOutputStream stream, TabulatedFunction function) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);
        objectOutputStream.writeObject(function);

        objectOutputStream.flush();

        public static TabulatedFunction deserialize(BufferedInputStream stream) throws IOException, ClassNotFoundException {
        ObjectInputStream dataInputStream = new ObjectInputStream(stream);
        return (TabulatedFunction) dataInputStream.readObject();
    }
}
