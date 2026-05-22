module com.muhammadhamza.algoquest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.muhammadhamza.algoquest to javafx.fxml;
    exports com.muhammadhamza.algoquest;
}