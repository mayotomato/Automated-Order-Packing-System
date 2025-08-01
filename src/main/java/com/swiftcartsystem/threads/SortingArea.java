package main.java.com.swiftcartsystem.threads;

import main.java.com.swiftcartsystem.*;
import main.java.com.swiftcartsystem.utils.*;
import java.util.concurrent.BlockingQueue;

public class SortingArea implements Runnable {
    private final BlockingQueue<Box> labellingQueue;
    private final BlockingQueue<Container> sortingQueue;
    
    public SortingArea(BlockingQueue<Box> labellingQueue, BlockingQueue<Container> sortingQueue) {
        this.labellingQueue = labellingQueue;
        this.sortingQueue = sortingQueue;
    }
    
    @Override
    public void run() {
        Batch currentBatch = new Batch();
        Container currentContainer = new Container();
        int batchCounter = 1;
        int containerCounter = 1;
        
        try {
            while (true) {
                Box box = labellingQueue.take();
                
                if (box == Box.terminate) {
                    // Handle remaining items before shutdown
                    if (!currentBatch.getBoxes().isEmpty()) {
                        currentContainer.addBatch(currentBatch);
                        Logger.log("SortingArea", "Final batch #" + batchCounter + " added to container");
                    }
                    
                    if (!currentContainer.getBatches().isEmpty()) {
                        sortingQueue.put(currentContainer);
                        Logger.log("SortingArea", "Final container #" + containerCounter + " sent for loading (" + 
                                  currentContainer.totalBoxes() + " boxes)");
                    }
                    
                    // Send termination signals to all loaders
                    for (int i = 0; i < 3; i++) {
                        sortingQueue.put(Container.terminate);
                    }
                    Logger.log("SortingArea", "Shutdown signals sent to loaders");
                    break;
                }
                
                // Add box to current batch
                currentBatch.addBox(box);
                Logger.log("SortingArea", "Added Box for Order #" + box.getOrder().getId() + 
                          " to Batch #" + batchCounter);
                
                // Check if batch is full (6 boxes)
                if (currentBatch.isFull()) {
                    currentContainer.addBatch(currentBatch);
                    Logger.log("SortingArea", "Batch #" + batchCounter + " completed and added to Container #" + containerCounter);
                    currentBatch = new Batch();
                    batchCounter++;
                }
                
                // Check if container is full (30 boxes)
                if (currentContainer.isFull()) {
                    sortingQueue.put(currentContainer);
                    ReportGenerator.incrementContainersCreated();
                    Logger.log("SortingArea", "Container #" + containerCounter + " full (30 boxes). Sent for loading.");
                    currentContainer = new Container();
                    containerCounter++;
                }
                
                Thread.sleep(100); // Small delay for processing
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Logger.log("SortingArea", "Interrupted during processing");
        }
    }
}