package gui;

import db.DatabaseManager;
import entities.Entity;
import entities.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class RankingView implements Initializable {
    public Label placeOne;
    public Label placeTwo;
    public Label placeThree;
    public Label placeFour;
    public Label placeFive;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseManager db = new DatabaseManager();
        List<Entity> players = db.getAllOf(Player.class);
        List<Player> playerList = new ArrayList<>();

        for(int i = 0; i < players.size(); i++){
            playerList.add((Player) players.get(i));
        }

        Collections.sort(playerList);

        List<Label> labelList = getlistOfLabels();
        for(int i = 0; i < playerList.size(); i++){
            labelList.get(i).setText(String.valueOf(i+1) + ". " + playerList.get(i).getPlayerName() + " Score: " + String.valueOf(playerList.get(i).getPoints()));
        }
    }

    public void goBackAction(ActionEvent actionEvent) throws IOException {
        Parent mainView = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        Scene scene = new Scene(mainView);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);

        stage.show();

    }

    private List<Label> getlistOfLabels(){
        List<Label> labels = new ArrayList<>();
        labels.add(placeOne);
        labels.add(placeTwo);
        labels.add(placeThree);
        labels.add(placeFour);
        labels.add(placeFive);

        return labels;
    }
}
