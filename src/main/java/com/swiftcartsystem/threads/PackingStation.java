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

public class PackingStation implements Runnable {
    private final BlockingQueue<OrderBin> pickingQueue;
    private final BlockingQueue<OrderBin> packingQueue;
    private final BlockingQueue<Order> rejectedQueue;
    private final BlockingQueue<Container> loadingQueue;
    private final Object bayLock;

    public PackingStation(BlockingQueue<OrderBin> pickingQueue, BlockingQueue<OrderBin> packingQueue,
                          BlockingQueue<Order> rejectedQueue, BlockingQueue<Container> loadingQueue, Object bayLock) {
        this.pickingQueue = pickingQueue;
        this.packingQueue = packingQueue;
        this.rejectedQueue = rejectedQueue;
        this.loadingQueue = loadingQueue;
        this.bayLock = bayLock;
    }

    public void run() {
        while (true) {
            try {
                synchronized (bayLock) {
                    while (loadingQueue.size() >= 5) {
                        Logger.log("PackingStation", "Loading bay full. Pausing packing.");
                        bayLock.wait();
                    }
                }

                OrderBin bin = pickingQueue.take();
                if (RandomUtil.shouldReject()) {
                    bin.getOrder().reject();
                    rejectedQueue.put(bin.getOrder());
                    Logger.log("PackingStation", "Rejected Order #" + bin.getOrder().getId());
                    continue;
                }
                packingQueue.put(bin);
                Logger.log("PackingStation", "Packed Order #" + bin.getOrder().getId());
                Thread.sleep(300);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

