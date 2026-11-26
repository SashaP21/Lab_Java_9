package app.task1;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread evenThread = new EvenThread();
        Thread oddThread = new Thread(new OddRunnable());

        evenThread.start();
        oddThread.start();

        evenThread.join();
        oddThread.join();
    }
}
