package app.task3;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ShoeWarehouseExecutor warehouse = new ShoeWarehouseExecutor();
        int n = 20;

        warehouse.processOrders(n);

        ExecutorService submitters = Executors.newFixedThreadPool(4);
        Random random = new Random();
        for (int i = 1; i <= n; i++) {
            int id = i;
            String type = ShoeWarehouseExecutor.PRODUCT_LIST.get(random.nextInt(ShoeWarehouseExecutor.PRODUCT_LIST.size()));
            int quantity = random.nextInt(10) + 1;
            submitters.submit(() -> {
                try {
                    warehouse.receiveOrder(new Order(id, type, quantity));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        submitters.shutdown();
        submitters.awaitTermination(1, TimeUnit.MINUTES);
        warehouse.shutdown();

        System.out.println("Все заказы обработаны.");
    }
}
