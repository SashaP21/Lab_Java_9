package app;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Задание 1: Создание потоков");
        app.task1.Main.main(args);

        System.out.println();
        System.out.println("Задание 2: Producer-Consumer");
        app.task2.Main.main(args);

        System.out.println();
        System.out.println("Задание 3: ExecutorService");
        app.task3.Main.main(args);
    }
}
