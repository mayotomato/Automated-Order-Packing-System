/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.swiftcartsystem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zechn
 */
public class Batch {
    private final List<Box> boxes = new ArrayList<>();

    public void addBox(Box box) {
        boxes.add(box);
    }

    public boolean isFull() {
        return boxes.size() == 6;
    }

    public List<Box> getBoxes() {
        return boxes;
    }
}
