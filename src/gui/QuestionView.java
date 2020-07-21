package gui;

import entities.Question;
import game.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;



public class QuestionView {

    @FXML
    Button antwortButton;
    @FXML
    TextArea topTextArea;
    @FXML
    TextArea bottomTextArea;
    @FXML
    Button correctButton;
    @FXML
    Button wrongButton;

    Question question;

    GameManager gm;
    @FXML
    public void initialize() {
        gm = new GameManager();
        question = gm.getActiveQuestion();
        topTextArea.setWrapText(true);
        topTextArea.setText(question.getStatement());
        topTextArea.setVisible(true);
    }

    @FXML
    public void answerButtonAction(ActionEvent actionEvent)throws IOException {
        bottomTextArea.setWrapText(true);
        bottomTextArea.setText(question.getAnswer());
        bottomTextArea.setVisible(true);
        correctButton.setVisible(true);
        wrongButton.setVisible(true);
    }

    @FXML
    public void correct(ActionEvent actionEvent) {
        gm.getActivePlayer().setPoints(gm.getActivePlayer().getPoints() + gm.getActiveQuestion().getField().getRowNumber() * 400);
        close();
    }

    @FXML
    public void wrong(ActionEvent actionEvent) {
        close();
    }

    private void close() {
        gm.answerQuestion(gm.getActiveQuestion());
        gm.turnSwap();
        Stage stage = (Stage) wrongButton.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }


}
