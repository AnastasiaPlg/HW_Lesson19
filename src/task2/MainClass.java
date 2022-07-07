package task2;

import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    public static void main(String[] args) {
        ReentrantLock locker = new ReentrantLock();
        Buffer buffer = new Buffer(locker);
        Thread adder = new Thread(buffer);
        adder.setName("+add");
        Thread reader = new Thread(buffer);
        reader.setName("-read");
        adder.start();
        reader.start();
    }
}
