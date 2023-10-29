package nl.inholland.finalproject.Controller;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import nl.inholland.finalproject.Database.Database;
import nl.inholland.finalproject.HelloApplication;
import nl.inholland.finalproject.Model.Order;
import nl.inholland.finalproject.Model.OrderItem;
import nl.inholland.finalproject.Service.OrderService;
import nl.inholland.finalproject.Service.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateOrderController implements Initializable {
    private ObservableList<OrderItem> observableList;
    private Database database;

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TableView<OrderItem> orderViewTable;
    @FXML
    private TableColumn<OrderItem, Integer> quantity;
    @FXML
    private TableColumn<OrderItem, String> name;
    @FXML
    private TableColumn<OrderItem, String> category;
    @FXML
    private TableColumn<OrderItem, Double> price;
    @FXML
    private Button createOrder;

    private OrderService orderService;
    private ProductService productService;
    @FXML
    public void onAddProductClick(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("productPopOut-view.fxml")); //TODO change to product-view.fxml
            ProductPopOutController controller = new ProductPopOutController(database); // TODO DO those stuff
            fxmlLoader.setController(controller);

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            stage.setTitle("Select Product");
            stage.setScene(scene);

            stage.showAndWait();

            observableList.addAll(controller.getSelectedOrderItems());
            createOrder.setDisable(observableList.isEmpty());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void onDeleteProductClick(){
        orderViewTable.getSelectionModel().getSelectedItems().forEach(orderItem ->
                productService.updateInventory(orderItem.getProduct().getId(), orderItem.getQuantity())
        );
        observableList.removeAll(orderViewTable.getSelectionModel().getSelectedItems());

        createOrder.setDisable(observableList.isEmpty());
    }
    @FXML
    public void onCreateOrder(){
        if (!checkInput()) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Order");
        alert.setHeaderText("Are you sure you want to create this order?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Order currentOrder = new Order(firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText());
            currentOrder.setOrderedItems(new ArrayList<>(observableList));
            orderService.addOrder(currentOrder);
            System.out.println(database.getAllOrders().size());
            refresh();
            resetFields();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            observableList = FXCollections.observableArrayList();
            orderViewTable.setItems(observableList);

            quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getProduct().getName()));
            category.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getProduct().getCategory()));
            price.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProduct().getPrice()));

            orderViewTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            createOrder.setDisable(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public CreateOrderController(Database database) {
        this.database = database;
        this.productService = new ProductService(database);
        this.orderService = new OrderService(database);
    }

    private boolean checkInput(){
        TextField[] fields = {firstName, lastName, email, phoneNumber};

        for (TextField field : fields) {
            String text = field.getText().trim();
            boolean isValid = false;

            if (field == firstName || field == lastName) {
                isValid = text.matches("[a-zA-Z]+");
            } else if (field == email) {
                isValid = text.contains("@");
            } else if (field == phoneNumber) {
                isValid =text.matches("\\+\\d+");
            }

            field.setStyle(isValid ? "" : "-fx-border-color: red;");
            if (!isValid) return false;
        }

        return true;
    }

    private void refresh(){
        observableList.clear();
        orderViewTable.refresh();
        createOrder.setDisable(true);
    }

    private void resetFields(){
        firstName.clear();
        lastName.clear();
        email.clear();
        phoneNumber.clear();
    }
}
