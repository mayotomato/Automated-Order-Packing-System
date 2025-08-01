package main.java.com.swiftcartsystem.utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ReportGenerator {
    private static final AtomicInteger ordersProcessed = new AtomicInteger(0);
    private static final AtomicInteger ordersRejected = new AtomicInteger(0);
    private static final AtomicInteger boxesPacked = new AtomicInteger(0);
    private static final AtomicInteger containersCreated = new AtomicInteger(0);
    private static final AtomicInteger containersShipped = new AtomicInteger(0);
    private static final AtomicInteger trucksDispatched = new AtomicInteger(0);
    
    private static final List<Long> truckLoadingTimes = Collections.synchronizedList(new ArrayList<>());
    private static final List<Long> truckWaitTimes = Collections.synchronizedList(new ArrayList<>());
    
    // Increment methods
    public static void incrementOrdersProcessed() {
        ordersProcessed.incrementAndGet();
    }
    
    public static void incrementOrdersRejected() {
        ordersRejected.incrementAndGet();
    }
    
    public static void incrementBoxesPacked() {
        boxesPacked.incrementAndGet();
    }
    
    public static void incrementContainersCreated() {
        containersCreated.incrementAndGet();
    }
    
    public static void incrementContainersShipped() {
        containersShipped.incrementAndGet();
    }
    
    public static void incrementTrucksDispatched() {
        trucksDispatched.incrementAndGet();
    }
    
    // Time recording methods
    public static void recordTruckLoadingTime(long timeMillis) {
        truckLoadingTimes.add(timeMillis);
    }
    
    public static void recordTruckWaitTime(long timeMillis) {
        truckWaitTimes.add(timeMillis);
    }
    
    // Report generation
    public static void generateReport() {
        System.out.println("\n" + "============================================================");
        System.out.println("            SWIFTCART SYSTEM FINAL REPORT");
        System.out.println("============================================================");
        
        // Order Statistics
        System.out.println("\n ORDER PROCESSING STATISTICS:");
        System.out.println("   Total Orders Processed:     " + ordersProcessed.get());
        System.out.println("   Total Orders Rejected:      " + ordersRejected.get());
        System.out.println("   Success Rate:               " + 
            String.format("%.1f%%", (ordersProcessed.get() * 100.0) / (ordersProcessed.get() + ordersRejected.get())));
        
        // Production Statistics  
        System.out.println("\n PRODUCTION STATISTICS:");
        System.out.println("   Total Boxes Packed:         " + boxesPacked.get());
        System.out.println("   Total Containers Created:   " + containersCreated.get());
        System.out.println("   Total Containers Shipped:   " + containersShipped.get());
        System.out.println("   Total Trucks Dispatched:    " + trucksDispatched.get());
        
        // Loading Time Statistics
        System.out.println("\n TRUCK PERFORMANCE STATISTICS:");
        if (!truckLoadingTimes.isEmpty()) {
            long maxLoading = Collections.max(truckLoadingTimes);
            long minLoading = Collections.min(truckLoadingTimes);
            double avgLoading = truckLoadingTimes.stream().mapToLong(Long::longValue).average().orElse(0);
            
            System.out.println("   Maximum Truck Loading Time:  " + maxLoading + " ms");
            System.out.println("   Minimum Truck Loading Time:  " + minLoading + " ms");
            System.out.println("   Average Truck Loading Time:  " + String.format("%.2f ms", avgLoading));
        } else {
            System.out.println("   No truck loading time data recorded.");
        }
        
        // Wait Time Statistics
        if (!truckWaitTimes.isEmpty()) {
            long maxWait = Collections.max(truckWaitTimes);
            long minWait = Collections.min(truckWaitTimes);
            double avgWait = truckWaitTimes.stream().mapToLong(Long::longValue).average().orElse(0);
            
            System.out.println("   Maximum Truck Wait Time:     " + maxWait + " ms");
            System.out.println("   Minimum Truck Wait Time:     " + minWait + " ms");
            System.out.println("   Average Truck Wait Time:     " + String.format("%.2f ms", avgWait));
        }
        
        // System Status
        System.out.println("\n SYSTEM STATUS:");
        boolean ordersBalanced = (ordersProcessed.get() + ordersRejected.get()) == 600;
        boolean containersBalanced = containersCreated.get() == containersShipped.get();
        boolean systemCleared = ordersBalanced && containersBalanced;
        
        System.out.println("   Orders Balanced (600 total):    " + (ordersBalanced ? " YES" : " NO"));
        System.out.println("   Containers Balanced:            " + (containersBalanced ? " YES" : " NO"));
        System.out.println("   System Fully Cleared:           " + (systemCleared ? " YES" : " NO"));
        
        // Efficiency Metrics
        System.out.println("\n EFFICIENCY METRICS:");
        if (trucksDispatched.get() > 0) {
            double avgContainersPerTruck = (double) containersShipped.get() / trucksDispatched.get();
            System.out.println("   Average Containers per Truck:   " + String.format("%.1f", avgContainersPerTruck));
        }
        
        if (containersCreated.get() > 0) {
            double avgBoxesPerContainer = (double) boxesPacked.get() / containersCreated.get();
            System.out.println("   Average Boxes per Container:    " + String.format("%.1f", avgBoxesPerContainer));
        }
        
        System.out.println("\n" + "============================================================");
        System.out.println("                    END OF REPORT");
        System.out.println("============================================================" + "\n");
    }
    
    // Getter methods for debugging
    public static int getOrdersProcessed() { return ordersProcessed.get(); }
    public static int getOrdersRejected() { return ordersRejected.get(); }
    public static int getBoxesPacked() { return boxesPacked.get(); }
    public static int getContainersShipped() { return containersShipped.get(); }
    public static int getTrucksDispatched() { return trucksDispatched.get(); }
}