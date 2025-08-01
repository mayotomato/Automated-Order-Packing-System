package main.java.com.swiftcartsystem;

// File: main.java.com.swiftcartsystem/Main.java

import main.java.com.swiftcartsystem.threads.*;
import main.java.com.swiftcartsystem.utils.*;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Order> intakeQueue = new LinkedBlockingQueue<>();
        BlockingQueue<OrderBin> pickingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<OrderBin> packingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Box> labellingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Container> sortingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Container> loadingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Order> rejectedQueue = new LinkedBlockingQueue<>();

        Semaphore loaderSemaphore = new Semaphore(3, true);
        Object bayLock = new Object();

        Thread intakeThread = new Thread(new OrderIntakeSystem(intakeQueue));
        Thread pickingThread = new Thread(new PickingStation(intakeQueue, pickingQueue, rejectedQueue));
        Thread packingThread = new Thread(new PackingStation(pickingQueue, packingQueue, rejectedQueue, loadingQueue, bayLock));
        Thread labellingThread = new Thread(new LabellingStation(packingQueue, labellingQueue, rejectedQueue));
        Thread sortingThread = new Thread(new SortingArea(labellingQueue, sortingQueue));

        Thread[] loaderThreads = new Thread[3];
        for (int i = 0; i < loaderThreads.length; i++) {
            loaderThreads[i] = new Thread(new AutonomousLoader(sortingQueue, loadingQueue, loaderSemaphore, bayLock));
        }

        Thread[] truckThreads = new Thread[2];
        for (int i = 0; i < truckThreads.length; i++) {
            truckThreads[i] = new Thread(new Truck(loadingQueue, bayLock));
        }

        Thread rejectHandlerThread = new Thread(new RejectHandler(rejectedQueue));

        // Start all threads
        intakeThread.start();
        pickingThread.start();
        packingThread.start();
        labellingThread.start();
        sortingThread.start();
        for (Thread t : loaderThreads) t.start();
        for (Thread t : truckThreads) t.start();
        rejectHandlerThread.start();

        // Wait for all processing threads to complete
        intakeThread.join();
        pickingThread.join();
        packingThread.join();
        labellingThread.join();
        sortingThread.join();
        for (Thread t : loaderThreads) t.join();

        // Send shutdown signals to trucks and reject handler
        for (int i = 0; i < truckThreads.length; i++) {
            loadingQueue.put(Container.terminate);
        }
        rejectedQueue.put(Order.terminate);

        for (Thread t : truckThreads) t.join();
        rejectHandlerThread.join();

        // Generate final report
        ReportGenerator.generateReport();
    }
}
