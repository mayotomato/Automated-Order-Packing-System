/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.swiftcartsystem.utils;

/**
 *
 * @author zechn
 */

public class ReportGenerator {
    private static int ordersProcessed = 0;
    private static int ordersRejected = 0;
    private static int boxesPacked = 0;

    public static synchronized void incrementOrdersProcessed() {
        ordersProcessed++;
    }

    public static synchronized void incrementOrdersRejected() {
        ordersRejected++;
    }

    public static synchronized void incrementBoxesPacked() {
        boxesPacked++;
    }

    public static synchronized void generateReport() {
        Logger.log("ReportGenerator", "================ Final Report ================");
        Logger.log("ReportGenerator", "Total Orders Processed: " + ordersProcessed);
        Logger.log("ReportGenerator", "Total Orders Rejected:  " + ordersRejected);
        Logger.log("ReportGenerator", "Total Boxes Packed:     " + boxesPacked);
        Logger.log("ReportGenerator", "=============================================");
    }
}

