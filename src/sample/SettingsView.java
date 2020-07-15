package sample;

import Entities.Template;
import db.DatabaseManager;
import javafx.collections.ObservableList;
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
import java.util.LinkedList;
import java.util.List;

public class  SettingsView {
    @FXML
    ComboBox settingCombo;

    public void startGame(ActionEvent actionEvent) throws IOException {
        Parent gameView = FXMLLoader.load(getClass().getResource("gameView.fxml"));

        Scene scene2 = new Scene(gameView);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    @FXML
    public void initialize() {
        DatabaseManager dbMangager = new DatabaseManager();
       settingCombo.getItems().addAll((dbMangager.getAllOf(Template.class)));
        settingCombo.setConverter(new StringConverter<Template>() {

            @Override
            public String toString(Template object) {
                return object.getTemplateName();
            }

            @Override
            public Template fromString(String string) {
                return (Template) settingCombo.getItems().stream().filter(ap ->
                        ap.toString().equals(string)).findFirst().orElse(null);
            }
        });
    }
    @FXML
    public void choseTemplate(ActionEvent actionEvent) {

    }
}
