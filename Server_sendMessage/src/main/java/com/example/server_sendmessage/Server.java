package com.example.server_sendmessage;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket server;
    private Socket client;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Server(ServerSocket server){
        try {
            this.server = server;
            this.client = server.accept();
            this.reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("erreur");
            closeEverythingth(client,writer,reader);
        }
    }

    public void sendMessageToClient(String sendMessageToClient){
        try {
            writer.write(sendMessageToClient);
            writer.newLine();
            writer.flush();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("erreur");
            closeEverythingth(client,writer,reader);
        }
    }

    public void receiveMessageFromClient(VBox vBox){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (client.isConnected()){
                    try {
                        String messageFromClient = reader.readLine();
                        Controller.addLabel(messageFromClient,vBox);
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
                client.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
