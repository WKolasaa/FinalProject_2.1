package nl.inholland.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.inholland.finalproject.Controller.LoginController;
import nl.inholland.finalproject.Database.Database;

import java.io.IOException;

public class HelloApplication extends Application {
    private Database database;
    @Override
    public void start(Stage stage) throws Exception {
        database = Database.deserialize();
        if (database == null) {
            database = new Database();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        LoginController loginController = fxmlLoader.getController();
        loginController.setDatabase(database);

        stage.setTitle("Final Project");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            database.serialize();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}