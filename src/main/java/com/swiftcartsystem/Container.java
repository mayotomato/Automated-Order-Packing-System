/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.swiftcartsystem;

/**
 *
 * @author zechn
 */
import java.util.ArrayList;
import java.util.List;

public class Container {
    private final List<Batch> batches = new ArrayList<>();
    public static final Container terminate = createTerminateContainer();

    private static Container createTerminateContainer() {
        Container c = new Container();
        c.batches.add(Batch.terminate);
        return c;
    }
    
    public void addBatch(Batch batch) {
        batches.add(batch);
    }

    public int totalBoxes() {
        return batches.stream().mapToInt(b -> b.getBoxes().size()).sum();
    }

    public boolean isFull() {
        return totalBoxes() >= 30;
    }

    public List<Batch> getBatches() {
        return batches;
    }
}

