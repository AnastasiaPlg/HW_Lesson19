package task3;

import java.util.Arrays;

public class Buffer implements Runnable {
    private final int capacity = 10;
    private final Integer[] content;
    private int size;

    public Buffer() {
        this.content = new Integer[capacity];
        this.size = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            workWithBuffer();
        }
    }

    private synchronized void workWithBuffer() {
        try {
            int number = (int) (20 * Math.random() + 1);
            String name = Thread.currentThread().getName();
            System.out.println("Work " + name + " thread");
            if (name.startsWith("+")) {
                while (size == capacity) {
                    wait();
                }
                notify();
                putObj(number);
            }
            if (name.startsWith("-")) {
                while (size == 0) {
                    wait();
                }
                notify();
                Integer obj = get();
                System.out.println(obj);
            }
        } catch (InterruptedException e) {
            System.out.println(e);
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
