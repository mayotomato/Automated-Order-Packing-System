package main.java.com.swiftcartsystem.threads;

import main.java.com.swiftcartsystem.*;
import main.java.com.swiftcartsystem.utils.*;
import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Truck implements Runnable {
    private final BlockingQueue<Container> loadingQueue;
    private final Object bayLock;
    private static final AtomicInteger truckIdCounter = new AtomicInteger(1);
    private final int truckId;
    
    public Truck(BlockingQueue<Container> loadingQueue, Object bayLock) {
        this.loadingQueue = loadingQueue;
        this.bayLock = bayLock;
        this.truckId = truckIdCounter.getAndIncrement();
    }
    
    @Override
    public void run() {
        List<Container> containers = new ArrayList<>();
        long loadingStartTime = System.currentTimeMillis();
        boolean isWaiting = false;
        
        try {
            while (true) {
                Container container;
                long waitStartTime = System.currentTimeMillis();
                
                synchronized (bayLock) {
                    while (loadingQueue.isEmpty()) {
                        if (!isWaiting) {
                            Logger.log("Truck-" + truckId, "Waiting for containers at loading bay");
                            isWaiting = true;
                        }
                        bayLock.wait();
                    }
                    container = loadingQueue.take();
                    bayLock.notifyAll(); // Notify other waiting threads
                }
                
                if (isWaiting) {
                    long waitTime = System.currentTimeMillis() - waitStartTime;
                    ReportGenerator.recordTruckWaitTime(waitTime);
                    isWaiting = false;
                }
                
                if (container == Container.terminate) {
                    // Shutdown signal received
                    if (!containers.isEmpty()) {
                        long loadingTime = System.currentTimeMillis() - loadingStartTime;
                        ReportGenerator.recordTruckLoadingTime(loadingTime);
                        ReportGenerator.incrementTrucksDispatched();
                        Logger.log("Truck-" + truckId, "Final dispatch with " + containers.size() + 
                                  " containers (Loading time: " + loadingTime + "ms)");
                    }
                    Logger.log("Truck-" + truckId, "Received shutdown signal. Terminating.");
                    break;
                }
                
                containers.add(container);
                ReportGenerator.incrementContainersShipped();
                Logger.log("Truck-" + truckId, "Loaded container. Current count: " + containers.size() + "/18");
                
                // Check if truck is fully loaded
                if (containers.size() >= 18) {
                    long loadingTime = System.currentTimeMillis() - loadingStartTime;
                    ReportGenerator.recordTruckLoadingTime(loadingTime);
                    ReportGenerator.incrementTrucksDispatched();
                    
                    Logger.log("Truck-" + truckId, "Fully loaded with 18 containers. " +
                              "Departing to Distribution Centre (Loading time: " + loadingTime + "ms)");
                    
                    // Simulate departure time
                    Thread.sleep(1000);
                    
                    // Reset for next load
                    containers.clear();
                    loadingStartTime = System.currentTimeMillis();
                    
                    Logger.log("Truck-" + truckId, "Returned and ready for next load");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Logger.log("Truck-" + truckId, "Interrupted during operation");
        }
    }
}