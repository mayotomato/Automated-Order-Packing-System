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

public class LabellingStation implements Runnable {
    private final BlockingQueue<OrderBin> packingQueue;
    private final BlockingQueue<Box> labellingQueue;
    private final BlockingQueue<Order> rejectedQueue;

    public LabellingStation(BlockingQueue<OrderBin> packingQueue, BlockingQueue<Box> labellingQueue, BlockingQueue<Order> rejectedQueue) {
        this.packingQueue = packingQueue;
        this.labellingQueue = labellingQueue;
        this.rejectedQueue = rejectedQueue;
    }

    public void run() {
        while (true) {
            try {
                OrderBin bin = packingQueue.take();
                if (RandomUtil.shouldReject()) {
                    bin.getOrder().reject();
                    rejectedQueue.put(bin.getOrder());
                    Logger.log("LabellingStation", "Rejected Order #" + bin.getOrder().getId());
                    continue;
                }
                String trackingId = RandomUtil.generateTrackingId();
                Box box = new Box(bin.getOrder(), trackingId);
                labellingQueue.put(box);
                Logger.log("LabellingStation", "Labelled Order #" + bin.getOrder().getId() + " with Tracking ID: " + trackingId);
                Thread.sleep(300);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
