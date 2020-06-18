package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotResult;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {

    @FXML
    public void startGame(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent gameView = FXMLLoader.load(getClass().getResource("gameView.fxml"));

        Scene scene2 = new Scene(gameView);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    @FXML
    public void exitGame(ActionEvent actionEvent)throws IOException {
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
