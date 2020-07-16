package gui;

import entities.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;



public class QuestionView {

    @FXML
    Button closeQuestionButton;
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

    @FXML
    public void initialize() {

        // String s = antwortButton.getParent().getScene().getRoot().getId();
        question = new Question(null, "mündliche oder schriftliche Erwiderung, Entgegnung", "Was ist Antwort?");
        topTextArea.setText(question.getStatement());
        topTextArea.setVisible(true);
    }

    @FXML
    public void closeQuestion(ActionEvent actionEvent)throws IOException {
        close();
    }



    @FXML
    public void antwortButtonAction(ActionEvent actionEvent)throws IOException {
        bottomTextArea.setText(question.getAnswer());
        bottomTextArea.setVisible(true);
        correctButton.setVisible(true);
        wrongButton.setVisible(true);
    }

    @FXML
    public void correct(ActionEvent actionEvent) {
        close();
    }

    @FXML
    public void wrong(ActionEvent actionEvent) {
        close();
    }

    private void close() {
        Stage stage = (Stage) closeQuestionButton.getScene().getWindow();
        stage.close();
    }


}
