package lk.ijse.Live_Chat_Application.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientController extends Thread{
    public TextField txtMessage;
    public ScrollPane txtArea;
    public Label txtClientName;
    public VBox messageArea;

    Socket socket;
    PrintWriter printWriter;
    BufferedReader bufferedReader;

    public void initialize(){
        this.txtClientName.setText(LoginController.clientName);
        connectSocket();
    }

    private void connectSocket(){
        try {
            socket = new Socket("localhost",3001);
            System.out.println("Connect With Server......");
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            printWriter = new PrintWriter(socket.getOutputStream(),true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            while (true) {
                String msg = bufferedReader.readLine();
                System.out.println("Message : "+msg);
                String[] array = msg.split(" :");
                String name = array[0];
                System.out.println("Client name : "+name);
                StringBuilder message = new StringBuilder();
                for (int i = 1; i < array.length ; i++) {
                    message.append(array[i]);
                }
                System.out.println("New Msg : "+message);
                System.out.println();
                if (name.equalsIgnoreCase(LoginController.clientName)){
                    continue;
                }else if (message.toString().equalsIgnoreCase("bye")){
                    break;
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        HBox hBox = new HBox();
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.setPadding(new Insets(5,10,5,5));
                        Text text = new Text(msg);
                        text.setStyle("-fx-font-size: 15px");
                        TextFlow textFlow = new TextFlow(text);
                        textFlow.setStyle("-fx-color:rgb(239,242,255);"
                                + "-fx-background-color: rgb(62,155,224);" +
                                "-fx-background-radius: 20px");
                        textFlow.setPadding(new Insets(5,10,5,10));
                        text.setFill(Color.color(0,0,0));
                        hBox.getChildren().add(textFlow);
                        messageArea.getChildren().add(hBox);
                    }
                });
            }
            bufferedReader.close();
            printWriter.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendOnAction(ActionEvent actionEvent) {
        send();
    }

    private void send(){
        String msg = txtMessage.getText();
        printWriter.println(LoginController.clientName + " : " + msg);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5,5,5,10));
        Text text = new Text("Me : "+msg);
        text.setStyle("-fx-font-size: 15px");

        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-color:rgb(239,242,255);"
                + "-fx-background-color: rgb(62,155,224);" +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5,10,5,10));
        text.setFill(Color.color(0,0,0));
        hBox.getChildren().add(textFlow);
        messageArea.getChildren().add(hBox);
        printWriter.flush();
        txtMessage.setText(null);
        if (msg.equalsIgnoreCase("bye")){
        System.exit(0);
        }
    }
}
