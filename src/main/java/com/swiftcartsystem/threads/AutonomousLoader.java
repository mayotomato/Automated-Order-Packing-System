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
    private final Object bayLock;

    public AutonomousLoader(BlockingQueue<Container> sortingQueue, BlockingQueue<Container> loadingQueue,
                            Semaphore loaderSemaphore, Object bayLock) {
        this.sortingQueue = sortingQueue;
        this.loadingQueue = loadingQueue;
        this.loaderSemaphore = loaderSemaphore;
        this.bayLock = bayLock;
    }

    public void run() {
        try {
            while (true) {
                loaderSemaphore.acquire();
                Container container = sortingQueue.take();

                if (container == Container.terminate) {
                    // One loader shuts down, do not signal downstream
                    loaderSemaphore.release();
                    break;
                }

                if (RandomUtil.loaderBreaksDown()) {
                    Logger.log("AutonomousLoader", "Loader broken down! Repairing...");
                    Thread.sleep(2000);
                }

                synchronized (bayLock) {
                    while (loadingQueue.size() >= 5) {
                        Logger.log("AutonomousLoader", "Loading bay full. Waiting...");
                        bayLock.wait();
                    }

                    loadingQueue.put(container);
                    Logger.log("AutonomousLoader", "Moved container to loading bay.");
                    bayLock.notifyAll();
                }

                loaderSemaphore.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
