/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.swiftcartsystem.utils;

/**
 *
 * @author zechn
 */
import java.util.Random;

public class RandomUtil {
    private static final Random rand = new Random();

    public static boolean shouldReject() {
        return rand.nextInt(10) < 2; // 20% rejection chance
    }

    public static boolean loaderBreaksDown() {
        return rand.nextInt(10) < 1; // 10% breakdown chance
    }

    public static String generateTrackingId() {
        return "T" + (1000 + rand.nextInt(9000));
    }
}
