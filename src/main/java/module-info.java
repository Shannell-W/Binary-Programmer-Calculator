module com.example.binarycalculatorfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.binarycalculatorfx to javafx.fxml;
    exports com.example.binarycalculatorfx;
    exports main;
    exports calculator.logic;
}