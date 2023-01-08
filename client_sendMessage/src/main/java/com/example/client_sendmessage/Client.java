package com.example.client_sendmessage;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket client;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Client(Socket client){
        try {
            this.client = client;
            this.reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("erreur");
            closeEverythingth(client,writer,reader);
        }
    }

    public void sendMessageToServer(String messageToServer){
        try {
            writer.write(messageToServer);
            writer.newLine();
            writer.flush();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("erreur");
            closeEverythingth(client,writer,reader);
        }
    }

    public void receiveMessageFromServer(VBox vBox){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (client.isConnected()){
                    try {
                        String messageFromServer = reader.readLine();
                        Controller.addLabel(messageFromServer,vBox);
                    }catch (IOException e){
                        e.printStackTrace();
                        System.out.println("erreur");
                        closeEverythingth(client,writer,reader);
                        break;
                    }
                }
            }
        }).start();
    }

    public void closeEverythingth(Socket client, BufferedWriter writer, BufferedReader reader){
        try {
            if(reader != null){
                reader.close();
            }
            if(writer != null){
                writer.close();
            }
            if(client != null){
                this.client.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
