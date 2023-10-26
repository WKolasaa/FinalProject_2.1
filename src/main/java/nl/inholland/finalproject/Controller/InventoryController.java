package nl.inholland.finalproject.Controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import nl.inholland.finalproject.Database.Database;
import nl.inholland.finalproject.Model.Product;
import nl.inholland.finalproject.Service.ProductService;

import java.net.URL;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {
    @FXML
    private TableView<Product> inventoryViewTable;
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
    @FXML
    private TextField stockTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField priceTF;
    @FXML
    private TextField categoryTF;
    @FXML
    private TextField descriptionTF;
    private ProductService productService;

    private Database database;
    private Product editingProduct;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Product> products = FXCollections.observableList(productService.getAllProducts());
            inventoryViewTable.setItems(products);
            stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            category.setCellValueFactory(new PropertyValueFactory<>("category"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            inventoryViewTable.setRowFactory(tv -> new TableRow<Product>() {
                @Override
                protected void updateItem(Product product, boolean empty) {
                    super.updateItem(product, empty);
                    if (product == null || empty) {
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
            inventoryViewTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            inventoryViewTable.getSortOrder().add(stock);
        } catch (Exception e) {
            errorMessageLabel.setText(e.getMessage());
        }
    }

    public InventoryController(Database database) {
        this.database = database;
        editingProduct = null;
        productService = new ProductService(database);
    }

    @FXML
    private void onAddInventoryClick(ActionEvent event){
        try{
            int tmpStock = Integer.parseInt(stockTF.getText());
            String tmpName = nameTF.getText();
            double tmpPrice = Double.parseDouble(priceTF.getText());
            String tmpCategory = categoryTF.getText();
            String tmpDescription = descriptionTF.getText();

            if(emptyField(tmpStock, tmpName, tmpPrice, tmpCategory, tmpDescription)){
                errorMessageLabel.setText("Please fill in all fields");
                return;
            }

            Product product = new Product(tmpStock, tmpName, tmpCategory, tmpPrice, tmpDescription);

            database.addProduct(product);
            inventoryViewTable.getItems().add(product);
            clearFields();
        }catch (Exception e){
            errorMessageLabel.setText(e.getMessage());
            //System.out.println(e.getMessage());
        }
    }

    private boolean emptyField(int stock, String name, double price, String category, String description){
        return stock == 0 || name.isEmpty() || price == 0 || category.isEmpty() || description.isEmpty();
    }

    private void clearFields(){
        stockTF.clear();
        nameTF.clear();
        priceTF.clear();
        categoryTF.clear();
        descriptionTF.clear();
    }

    @FXML
    private void onEditInventoryClick(ActionEvent event){
        try{
            Product selectedProduct = inventoryViewTable.getSelectionModel().getSelectedItem();
            if(selectedProduct != null){
                importData(selectedProduct);

                stockTF.textProperty().addListener((observable, oldValue, newValue) -> saveChanges());
                nameTF.textProperty().addListener((observable, oldValue, newValue) -> saveChanges());
                priceTF.textProperty().addListener((observable, oldValue, newValue) -> saveChanges());
                categoryTF.textProperty().addListener((observable, oldValue, newValue) -> saveChanges());
                descriptionTF.textProperty().addListener((observable, oldValue, newValue) -> saveChanges());
            }
        }catch (Exception e){
            errorMessageLabel.setText(e.getMessage());
        }
    }

    private void importData(Product selectedProduct){
        stockTF.setText(String.valueOf(selectedProduct.getStock()));
        nameTF.setText(selectedProduct.getName());
        priceTF.setText(String.valueOf(selectedProduct.getPrice()));
        categoryTF.setText(String.valueOf(selectedProduct.getCategory()));
        descriptionTF.setText(selectedProduct.getDescription());
        editingProduct = selectedProduct;
    }

    private void saveChanges(){
        if (editingProduct != null) {
            try {
                editingProduct.setStock(Integer.parseInt(stockTF.getText()));
                editingProduct.setName(nameTF.getText());
                editingProduct.setPrice(Double.parseDouble(priceTF.getText()));
                editingProduct.setCategory((categoryTF.getText()));
                editingProduct.setDescription(descriptionTF.getText());

                productService.updateProduct(editingProduct);

                inventoryViewTable.refresh();
            } catch (Exception e) {
                errorMessageLabel.setText("something went wrong");
            }
        }
    }

    @FXML
    private void onDeleteInventoryClick(ActionEvent event){
        editingProduct = inventoryViewTable.getSelectionModel().getSelectedItem();
        if(editingProduct != null){
            try{
                productService.removeProduct(editingProduct);
                inventoryViewTable.getItems().remove(editingProduct);
                clearFields();
            } catch (Exception e){
                errorMessageLabel.setText(e.getMessage());
            }
        }
    }
}
