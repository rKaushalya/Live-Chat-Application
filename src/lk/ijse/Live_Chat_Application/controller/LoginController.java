package lk.ijse.Live_Chat_Application.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class LoginController {
    public TextField txtUserName;
    public static String clientName;
    public AnchorPane loginPane;
    public JFXButton btnSend;

    public void loadClientPageOnAction(ActionEvent actionEvent) throws IOException {
        clientName = txtUserName.getText();

        URL resource =getClass().getResource("/lk/ijse/Live_Chat_Application/view/Client.fxml");
        Parent load = FXMLLoader.load(resource);
        loginPane.getChildren().clear();
        loginPane.getChildren().add(load);
    }
}
