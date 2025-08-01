/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.swiftcartsystem;

/**
 *
 * @author zechn
 */
import java.util.*;

public class OrderBin {
    private final Order order;
    private final List<String> pickedItems;
    public static final OrderBin terminate = new OrderBin(Order.terminate);

    public OrderBin(Order order) {
        this.order = order;
        this.pickedItems = new ArrayList<>(order.getContents());
    }

    public Order getOrder() {
        return order;
    }

    public List<String> getPickedItems() {
        return pickedItems;
    }

    public void modifyContentsRandomly() {
        if (!pickedItems.isEmpty()) {
            pickedItems.remove(0); // simulate missing item
        }
    }

    public boolean matchesOrderContents() {
        List<String> original = order.getContents();
        return new HashSet<>(original).equals(new HashSet<>(pickedItems));
    }
}

