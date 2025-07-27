# Automated Order Packing System

## Project Overview

This project implements a simulation of SwiftCart, a highly automated e-commerce centre, using concurrent programming techniques in Java. The simulation models the end-to-end process of online order fulfillment, from order intake to final dispatch, emphasizing concurrent activities, resource management, and error handling.

The objective of this assignment is to apply appropriate concurrent program methods in implementing a concurrent program from a program specification.

## Learning Outcomes Achieved

This assignment demonstrates the following learning outcomes:

* Application of the concepts of concurrency and parallelism in the construction of a system using a suitable programming language.
* Explanation of the safety aspects of multi-threaded and parallel systems.

## Features Implemented

The simulation incorporates the following key functionalities as per the assignment specifications:

### Basic Requirements

* **Order Intake System:**
    * Orders arrive from the online platform at a rate of 1 order every 500ms.
    * Each order is verified for payment, inventory availability, and shipping address.
* **Picking Station:**
    * Robotic arms pick items from shelves and place them into order bins.
    * Up to 4 orders can be picked at a time.
    * Orders are verified for missing items.
* **Packing Station:**
    * Completed bins are packed into shipping boxes (1 order at a time).
    * A scanner checks each box to ensure contents match the order.
* **Labelling Station:**
    * Each box is assigned a shipping label with destination and tracking.
    * Boxes pass through a quality scanner (1 at a time).
* **Sorting Area:**
    * Boxes are sorted into batches of 6 boxes based on regional zones.
    * Batches are loaded into transport containers (27 boxes per container).
* **Loading Bay & Transport:**
    * 3 autonomous loaders (AGVs) transfer containers to 2 outbound loading bays.
    * Trucks take up to 18 containers and leave for delivery hubs.
    * If both bays are occupied, incoming trucks must wait.

### Additional Requirements

* **Defective Orders:** Orders may be rejected at any stage (e.g., out-of-stock items, packing errors, mislabelling). Defective orders are removed by a reject handler and logged.
* **Capacity Constraints:** The loading bay has space for only 20 containers. If full, packing must pause.
* **Autonomous Loaders (AGVs):**
    * Only 3 loaders are available and can break down randomly (simulated with thread stalls).
    * Trucks can only be loaded when a loader and bay are free.
* **Concurrent Activities:**
    * Loaders and outbound trucks operate concurrently.
    * Congestion is simulated (e.g., 1 truck is waiting while both loading bays are in use).
* **Statistical Report:** When all trucks have departed for the day, the system should print a detailed report:
    * Confirm that all orders, boxes, and containers have been cleared.
    * Maximum, minimum and average loading and wait time per truck.
    * Total number of orders processed, rejected, boxes packed, containers shipped and trucks dispatched.

## Implementation Details

* **Programming Language:** Java
* **Concurrency Model:** Implemented using Java's built-in `Thread` classes, and concurrent programming facilities to manage shared resources, synchronization, and inter-thread communication.
* **Key Concurrent Programming Facilities Utilized:** (You'll fill this in based on your actual implementation, e.g., `java.util.concurrent` package, `synchronized` keyword, `wait()`, `notifyAll()`, etc.)
* **Logging:** Detailed logging for each major activity per thread is provided, including the thread name/ID responsible for the output.
* **Simulation Duration:** The simulation should take about 5 minutes to simulate 600 orders. Orders arrive every 500ms.

## How to Run the Simulation

To compile and run the simulation, follow these steps:

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/your-username/SwiftCart-E-commerce-Simulation.git](https://github.com/your-username/SwiftCart-E-commerce-Simulation.git)
    cd SwiftCart-E-commerce-Simulation
    ```
2.  **Compile the Java code:**
    If you are using Maven/Gradle, you can use:
    ```bash
    # For Maven
    mvn clean install
    # For Gradle
    gradle build
    ```
    If compiling manually:
    ```bash
    javac -d bin src/main/java/com/swiftcart/simulation/*.java src/main/java/com/swiftcart/simulation/util/*.java
    ```
3.  **Run the simulation:**
    If you are using Maven/Gradle, you can use:
    ```bash
    # For Maven
    mvn exec:java -Dexec.mainClass="com.swiftcart.simulation.Main"
    # For Gradle
    gradle run
    ```
    If running manually:
    ```bash
    java -cp bin com.swiftcart.simulation.Main
    ```

The console will display real-time activity logs, followed by the final statistical report upon completion.

## Project Structure
