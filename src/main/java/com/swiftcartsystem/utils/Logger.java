/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.swiftcartsystem.utils;

/**
 *
 * @author zechn
 */
public class Logger {
    public static synchronized void log(String component, String message) {
        System.out.printf("[%s] %s (Thread: %s)%n", component, message, Thread.currentThread().getName());
    }
}
