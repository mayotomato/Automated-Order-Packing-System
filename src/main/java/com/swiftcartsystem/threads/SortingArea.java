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

public class SortingArea implements Runnable {
    private final BlockingQueue<Box> labellingQueue;
    private final BlockingQueue<Container> sortingQueue;

    public SortingArea(BlockingQueue<Box> labellingQueue, BlockingQueue<Container> sortingQueue) {
        this.labellingQueue = labellingQueue;
        this.sortingQueue = sortingQueue;
    }

    public void run() {
        Batch batch = new Batch();
        Container container = new Container();

        while (true) {
            try {
                Box box = labellingQueue.take();
                batch.addBox(box);
                Logger.log("SortingArea", "Added Box for Order #" + box.getOrder().getId() + " to Batch");

                if (batch.isFull()) {
                    container.addBatch(batch);
                    batch = new Batch();
                    Logger.log("SortingArea", "Batch added to Container");

                    if (container.isFull()) {
                        sortingQueue.put(container);
                        Logger.log("SortingArea", "Container full. Sent for loading.");
                        container = new Container();
                    }
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}


