package task2;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer implements Runnable {
    private final int capacity = 10;
    private final Integer[] content;
    private int size;
    ReentrantLock locker;
    Condition condition;

    public Buffer(ReentrantLock lock) {
        this.content = new Integer[capacity];
        this.size = 0;
        this.locker = lock;
        condition = locker.newCondition();
    }

    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            workWithBuffer();
        }
    }

    private void workWithBuffer() {
        locker.lock();
        try {
            int number = (int) (20 * Math.random() + 1);
            String name = Thread.currentThread().getName();
            System.out.println("Work " + name + " thread");
            if (name.startsWith("+")) {
                while (size == capacity) {
                    condition.await();
                }
                condition.signal();
                putObj(number);
            }
            if (name.startsWith("-")) {
                while (size == 0) {
                    condition.await();
                }
                condition.signal();
                Integer obj = get();
                System.out.println(obj);
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            locker.unlock();
        }
    }

    private void putObj(Integer obj) throws InterruptedException {
        content[size] = obj;
        size++;
        System.out.println(Arrays.toString(content));
    }

    private Integer get() throws InterruptedException {
        Integer obj = content[0];
        content[0] = null;
        removeObj();
        size--;
        System.out.println(Arrays.toString(content));
        return obj;
    }

    private void removeObj() {
        for (int i = 0; i < capacity - 1; i++) {
            if (content[i] == null && content[i + 1] != null) {
                content[i] = content[i + 1];
                content[i + 1] = null;
            }
        }
    }
}
