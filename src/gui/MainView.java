package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {

    @FXML
    public TextField playerName;

    @FXML
    public void startGame(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent settingsView = FXMLLoader.load(getClass().getResource("settingsView.fxml"));

        Scene scene2 = new Scene(settingsView);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    @FXML
    public void exitGame(ActionEvent actionEvent)throws IOException {
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }

    public void loadGame(ActionEvent actionEvent) throws IOException {
        Parent loadView = FXMLLoader.load(getClass().getResource("loadView.fxml"));

        Scene scene2 = new Scene(loadView);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }
}
