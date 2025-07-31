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

public class RandomUtil {
    private static final Random rand = new Random();
    private static final List<String> inventory = Arrays.asList("Keyboard", "Mouse", "Monitor", "CPU", "RAM", "SSD", "HDD", "GPU", "Cable", "Power Supply");

    public static boolean shouldReject() {
        return rand.nextInt(10) < 2; // 20% rejection chance
    }

    public static boolean loaderBreaksDown() {
        return rand.nextInt(10) < 1; // 10% breakdown chance
    }

    public static String generateTrackingId() {
        return "T" + (1000 + rand.nextInt(9000));
    }

    public static List<String> getRandomOrderContents() {
        int itemCount = 1 + rand.nextInt(4); // 1 to 4 items
        Set<String> items = new HashSet<>();
        while (items.size() < itemCount) {
            items.add(inventory.get(rand.nextInt(inventory.size())));
        }
        return new ArrayList<>(items);
    }
}
