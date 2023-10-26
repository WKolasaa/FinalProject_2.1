package nl.inholland.finalproject.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import nl.inholland.finalproject.Database.Database;
import nl.inholland.finalproject.HelloApplication;
import nl.inholland.finalproject.Model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private HBox hbox;
    private Database database;
    private User loggedUser;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadScene("dashboard-view.fxml", new DashboardController(database));
    }
    public void logUser(User user){
        loggedUser = user;
        DashboardController dashboardController = new DashboardController(database);
        dashboardController.setUser(user);
        loadScene("dashboard-view.fxml", dashboardController);
    }

    public void loadScene(String name, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(name));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            if (hbox.getChildren().size() > 1)
                hbox.getChildren().remove(1);
            hbox.getChildren().add(scene.getRoot());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void dashboardClick(ActionEvent actionEvent) {
        logUser(loggedUser);
    }

    public void createOrderClick(ActionEvent actionEvent) {
        loadScene("createOrder-view.fxml", new CreateOrderController(database));
    }

    public void productInventoryClick(ActionEvent actionEvent){
        loadScene("inventory-view.fxml", new InventoryController(database));
    }

    public void orderHistoryClick(ActionEvent actionEvent){

    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}