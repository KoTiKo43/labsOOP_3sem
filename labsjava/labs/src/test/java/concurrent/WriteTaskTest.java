package concurrent;

import functions.LinkedListTabulatedFunction;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class WriteTaskTest {

    @Test
    void TestRun() {
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(new double[]{0, 1, 2, 3, 4}, new double[]{0, 1, 4, 9, 16});
        WriteTask writeTask = new WriteTask(function, 52);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        writeTask.run();
        String output = outputStream.toString();
        assertTrue(output.contains("""
                Writing for index 0 complete
                Writing for index 1 complete
                Writing for index 2 complete
                Writing for index 3 complete
                Writing for index 4 complete"""));
    }
}