/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.swiftcartsystem;

/**
 *
 * @author zechn
 */
public class Box {
    private final Order order;
    private final String trackingId;
    public static final Box terminate = new Box(Order.terminate, "-1");

    public Box(Order order, String trackingId) {
        this.order = order;
        this.trackingId = trackingId;
    }

    public Order getOrder() {
        return order;
    }

    public String getTrackingId() {
        return trackingId;
    }

    @Override
    public String toString() {
        return "Box for Order #" + order.getId() + " [Tracking ID: " + trackingId + "]";
    }
}
