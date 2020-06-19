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
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameView implements Initializable {

    @FXML
    public Label Count;
    public Label nameFieldGame;
    public Button k1d400;
    public Button k2d400;
    public Button k3d400;
    public Button k4d400;

    @FXML
    public void closeGame(ActionEvent actionEvent)throws IOException {

        Parent mainView = FXMLLoader.load(getClass().getResource("mainView.fxml"));

        Scene scene = new Scene(mainView);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    // TODO this function should be in QuestionView
    private void disableButtonAndCount(Button button, int value){
        int points = Integer.valueOf(Count.getText());
        points = points + value;
        Count.setText(String.valueOf(points));
        button.setDisable(true);
    }

    private void showQuestion(String category, int value) throws IOException {

        Parent questionView = FXMLLoader.load(getClass().getResource("questionView.fxml"));

        Scene scene = new Scene(questionView);
        Stage window = new Stage();
        window.setScene(scene);
        window.initStyle(StageStyle.UNDECORATED);
        window.show();

        //TODO get from the database the designated questions in this category and with this value
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void d400k1button(ActionEvent actionEvent)throws IOException {
        showQuestion("K1",400);
    }

    public void d400k2button(ActionEvent actionEvent) {
    }

    public void d400k3button(ActionEvent actionEvent) {
    }

    public void d400k4button(ActionEvent actionEvent) {
    }
}
