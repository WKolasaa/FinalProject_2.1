module nl.inholland.finalproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.inholland.finalproject to javafx.fxml;
    exports nl.inholland.finalproject;
    exports nl.inholland.finalproject.Controller;
    opens nl.inholland.finalproject.Controller to javafx.fxml;
    exports nl.inholland.finalproject.Model;
    opens nl.inholland.finalproject.Model to javafx.fxml;
    exports nl.inholland.finalproject.Database;
    opens nl.inholland.finalproject.Database to javafx.fxml;
}