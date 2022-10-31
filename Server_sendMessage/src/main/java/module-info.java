module com.example.server_sendmessage {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.server_sendmessage to javafx.fxml;
    exports com.example.server_sendmessage;
}