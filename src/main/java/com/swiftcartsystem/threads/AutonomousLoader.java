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

import java.util.concurrent.*;

public class AutonomousLoader implements Runnable {
    private final BlockingQueue<Container> sortingQueue;
    private final BlockingQueue<Container> loadingQueue;
    private final Semaphore loaderSemaphore;

    public AutonomousLoader(BlockingQueue<Container> sortingQueue, BlockingQueue<Container> loadingQueue, Semaphore loaderSemaphore) {
        this.sortingQueue = sortingQueue;
        this.loadingQueue = loadingQueue;
        this.loaderSemaphore = loaderSemaphore;
    }

    public void run() {
        while (true) {
            try {
                loaderSemaphore.acquire();
                Container container = sortingQueue.take();

                if (RandomUtil.loaderBreaksDown()) {
                    Logger.log("AutonomousLoader", "Loader broken down! Repairing...");
                    Thread.sleep(2000);
                }

                loadingQueue.put(container);
                Logger.log("AutonomousLoader", "Moved container to loading bay.");
                loaderSemaphore.release();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
