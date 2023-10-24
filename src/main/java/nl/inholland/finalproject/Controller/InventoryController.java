package nl.inholland.finalproject.Controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.inholland.finalproject.Database.Database;
import nl.inholland.finalproject.Model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {
    @FXML
    private TableView<Product> productsTables;
    @FXML
    private TableColumn<Product, Integer> stock;
    @FXML
    private TableColumn<Product, String> name;
    @FXML
    private TableColumn<Product, Double> price;
    @FXML
    private TableColumn<Product, String> category;
    @FXML
    private TableColumn<Product, String> description;
    @FXML
    private Button addProduct;
    @FXML
    private Button editProduct;
    @FXML
    private Button deleteProduct;
    @FXML
    private Label errorMessageLabel;

    private Database database;
    private Product product;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Product> products = FXCollections.observableArrayList(database.getAllProducts());
            productsTables.setItems(products);
            stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            category.setCellValueFactory(new PropertyValueFactory<>("category"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            productsTables.setRowFactory(tv -> new TableRow<Product>() {
                @Override
                protected void updateItem(Product product, boolean empty) {
                    super.updateItem(product, empty);
                    if (product == null) {
                        setStyle("");
                    } else if (product.getStock() == 0) {
                        setStyle("-fx-background-color: #ff0000");
                    } else if (product.getStock() < 10) {
                        setStyle("-fx-background-color: #ff9900");
                    } else {
                        setStyle("-fx-background-color: #00ff00");
                    }
                }
            });
            productsTables.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (Exception e) {
            errorMessageLabel.setText(e.getMessage());
        }
    }

    public InventoryController(Database database) {
        this.database = database;
        product = null;
    }

    @FXML
    private void onAddInventoryClick(){
        try{
            int tmpStock = Integer.parseInt(stock.getText());
            String tmpName = name.getText();
            double tmpPrice = Double.parseDouble(price.getText());
            String tmpCategory = category.getText();
            String tmpDescription = description.getText();

            if(emptyField(tmpStock, tmpName, tmpPrice, tmpCategory, tmpDescription)){
                errorMessageLabel.setText("Please fill in all fields");
            }

            Product product = new Product(tmpStock, tmpName, tmpCategory, tmpPrice, tmpDescription);

            database.addProduct(product);
            productsTables.getItems().add(product);
            database.serialize();

            clearFields();
        }catch (Exception e){
            errorMessageLabel.setText(e.getMessage());
        }
    }

    private boolean emptyField(int stock, String name, double price, String category, String description){
        return stock == 0 || name.isEmpty() || price == 0 || category.isEmpty() || description.isEmpty();
    }

    private void clearFields(){
        stock.setText("");
        name.setText("");
        price.setText("");
        category.setText("");
        description.setText("");
    }

    @FXML
    private void onEditInventoryClick(){
        try{
            Product selectedProduct = productsTables.getSelectionModel().getSelectedItem();
            if(selectedProduct != null){
                stock.setText(String.valueOf(selectedProduct.getStock()));
                name.setText(selectedProduct.getName());
                price.setText(String.valueOf(selectedProduct.getPrice()));
                category.setText(selectedProduct.getCategory());
                description.setText(selectedProduct.getDescription());
                product = selectedProduct;
            }
        }catch (Exception e){
            errorMessageLabel.setText(e.getMessage());
        }
    }
}
