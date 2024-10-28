package concurrent;

import functions.LinkedListTabulatedFunction;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ReadTaskTest {

    @Test
    void TestRun() {
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(new double[]{0, 1, 2, 3, 4}, new double[]{0, 1, 4, 9, 16});
        ReadTask readTask = new ReadTask(function);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        readTask.run();

        String output = outputStream.toString();
        assertTrue(output.contains("""
                After read: i = 0, x = 0,000000, y = 0,000000
                After read: i = 1, x = 1,000000, y = 1,000000
                After read: i = 2, x = 2,000000, y = 4,000000
                After read: i = 3, x = 3,000000, y = 9,000000
                After read: i = 4, x = 4,000000, y = 16,000000"""));
    }
}