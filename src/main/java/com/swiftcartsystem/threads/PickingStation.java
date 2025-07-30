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

public class PickingStation implements Runnable {
    private final BlockingQueue<Order> intakeQueue;
    private final BlockingQueue<OrderBin> pickingQueue;
    private final BlockingQueue<Order> rejectedQueue;

    public PickingStation(BlockingQueue<Order> intakeQueue, BlockingQueue<OrderBin> pickingQueue, BlockingQueue<Order> rejectedQueue) {
        this.intakeQueue = intakeQueue;
        this.pickingQueue = pickingQueue;
        this.rejectedQueue = rejectedQueue;
    }

    public void run() {
        while (true) {
            try {
                Order order = intakeQueue.take();
                if (RandomUtil.shouldReject()) {
                    order.reject();
                    rejectedQueue.put(order);
                    Logger.log("PickingStation", "Rejected Order #" + order.getId());
                    continue;
                }
                OrderBin bin = new OrderBin(order);
                pickingQueue.put(bin);
                Logger.log("PickingStation", "Picked Order #" + order.getId());
                Thread.sleep(300);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
