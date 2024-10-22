package io;

import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;

import java.io.*;

public class TabulatedFunctionFileInputStream {

    public static void main(String[] args){
        try (
                BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream("input/binary function.bin"))
        ) {
            ArrayTabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
            TabulatedFunction function = FunctionsIO.readTabulatedFunction(fileInputStream, factory);
            System.out.println(function.toString());

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите размер и значения функции:");
            int size = Integer.parseInt(reader.readLine());
            double[] xValues = new double[size];
            double[] yValues = new double[size];

            for (int i = 0; i < size; i++) {
                String[] input = reader.readLine().split(" ");
                xValues[i] = Double.parseDouble(input[0]);
                yValues[i] = Double.parseDouble(input[1]);
            }

            TabulatedFunction linkedListFunction = new LinkedListTabulatedFunctionFactory().create(xValues, yValues);

            TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();
            TabulatedFunction derivative = operator.derive(linkedListFunction);
            System.out.println("Производная функции:");
            System.out.println(derivative);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
