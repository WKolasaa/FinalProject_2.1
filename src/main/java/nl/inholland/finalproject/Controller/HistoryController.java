package nl.inholland.finalproject.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.inholland.finalproject.Database.Database;
import nl.inholland.finalproject.Model.Order;
import nl.inholland.finalproject.Model.OrderItem;
import nl.inholland.finalproject.Service.OrderService;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    @FXML
    private TableView<Order> orderListView;
    @FXML
    private TableColumn<Order, LocalDateTime> dateTimeColumn;
    @FXML
    private TableColumn<Order, String> orderNameColumn;
    @FXML
    private TableColumn<Order, Double> totalPriceColumn;
    @FXML
    private TableView<OrderItem> historyListView;
    @FXML
    private TableColumn<Order, Integer>quantityColumn;
    @FXML
    private TableColumn<Order, String> productNameColumn;
    @FXML
    private TableColumn<Order, String> productCategoryColumn;
    @FXML
    private TableColumn<Order, Double> productPriceColumn;
    private OrderService orderService;

    public HistoryController(Database database) {
        this.orderService = new OrderService(database);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
            orderNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
            productNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
            productCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("Category"));
            productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

            loadOrders();

            orderListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    loadOrderDetails(newSelection);
                }
            });

            orderListView.refresh();
            historyListView.refresh();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadOrders() {
        orderListView.getItems().clear();
        orderListView.getItems().addAll(orderService.getAllOrders());
    }

    private void loadOrderDetails(Order order) {
        ObservableList<OrderItem> orderItems = FXCollections.observableList(orderService.getOrder(order));
        historyListView.setItems(orderItems);
    }
}
