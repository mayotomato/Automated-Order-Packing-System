package main.java.com.swiftcartsystem;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author zechn
 */
public class Order {
    private final int id;
    private boolean rejected = false;

    public Order(int id) {
        this.id = id;
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
}
