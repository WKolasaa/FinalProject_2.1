package nl.inholland.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    public void loginButtonOnAction(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        UserDatabase userDatabase = new UserDatabase();
        if (userDatabase.isValid(username, password)) {
            labelLogin.setText("Login successful!");
        } else {
            labelLogin.setText("Invalid login/password!");
        }
    }
}
