package app.task2;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ShoeWarehouse warehouse = new ShoeWarehouse();

        int consumerCount = 4;
        int ordersPerConsumer = 5;
        int n = consumerCount * ordersPerConsumer;

        Thread producer = new Thread(() -> {
            Random random = new Random();
            for (int i = 1; i <= n; i++) {
                String type = ShoeWarehouse.PRODUCT_LIST.get(random.nextInt(ShoeWarehouse.PRODUCT_LIST.size()));
                int quantity = random.nextInt(10) + 1;
                try {
                    warehouse.receiveOrder(new Order(i, type, quantity));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread[] consumers = new Thread[consumerCount];
        for (int c = 0; c < consumerCount; c++) {
            consumers[c] = new Thread(() -> {
                for (int i = 0; i < ordersPerConsumer; i++) {
                    try {
                        warehouse.fulfillOrder();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        producer.start();
        for (Thread consumer : consumers) {
            consumer.start();
        }

        producer.join();
        for (Thread consumer : consumers) {
            consumer.join();
        }

        System.out.println("Все заказы обработаны.");
    }
}
