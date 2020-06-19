package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameView implements Initializable {

    @FXML
    public Label Count;
    public Button d400;
    public Label nameFieldGame;

    @FXML
    public void closeGame(ActionEvent actionEvent)throws IOException {

        Parent mainView = FXMLLoader.load(getClass().getResource("mainView.fxml"));

        Scene scene = new Scene(mainView);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void d400Buttons(ActionEvent actionEvent)throws IOException {

        int points = Integer.valueOf(Count.getText());
        points = points + 400;
        Count.setText(String.valueOf(points));
        d400.setDisable(true);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
