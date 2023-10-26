package nl.inholland.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.inholland.finalproject.Controller.LoginController;
import nl.inholland.finalproject.Database.Database;

import java.io.*;

public class HelloApplication extends Application {
    private Database database;
    @Override
    public void start(Stage stage) throws Exception {
        loadData();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        LoginController loginController = new LoginController();
        loginController.setDatabase(database);
        fxmlLoader.setController(loginController);
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Final Project");
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {saveData();});
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void loadData(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("database.dat"))) {
            database = (Database) ois.readObject();
        } catch (FileNotFoundException e) {
            database = new Database();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            database = new Database();
        } catch (Exception e) {
            e.printStackTrace();
            database = new Database();
        }
    }

    private void saveData(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.dat"))) {
            oos.writeObject(database);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}