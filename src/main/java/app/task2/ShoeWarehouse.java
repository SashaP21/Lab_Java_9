package app.task2;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShoeWarehouse {
    public static final List<String> PRODUCT_LIST = List.of("Nike", "Adidas", "Puma", "Reebok");
    private static final int CAPACITY = 5;

    private final Queue<Order> orders = new LinkedList<>();

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
}
