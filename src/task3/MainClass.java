package task3;

public class MainClass {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Thread adder = new Thread(buffer);
        adder.setName("+add");
        Thread reader = new Thread(buffer);
        reader.setName("-read");
        adder.start();
        reader.start();
    }
}
