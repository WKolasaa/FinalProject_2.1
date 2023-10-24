package nl.inholland.finalproject.Controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.inholland.finalproject.Database.Database;
import nl.inholland.finalproject.HelloApplication;
import nl.inholland.finalproject.Model.OrderItem;

import java.io.IOException;
import java.net.URL;
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
    @FXML
    public void onAddProductClick(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("productPopOut-view.fxml")); //TODO change to product-view.fxml
            ProductPopOutController controller = new ProductPopOutController(database); // TODO DO those stuff
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void onDeleteProductClick(){

    }
    @FXML
    public void onCreateOrder(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            observableList = FXCollections.observableArrayList();
            orderViewTable.setItems(observableList);

            quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            category.setCellValueFactory(new PropertyValueFactory<>("category"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));

            orderViewTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            createOrder.setDisable(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public CreateOrderController(Database database) {
        this.database = database;
    }
}
