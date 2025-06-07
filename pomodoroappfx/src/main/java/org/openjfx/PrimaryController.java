package org.openjfx;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    private void switchToSecondary(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(App.class.getResource("secondary.fxml"));
        Parent root = loader.load();

        SecondaryController controller = loader.getController();
        ObservableList<String> sessionTimes = FXCollections.observableArrayList("1 hr", "2 hr", "3 hr");
        controller.setSessionDropDown(sessionTimes);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
