package nl.inholland.finalproject.Model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private int stock;
    private String name;
    private String category;
    private double price;
    private String description;
    private int i=0;

    public Product(int stock, String name, String category, double price, String description) {
        this.id = i++;
        this.stock = stock;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public int getStock() {
        return stock;
    }
    public double getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public String getDescription() {
        return description;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setName(String text) {
        this.name = text;
    }
    public void setCategory(String text) {
        this.category = text;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setDescription(String text) {
        this.description = text;
    }
    public Product getProduct() {
        return this;
    }

}
