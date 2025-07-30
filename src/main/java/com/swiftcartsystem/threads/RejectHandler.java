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

public class RejectHandler implements Runnable {
    private final BlockingQueue<Order> rejectedQueue;

    public RejectHandler(BlockingQueue<Order> rejectedQueue) {
        this.rejectedQueue = rejectedQueue;
    }

    public void run() {
        while (true) {
            try {
                Order order = rejectedQueue.take();
                Logger.log("RejectHandler", "Handled rejected order #" + order.getId());
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
