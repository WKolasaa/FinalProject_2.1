package nl.inholland.finalproject.Model;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    public Product getProduct() {
        return product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return product.getPrice() * quantity;
    }
    public double getUnitPrice() {
        return product.getPrice();
    }
    public String getName() {
        return product.getName();
    }
    public String getCategory() {
        return product.getCategory();
    }
}
