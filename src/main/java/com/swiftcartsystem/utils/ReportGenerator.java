/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.swiftcartsystem.utils;

/**
 *
 * @author zechn
 */

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ReportGenerator {
    private static final AtomicInteger ordersProcessed = new AtomicInteger(0);
    private static final AtomicInteger ordersRejected = new AtomicInteger(0);
    private static final AtomicInteger boxesPacked = new AtomicInteger(0);
    private static final AtomicInteger containersShipped = new AtomicInteger(0);
    private static final AtomicInteger trucksDispatched = new AtomicInteger(0);

    private static final List<Long> truckLoadingTimes = Collections.synchronizedList(new ArrayList<>());

    public static void incrementOrdersProcessed() {
        ordersProcessed.incrementAndGet();
    }

    public static void incrementOrdersRejected() {
        ordersRejected.incrementAndGet();
    }

    public static void incrementBoxesPacked() {
        boxesPacked.incrementAndGet();
    }

    public static void incrementContainersShipped() {
        containersShipped.incrementAndGet();
    }

    public static void incrementTrucksDispatched() {
        trucksDispatched.incrementAndGet();
    }

    public static void recordTruckLoadingTime(long timeMillis) {
        truckLoadingTimes.add(timeMillis);
    }

    public static void generateReport() {
        System.out.println("\n========== SwiftCart System Report ==========");

        System.out.println("Total Orders Processed: " + ordersProcessed.get());
        System.out.println("Total Orders Rejected: " + ordersRejected.get());
        System.out.println("Total Boxes Packed: " + boxesPacked.get());
        System.out.println("Total Containers Shipped: " + containersShipped.get());
        System.out.println("Total Trucks Dispatched: " + trucksDispatched.get());

        if (!truckLoadingTimes.isEmpty()) {
            long max = Collections.max(truckLoadingTimes);
            long min = Collections.min(truckLoadingTimes);
            double avg = truckLoadingTimes.stream().mapToLong(Long::longValue).average().orElse(0);

            System.out.println("Maximum Truck Loading Time: " + max + " ms");
            System.out.println("Minimum Truck Loading Time: " + min + " ms");
            System.out.println("Average Truck Loading Time: " + avg + " ms");
        } else {
            System.out.println("No truck loading time data recorded.");
        }

        boolean systemCleared = (ordersProcessed.get() == (boxesPacked.get() + ordersRejected.get()));
        System.out.println("System Cleared: " + (systemCleared ? "YES" : "NO"));

        System.out.println("==============================================\n");
    }
}

