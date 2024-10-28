package concurrent;

import functions.ConstantFunction;
import functions.LinkedListTabulatedFunction;

public class ReadWriteTaskExecutor {

    public static void main(String[] args) {
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(new ConstantFunction(-52), 1, 1000, 1000);
        Thread readThread = new Thread(new ReadTask(function));
        Thread writeThread = new Thread(new WriteTask(function, 0.52));
        readThread.start();
        writeThread.start();


    }
}
