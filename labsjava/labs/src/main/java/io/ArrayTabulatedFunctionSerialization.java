package io;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;

import java.io.*;

public class ArrayTabulatedFunctionSerialization {
    public static void main(String[] args) {
        try(BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("output/serialized array functions.bin"))) {
            double[] xValues = {1, 2, 3, 4, 5};
            double[] yValues = {1, 4, 9, 16, 25};
            TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
            TabulatedFunction originalArrayFunction = new ArrayTabulatedFunction(xValues, yValues);

            TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(factory);
            TabulatedFunction firstArrayDerivative = operator.derive(originalArrayFunction);
            TabulatedFunction secondArrayDerivative = operator.derive(firstArrayDerivative);

            FunctionsIO.serialize(outputStream, originalArrayFunction);
            FunctionsIO.serialize(outputStream, firstArrayDerivative);
            FunctionsIO.serialize(outputStream, secondArrayDerivative);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("output/serialized array functions.bin"))) {
            TabulatedFunction deserializedOriginalArrayFunction = FunctionsIO.deserialize(inputStream);
            TabulatedFunction deserializedFirstArrayDerivative = FunctionsIO.deserialize(inputStream);
            TabulatedFunction deserializedSecondArrayDerivative = FunctionsIO.deserialize(inputStream);

            System.out.println(deserializedOriginalArrayFunction.toString());

            System.out.println(deserializedFirstArrayDerivative.toString());

            System.out.println(deserializedSecondArrayDerivative.toString());

        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
