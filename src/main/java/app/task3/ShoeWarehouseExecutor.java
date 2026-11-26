package app.task3;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShoeWarehouseExecutor {
    public static final List<String> PRODUCT_LIST = List.of("Nike", "Adidas", "Puma", "Reebok");
    private static final int CAPACITY = 5;

    private final Queue<Order> orders = new LinkedList<>();
    private final ExecutorService processors = Executors.newFixedThreadPool(4);

    public synchronized void receiveOrder(Order order) throws InterruptedException {
        while (orders.size() >= CAPACITY) {
            wait();
        }
        orders.add(order);
        System.out.println("Получен заказ: " + order);
        notifyAll();
    }

    public synchronized Order fulfillOrder() throws InterruptedException {
        while (orders.isEmpty()) {
            wait();
        }
        Order order = orders.poll();
        System.out.println("Обработан заказ: " + order);
        notifyAll();
        return order;
    }

    public void processOrders(int n) {
        for (int i = 0; i < n; i++) {
            processors.submit(() -> {
                try {
                    fulfillOrder();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    public void shutdown() throws InterruptedException {
        processors.shutdown();
        processors.awaitTermination(1, TimeUnit.MINUTES);
    }
}
