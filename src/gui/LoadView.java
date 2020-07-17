package gui;

import db.DatabaseManager;
import entities.Entity;
import entities.Template;
import game.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.List;

public class LoadView {

    public ComboBox loadGameCombo;

    @FXML
    public void goBackAction(ActionEvent actionEvent)throws IOException {
        Parent settingsView = FXMLLoader.load(getClass().getResource("mainView.fxml"));

        Scene scene2 = new Scene(settingsView);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    @FXML
    public void loadAction(ActionEvent actionEvent) {
        DatabaseManager dbMangager = new DatabaseManager();
        List<Entity> games = (dbMangager.getAllOf(GameManager.class));
        loadGameCombo.getItems().addAll(games);
        loadGameCombo.setValue(games.iterator().next());
        loadGameCombo.setConverter(new StringConverter<GameManager>() {

            @Override
            public String toString(GameManager object) {
                return object.getActiveGame().toString();
            }


            @Override
            public GameManager fromString(String string) {
                GameManager t = (GameManager) loadGameCombo.getItems().stream().filter(ap ->
                        ap.toString().equals(string)).findFirst().orElse(null);
                System.out.println(t);
                return t;
            }
    });
}
}
