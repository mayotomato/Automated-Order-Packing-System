/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.swiftcartsystem.threads;

/**
 *
 * @author zechn
 */
import main.java.com.swiftcartsystem.*;
import main.java.com.swiftcartsystem.utils.*;

import java.util.concurrent.*;
import java.util.*;

public class Truck implements Runnable {
    private final BlockingQueue<Container> loadingQueue;

    public Truck(BlockingQueue<Container> loadingQueue) {
        this.loadingQueue = loadingQueue;
    }

    public void run() {
        List<Container> containers = new ArrayList<>();
        while (true) {
            try {
                Container container = loadingQueue.take();
                containers.add(container);
                Logger.log("Truck", "Loaded container. Current count: " + containers.size());

                if (containers.size() >= 18) {
                    Logger.log("Truck", "Fully loaded with 18 containers. Departing.");
                    containers.clear();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
