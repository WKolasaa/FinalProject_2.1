package nl.inholland.finalproject.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.inholland.finalproject.Model.Order;
import nl.inholland.finalproject.Model.OrderItem;
import nl.inholland.finalproject.Model.Product;
import nl.inholland.finalproject.Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database implements Serializable {
    private List<User> users = new ArrayList<>();;
    private Map<Integer, Product> products = new HashMap<>();
    private static final long serialVersionUID = 315557262292413227L;
    private List<Order> orders = new ArrayList<>();

    public Database() {
        //addData();
    }

    private void addData(){
        User user1 = new User("admin", "admin", User.Role.Manager);
        User user2 = new User("user", "user", User.Role.Sales);

        users.add(user1);
        users.add(user2);

        Product product1 = new Product(1, "Guitar","Electric", 1000, "Guitar That is Electric");
        Product product2 = new Product(2, "Drums","Acoustic", 2000, "Drums That is Acoustic");
        Product product3 = new Product(3, "Piano","Electric", 3000, "Piano That is Electric");
        Product product4 = new Product(4, "Violin","Acoustic", 4000, "Violin That is Acoustic");

        products.put(product1.getId(), product1);
        products.put(product2.getId(), product2);
        products.put(product3.getId(), product3);
        products.put(product4.getId(), product4);
    }

    public void serialize() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.dat"))) {
            oos.writeObject(oos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Database deserialize() {
        Database database;
        try (FileInputStream fileIn = new FileInputStream("database.dat");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            database = (Database) in.readObject();
        } catch (IOException | ClassNotFoundException c) {
            c.printStackTrace();
            database = new Database();
        }
        return database;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void addProduct(Product product){
        products.put(product.getId(), product);
    }
    public List<Product> getAllProducts(){
        return new ArrayList<>(products.values());
    }

    public void updateProduct(Product product){
        products.put(product.getId(), product);
    }

    public void removeProduct(Product product){
        products.remove(product.getId());
    }

    public void addOrder(Order order){
        orders.add(order);
    }
    public ObservableList<Order> getAllOrders(){
        return FXCollections.observableArrayList(orders);
    }

    public List<OrderItem> getOrder(Order order){
        return order.getOrderedItems();
    }

    public void updateProductInventory(int productId, int quantity) {
        Product product = products.get(productId);
        if (product != null) {
            product.setStock(product.getStock() + quantity);
        }

    }



}
