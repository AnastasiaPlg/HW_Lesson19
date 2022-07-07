package task1;

public class MainClass {
    public static void main(String[] args) {
        MyThread myThread1 = new MyThread("myThread1");
        MyThread myThread2 = new MyThread("myThread2");
        MyThread myThread3 = new MyThread("myThread3");
        Thread thread1 = new Thread(myThread1);
        Thread thread2 = new Thread(myThread2);
        Thread thread3 = new Thread(myThread3);
        try {
            thread3.start();
            thread3.join();
            thread2.start();
            thread2.join();
            thread1.start();
            thread1.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }

    }
}
