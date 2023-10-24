package nl.inholland.finalproject.Controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import nl.inholland.finalproject.Database.Database;
import nl.inholland.finalproject.Model.OrderItem;
import nl.inholland.finalproject.Model.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductPopOutController implements Initializable {
    @FXML
    private Label productErrorMessage;
    @FXML
    private TextField quantityInput;
    @FXML
    private Button addOrderbtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TableColumn<Product, Integer> stock;
    @FXML
    private TableColumn<Product, String> name;
    @FXML
    private TableColumn<Product, String> category;
    @FXML
    private TableColumn<Product, Double> price;
    @FXML
    private TableColumn<Product, String> description;
    @FXML
    private TableView productTableView;
    private Database database;
    private List<OrderItem> selectedOrderItems=new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            ObservableList<Product> products = FXCollections.observableArrayList(database.getAllProducts());
            productTableView.setItems(products);
            stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            category.setCellValueFactory(new PropertyValueFactory<>("category"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));

            productTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public ProductPopOutController(Database database) {
        this.database = database;
    }
    @FXML
    public void addToOrderClick(){
        try{
            ObservableList<Product> selectedProductsFromTable = productTableView.getSelectionModel().getSelectedItems();
            int quantity = Integer.parseInt(quantityInput.getText());
            if(quantity==0){
                productErrorMessage.setText("Please select a product to add to the order");
                return;
            }
            else if (quantity <= 0) {
                return;
            }
            for (Product product : selectedProductsFromTable) {
                int currentStock = product.getStock();
                if (currentStock >= quantity) {
                    OrderItem orderItem = new OrderItem(product, quantity);
                    selectedOrderItems.add(orderItem);
                    product.setStock(currentStock - quantity);
                }
                else{
                    productErrorMessage.setText("Not enough stock");
                }
            }
            quantityInput.clear();
            productTableView.refresh();
        } catch (NumberFormatException e) {
            productErrorMessage.setText("Please enter a quantity");
        }
    }
    @FXML
    public void onCancelButton(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
    public List<OrderItem> getSelectedOrderItems() {
        return selectedOrderItems;
    }
}
