package nl.inholland.finalproject.Service;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import nl.inholland.finalproject.Database.Database;
import nl.inholland.finalproject.Model.Order;
import nl.inholland.finalproject.Model.OrderItem;

import java.util.List;

public class OrderService {
    private Database database;
    public OrderService(Database database) {
        this.database = database;
    }

    public void addOrder(Order order) {
        database.addOrder(order);
    }

    public ObservableList<Order> getAllOrders() {
        return database.getAllOrders();
    }

    public List<OrderItem> getOrder(Order order){
        return database.getOrder(order);
    }

}
