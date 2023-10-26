package nl.inholland.finalproject.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import nl.inholland.finalproject.Database.Database;
import nl.inholland.finalproject.Database.UserDatabase;
import nl.inholland.finalproject.HelloApplication;
import nl.inholland.finalproject.Model.Exceptions;
import nl.inholland.finalproject.Model.User;
import nl.inholland.finalproject.Service.UserService;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label labelLogin;

    @FXML
    private Button cancelButton;

    private Database database;

    public void loginButtonOnAction(ActionEvent actionEvent) {
        authenticate(usernameField.getText(), passwordField.getText());
    }

    private void authenticate(String userName, String password){
        UserService userService = new UserService(database);
        try{
            if(userService.isValid(userName, password)){
                User user = userService.getUserByUsername(userName);
                loadMainScene(user);
            }else{
                labelLogin.setText("Login failed. Please try again.");
            }
        }catch (Exceptions.UserNotFoundException e){
            labelLogin.setText("Invalid username");
        }catch (Exceptions.InvalidPasswordException e){
            labelLogin.setText("Invalid password");
        }
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public LoginController() {

    }

    private void loadMainScene(User loggedUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            HBox mainRoot = fxmlLoader.load();

            HelloController mainController = fxmlLoader.getController();
            mainController.logUser(loggedUser);
            mainController.setDatabase(database);

            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.setWidth(900);
            currentStage.setHeight(550);
            mainController.setStage(currentStage);

            if (currentStage.getScene() == null) {
                Scene scene = new Scene(mainRoot);

                currentStage.setScene(scene);
            } else {
                currentStage.getScene().setRoot(mainRoot);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
