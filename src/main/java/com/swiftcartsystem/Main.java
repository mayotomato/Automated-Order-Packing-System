package main.java.com.swiftcartsystem;

import main.java.com.swiftcartsystem.threads.*;
import main.java.com.swiftcartsystem.utils.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Initialize all queues
        BlockingQueue<Order> intakeQueue = new LinkedBlockingQueue<>();
        BlockingQueue<OrderBin> pickingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<OrderBin> packingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Box> labellingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Container> sortingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Container> loadingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Order> rejectedQueue = new LinkedBlockingQueue<>();
        
        // Synchronization primitives
        Semaphore loaderSemaphore = new Semaphore(3, true);
        Object bayLock = new Object();
        
        // Create threads
        Thread intakeThread = new Thread(new OrderIntakeSystem(intakeQueue), "OrderIntake-1");
        Thread pickingThread = new Thread(new PickingStation(intakeQueue, pickingQueue, rejectedQueue), "Picker-1");
        Thread packingThread = new Thread(new PackingStation(pickingQueue, packingQueue, rejectedQueue, loadingQueue, bayLock), "Packer-1");
        Thread labellingThread = new Thread(new LabellingStation(packingQueue, labellingQueue, rejectedQueue), "Labeller-1");
        Thread sortingThread = new Thread(new SortingArea(labellingQueue, sortingQueue), "Sorter-1");
        
        // Create loader threads with proper names
        Thread[] loaderThreads = new Thread[3];
        for (int i = 0; i < loaderThreads.length; i++) {
            loaderThreads[i] = new Thread(new AutonomousLoader(sortingQueue, loadingQueue, loaderSemaphore, bayLock), "Loader-" + (i + 1));
        }
        
        // Create truck threads with proper names
        Thread[] truckThreads = new Thread[2];
        for (int i = 0; i < truckThreads.length; i++) {
            truckThreads[i] = new Thread(new Truck(loadingQueue, bayLock), "Truck-" + (i + 1));
        }
        
        Thread rejectHandlerThread = new Thread(new RejectHandler(rejectedQueue), "RejectHandler-1");
        
        System.out.println("SwiftCart E-commerce Centre Starting...");
        System.out.println("Processing 600 orders at 1 order per 500ms");
        System.out.println("==========================================");
        
        // Start all threads
        intakeThread.start();
        pickingThread.start();
        packingThread.start();
        labellingThread.start();
        sortingThread.start();
        for (Thread t : loaderThreads) t.start();
        for (Thread t : truckThreads) t.start();
        rejectHandlerThread.start();
        
        System.out.println("All threads started. Processing orders...");
        
        // Wait for intake to complete (this ensures all 600 orders are generated)
        intakeThread.join();
        System.out.println("Order intake completed. Processing remaining orders...");
        
        // Wait for processing pipeline to complete
        pickingThread.join();
        System.out.println("Picking completed.");
        
        packingThread.join();
        System.out.println("Packing completed.");
        
        labellingThread.join();
        System.out.println("Labelling completed.");
        
        sortingThread.join();
        System.out.println("Sorting completed.");
        
        // Wait for all loaders to finish
        for (Thread t : loaderThreads) {
            t.join();
        }
        System.out.println("All loaders completed.");
        
        // Send shutdown signals to trucks and reject handler
        System.out.println("Sending shutdown signals to trucks...");
        for (int i = 0; i < truckThreads.length; i++) {
            loadingQueue.put(Container.terminate);
        }
        rejectedQueue.put(Order.terminate);
        
        // Wait for trucks and reject handler to finish
        for (Thread t : truckThreads) {
            t.join();
        }
        rejectHandlerThread.join();
        
        System.out.println("All processing completed. Generating final report...");
        
        // Generate final report
        ReportGenerator.generateReport();
        
        System.out.println("SwiftCart system shutdown complete.");
    }
}