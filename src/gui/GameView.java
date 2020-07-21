package gui;

import db.DatabaseManager;
import entities.Category;
import game.GameManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameView implements Initializable {

    @FXML
    public Label Count;

    @FXML
    public Label k1;
    @FXML
    public Label k2;
    @FXML
    public Label k3;
    @FXML
    public Label k4;
    @FXML
    public Label k5;
    @FXML
    public Label k6;
    @FXML
    public Label player1name;
    @FXML
    public Label player2name;
    @FXML
    public Label balance1;
    @FXML
    public Label balance2;
    @FXML
    public Button player1turn;
    @FXML
    public Button player2turn;


    GameManager gm;

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

    private void showQuestion(int category, int row) throws IOException {
        GameManager gm = new GameManager();
        gm.setActiveQuestion(gm.getQuestionMatrix()[category][row]);
        Parent questionView = FXMLLoader.load(getClass().getResource("questionView.fxml"));
        Scene scene = new Scene(questionView);
        Stage window = new Stage();
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                update();
            }
        });
        window.setScene(scene);
        window.initStyle(StageStyle.UNDECORATED);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gm = new GameManager();
        Category[] categories = gm.getCategories();
        updatePlayerBoard();
        player1name.setText(gm.getPlayers()[0].getPlayerName());
        player2name.setText(gm.getPlayers()[1].getPlayerName());

        k1.setText(categories[1].getCategoryName());
        k2.setText(categories[2].getCategoryName());
        k3.setText(categories[3].getCategoryName());
        k4.setText(categories[4].getCategoryName());
        k5.setText(categories[5].getCategoryName());
        k6.setText(categories[6].getCategoryName());


    }

    @FXML
    public void openQuestion(ActionEvent actionEvent)throws IOException {
        String buttonName = ((Button) actionEvent.getSource()).getId();
        String[] split = buttonName.split("k|d");
        int category = Integer.valueOf(split[1]);
        int row = Integer.valueOf(split[2]) / 400;
        showQuestion(category, row);
    }

    private void update() {
        updatePlayerBoard();
        updateFields();
        checkFinished();
    }



    private void updatePlayerBoard() {
        if (gm.getActivePlayer() == gm.getPlayers()[0]) {
            player1turn.setVisible(true);
            player2turn.setVisible(false);
        }
        if (gm.getActivePlayer() == gm.getPlayers()[1]) {
            player1turn.setVisible(false);
            player2turn.setVisible(true);
        }
        balance1.setText("$" + gm.getPlayers()[0].getPoints());
        balance2.setText("$" + gm.getPlayers()[1].getPoints());
    }


    private void updateFields() {
        Stage stage = (Stage) player1turn.getScene().getWindow();
        String buttonName = "k" + gm.getCategoryMap().get(gm.getActiveQuestion().getField().getCategory())+ "d" + gm.getActiveQuestion().getField().getRowNumber() * 400;
        Button button = (Button) player1turn.getScene().lookup("#"+ buttonName);
        button.setDisable(true);
        button.setVisible(false);
    }

    private void checkFinished() {
        // check if there are unanswered Questions
    }

    public void saveGame(ActionEvent actionEvent) {
        DatabaseManager db = new DatabaseManager();
        db.openTransaction();
        db.persist(gm.getActiveGame(), gm.getPlayers()[0], gm.getPlayers()[1]);
        for(int i = 1; i < gm.getQuestionMatrix().length -1; i++){
            for (int j = 1; j < gm.getQuestionMatrix()[0].length-1; j++){
                db.update(gm.getQuestionMatrix()[i][j]);
            }
        }
        db.commitTransaction();
    }
}
