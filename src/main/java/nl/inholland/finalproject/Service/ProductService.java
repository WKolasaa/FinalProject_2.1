package nl.inholland.finalproject.Service;

import nl.inholland.finalproject.Database.Database;
import nl.inholland.finalproject.Model.Product;

import java.io.Serializable;
import java.util.List;

public class ProductService implements Serializable {
    private Database database;

    public ProductService(Database database) {
        this.database = database;
    }

    public List<Product> getAllProducts() {
        return database.getAllProducts();
    }

    public void updateInventory(int productId, int quantity) {
        database.updateProductInventory(productId, quantity);
    }

    public void addProduct(Product product) {
        database.addProduct(product);
    }

    public void updateProduct(Product product) {
        database.updateProduct(product);
    }

    public void removeProduct(Product product) {
        database.removeProduct(product);
    }
}
