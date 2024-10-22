package io;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.BufferOverflowException;

public class TabulatedFunctionFileWriter {
    public static void main(String[] args) {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {1, 4, 9, 16, 25};

        TabulatedFunction arrayFunction = new ArrayTabulatedFunctionFactory().create(xValues, yValues);
        TabulatedFunction linkedFunction = new LinkedListTabulatedFunctionFactory().create(xValues, yValues);

        try (BufferedWriter arrayWriter = new BufferedWriter(new FileWriter("output/array function.txt"));
             BufferedWriter linkedListWriter = new BufferedWriter(new FileWriter("output/linked list function.txt"))) {

            FunctionsIO.writeTabulatedFunction(arrayWriter, arrayFunction);
            FunctionsIO.writeTabulatedFunction(linkedListWriter, linkedFunction);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
