module com.example.client_sendmessage {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.client_sendmessage to javafx.fxml;
    exports com.example.client_sendmessage;
}