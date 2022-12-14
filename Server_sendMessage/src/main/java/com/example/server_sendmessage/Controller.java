package com.example.server_sendmessage;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

//test
public class Controller implements Initializable {
//autre petit test
    @FXML
    private Button button_send;
    @FXML
    private TextField message;
    @FXML
    private VBox VBox;
    @FXML
    private ScrollPane sPane;
    private Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            server = new Server(new ServerSocket(1666));
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("erreur");
        }

        VBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sPane.setVvalue((Double) t1);
            }
        });

        server.receiveMessageFromClient(VBox);

        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String messageToSend = message.getText();
                if(!messageToSend.isEmpty()){
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    hBox.getChildren().add(textFlow);
                    VBox.getChildren().add(hBox);

                    server.sendMessageToClient(messageToSend);
                    message.clear();
                }
            }
        });
    }

    public static void addLabel(String messageFromClient, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);

        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }

}