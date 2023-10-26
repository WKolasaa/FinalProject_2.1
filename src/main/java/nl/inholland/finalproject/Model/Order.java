package nl.inholland.finalproject.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDateTime dateTime;
    private List<OrderItem> orderedItems;
    private double totalPrice;

    public Order(String firstName, String lastName, String email, String phoneNumber) {
        orderedItems = new ArrayList<>();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateTime = LocalDateTime.now();
        this.totalPrice = calculateTotalPrice();
    }

    public double calculateTotalPrice(){
        double totalPrice = 0;
        for (OrderItem item : orderedItems) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    public void setOrderedItems(List<OrderItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public List<OrderItem> getOrderedItems() {
        return orderedItems;
    }
}
