package io;

import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TabulatedFunctionFileReader {
    public static void main(String[] args) {
        try (BufferedReader arrayReader = new BufferedReader(new FileReader("input/function.txt"));
             BufferedReader linkedListReader = new BufferedReader(new FileReader("input/function.txt"))) {

            TabulatedFunction arrayFunction = FunctionsIO.readTabulatedFunction(arrayReader, new ArrayTabulatedFunctionFactory());
            System.out.println("Array Tabulated Function:");
            System.out.println(arrayFunction);

            TabulatedFunction linkedListFunction = FunctionsIO.readTabulatedFunction(linkedListReader, new LinkedListTabulatedFunctionFactory());
            System.out.println("Linked List Tabulated Function:");
            System.out.println(linkedListFunction);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
