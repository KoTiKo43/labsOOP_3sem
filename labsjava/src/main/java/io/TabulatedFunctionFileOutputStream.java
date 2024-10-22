package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TabulatedFunctionFileOutputStream {
    public static void main(String[] args)  {
        try (
            BufferedOutputStream LinkedListfileOutputStream = new BufferedOutputStream(new FileOutputStream("output/linked list function.bin"));
            BufferedOutputStream ArrayfileOutputStream = new BufferedOutputStream(new FileOutputStream("output/array function.bin"))
        ) {

            FunctionsIO.writeTabulatedFunction(LinkedListfileOutputStream, new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 4, 9, 16, 25}));
            FunctionsIO.writeTabulatedFunction(ArrayfileOutputStream, new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{-1, -2, -3, -4, -5}));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
