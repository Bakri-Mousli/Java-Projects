module org.example.algorithms2assiggnment {
    requires javafx.controls;
    requires javafx.fxml;

    // Open the Core package to javafx.base
    opens org.example.algorithms2assiggnment.Core to javafx.base;

    // Export packages (optional for other uses)
    exports org.example.algorithms2assiggnment.Core;
    exports org.example.algorithms2assiggnment;
}
