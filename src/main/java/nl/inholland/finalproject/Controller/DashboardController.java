package nl.inholland.finalproject.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import nl.inholland.finalproject.Database.Database;
import nl.inholland.finalproject.Model.User;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Label welcomeUser;
    @FXML
    private Label userRole;
    @FXML
    private Label dateTime;

    private User user;
    private Database database;

    public DashboardController(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void update(){
        try {
            if(user == null) {
                return;
            }
            welcomeUser.setText("Welcome back " + user.getUsername());
            userRole.setText(user.getRole().toString());

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a");
            String formattedDateTime = now.format(formatter);
            dateTime.setText(formattedDateTime);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
