module com.example.projectnqueen {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projectnqueen to javafx.fxml;
    exports com.example.projectnqueen;
}