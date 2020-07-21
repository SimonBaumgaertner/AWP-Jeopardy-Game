package gui;

import db.DatabaseManager;
import entities.Entity;
import entities.Game;
import entities.Template;
import game.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.List;

public class LoadView {

    public ComboBox loadGameCombo;

    @FXML
    public void initialize() {
        DatabaseManager dbMangager = new DatabaseManager();
        List<Entity> games = (dbMangager.getAllOf(Game.class));
        for (Entity game : games) {
            ((Game) game).generateName();
            if (!loadGameCombo.getItems().contains(game)) {
                loadGameCombo.getItems().add(game);
            }
        }

        if (games.size() > 0) {
            loadGameCombo.setValue(games.iterator().next());
        }
        loadGameCombo.setConverter(new StringConverter<Game>() {

            @Override
            public String toString(Game object) {
                return object.getGameName();
            }

            @Override
            public Game fromString(String string) {
                return (Game) loadGameCombo.getItems().stream().filter(ap ->
                        ap.toString().equals(string)).findFirst().orElse(null);
            }
        });
    }


    @FXML
    public void goBackAction(ActionEvent actionEvent)throws IOException {
        Parent settingsView = FXMLLoader.load(getClass().getResource("mainView.fxml"));

        Scene scene2 = new Scene(settingsView);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    @FXML
    public void loadAction(ActionEvent actionEvent) throws IOException {


        Game chosenGame = (Game) loadGameCombo.getValue();

        GameManager gameManager = new GameManager();

        gameManager.loadGame(chosenGame);

        Parent gameView = FXMLLoader.load(getClass().getResource("gameView.fxml"));

        Scene scene2 = new Scene(gameView);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    };

}
