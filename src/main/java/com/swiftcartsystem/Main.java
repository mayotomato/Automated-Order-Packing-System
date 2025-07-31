package main.java.com.swiftcartsystem;

// File: main.java.com.swiftcartsystem/Main.java

import main.java.com.swiftcartsystem.threads.*;
import main.java.com.swiftcartsystem.utils.*;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Order> intakeQueue = new LinkedBlockingQueue<>();
        BlockingQueue<OrderBin> pickingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<OrderBin> packingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Box> labellingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Container> sortingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Container> loadingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Order> rejectedQueue = new LinkedBlockingQueue<>();

        Semaphore loaderSemaphore = new Semaphore(3, true);

        new Thread(new OrderIntakeSystem(intakeQueue)).start();
        new Thread(new PickingStation(intakeQueue, pickingQueue, rejectedQueue)).start();
        new Thread(new PackingStation(pickingQueue, packingQueue, rejectedQueue)).start();
        new Thread(new LabellingStation(packingQueue, labellingQueue, rejectedQueue)).start();
        new Thread(new SortingArea(labellingQueue, sortingQueue)).start();

        for (int i = 0; i < 3; i++) {
            new Thread(new AutonomousLoader(sortingQueue, loadingQueue, loaderSemaphore)).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(new Truck(loadingQueue)).start();
        }

        new Thread(new RejectHandler(rejectedQueue)).start();
    }
}
