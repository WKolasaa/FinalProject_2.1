module nl.inholland.finalproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.inholland.finalproject to javafx.fxml;
    exports nl.inholland.finalproject;
}