/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.swiftcartsystem.threads;

/**
 *
 * @author zechn
 */

import main.java.com.swiftcartsystem.*;
import main.java.com.swiftcartsystem.utils.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderIntakeSystem implements Runnable {
    private final BlockingQueue<Order> intakeQueue;
    private static final int MAX_ORDERS = 600;
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    public OrderIntakeSystem(BlockingQueue<Order> intakeQueue) {
        this.intakeQueue = intakeQueue;
    }

    public void run() {
        try {
            for (int i = 0; i < MAX_ORDERS; i++) {
                int id = idCounter.getAndIncrement();
                Order order = new Order(id, RandomUtil.getRandomOrderContents());
                intakeQueue.put(order);
                Logger.log("OrderIntake", "Order #" + order.getId() + " received with contents: " + order.getContents());
                Thread.sleep(500);
            }
            // Signal end of orders
            intakeQueue.put(Order.terminate);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
