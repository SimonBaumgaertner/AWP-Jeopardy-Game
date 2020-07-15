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

    Question question;

    @FXML
    public void initialize() {
        question = new Question(null, "This is a Question", "This is the answer");
        topTextArea.setText(question.getStatement());
        topTextArea.setVisible(true);
    }

    @FXML
    public void closeQuestion(ActionEvent actionEvent)throws IOException {
        Stage stage = (Stage) closeQuestionButton.getScene().getWindow();
        stage.close();
    }



    @FXML
    public void antwortButtonAction(ActionEvent actionEvent)throws IOException {
        bottomTextArea.setText(question.getAnswer());
        bottomTextArea.setVisible(true);
    }

}
