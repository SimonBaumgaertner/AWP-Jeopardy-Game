package gui;

import entities.Entity;
import entities.Game;
import entities.Player;
import entities.Template;
import db.DatabaseManager;
import game.GameManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.List;

public class  SettingsView {
    @FXML
    public TextField player1Name;
    @FXML
    public TextField player2Name;
    @FXML
    ComboBox settingCombo;

    @FXML
    public void startGame(ActionEvent actionEvent) throws IOException {
        Template chosenTemplate = (Template) settingCombo.getValue();

        createPlayedGame(chosenTemplate);

        Parent gameView = FXMLLoader.load(getClass().getResource("gameView.fxml"));
        Scene scene = new Scene(gameView);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    public void initialize() {
        DatabaseManager dbMangager = new DatabaseManager();
        List<Entity> templates = (dbMangager.getAllOf(Template.class));
       settingCombo.getItems().addAll(templates);
       settingCombo.setValue(templates.iterator().next());
       settingCombo.setConverter(new StringConverter<Template>() {

            @Override
            public String toString(Template object) {
                return object.getTemplateName();
            }

            @Override
            public Template fromString(String string) {
                Template t = (Template) settingCombo.getItems().stream().filter(ap ->
                        ap.toString().equals(string)).findFirst().orElse(null);
                System.out.println(t);
                return t;
            }
        });
    }

    public void createPlayedGame(Template template){
        GameManager gameManager = new GameManager();
        gameManager.startGame(template, player1Name.getText(), player2Name.getText());
    }

    public void goBackAction(ActionEvent actionEvent) throws IOException {
        Parent mainView = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        Scene scene = new Scene(mainView);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);

        stage.show();

    }
}
