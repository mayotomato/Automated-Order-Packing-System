package main.java.com.swiftcartsystem;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author zechn
 */
import java.util.*;

public class Order {
    private final int id;
    private boolean rejected = false;
    private final List<String> contents;

    public Order(int id, List<String> contents) {
        this.id = id;
        this.contents = new ArrayList<>(contents);
    }

    public int getId() {
        return id;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void reject() {
        this.rejected = true;
    }

    public List<String> getContents() {
        return new ArrayList<>(contents);
    }
}

