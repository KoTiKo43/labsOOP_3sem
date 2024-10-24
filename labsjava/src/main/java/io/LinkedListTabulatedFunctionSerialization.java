package io;

import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import operations.TabulatedDifferentialOperator;

import java.io.*;

public class LinkedListTabulatedFunctionSerialization {

    public static void main(String[] args) {
        try (BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream("output/serialized_linked_list_functions.bin"))) {
            TabulatedFunction function = new LinkedListTabulatedFunction(new double[]{0.0, 1.0, 2.0, 3.0, 4.0, 5.0}, new double[]{0.0, 1.0, 4.0, 9.0, 16.0, 25.0});

            TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();

            TabulatedFunction derivative1 = operator.derive(function);
            TabulatedFunction derivative2 = operator.derive(derivative1);

            FunctionsIO.serialize(fileOutputStream, function);
            FunctionsIO.serialize(fileOutputStream, derivative1);
            FunctionsIO.serialize(fileOutputStream, derivative2);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try (BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream("output/serialized_linked_list_functions.bin"))) {
            TabulatedFunction function1 = FunctionsIO.deserialize(fileInputStream);
            TabulatedFunction function2 = FunctionsIO.deserialize(fileInputStream);
            TabulatedFunction function3 = FunctionsIO.deserialize(fileInputStream);

            System.out.println(function1.toString());
            System.out.println(function2.toString());
            System.out.println(function3.toString());
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
