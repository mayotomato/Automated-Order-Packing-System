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
    private static final AtomicInteger idCounter = new AtomicInteger(1);
    
    public OrderIntakeSystem(BlockingQueue<Order> intakeQueue) {
        this.intakeQueue = intakeQueue;
    }

    public void run() {
        while (true) {
            try {
                int id = idCounter.getAndIncrement();
                Order order = new Order(id, RandomUtil.getRandomOrderContents());
                intakeQueue.put(order);
                Logger.log("OrderIntake", "Order #" + order.getId() + " received with contents: " + order.getContents());
                ReportGenerator.incrementOrdersProcessed();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
