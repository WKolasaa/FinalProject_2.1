package nl.inholland.finalproject.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private String fullName;

    public Order(String firstName, String lastName, String email, String phoneNumber) {
        orderedItems = new ArrayList<>();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateTime = LocalDateTime.now();
        this.totalPrice = calculateTotalPrice();
        this.fullName = firstName + " " + lastName;
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
    public String getFullName(){
        return fullName;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public String getFormatedDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        return dateTime.format(formatter);
    }
}
