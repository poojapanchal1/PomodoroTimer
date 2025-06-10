package org.openjfx;

import java.io.IOException;

import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SecondaryController {
    
    @FXML
    private TextField goalInputText;

    @FXML
    private ComboBox<String> sessionDropdown;
    @FXML
    public void setSessionDropDown(ObservableList<String> times) {
        sessionDropdown.setItems(times);
    }

    @FXML
    public Long getSessionTime(){
        String selectedVal = sessionDropdown.getValue();
        Long selectedNum = Long.parseLong(selectedVal.substring(0, 1));
        System.err.println(selectedNum);
        return selectedNum * 3600; //hours to seconds
    }

    @FXML
    private Long getTimerInterval(){
        long interval = getSessionTime();
        long initialTimer = 0;
        if (interval == 2){
            initialTimer = 20;
        } else {
            initialTimer = 25;// 25 5, 25 5, 25 5, 25 5 +25 long break, repeat
        }
        return initialTimer * 60; //mins to seconds
    }

    @FXML
    private void switchToFocus(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("focus.fxml"));
        Parent root = loader.load(); 

        FocusController focusController = loader.getController();
        focusController.setGoalText(goalInputText != null ? goalInputText.getText() : "have a productive session!");
        focusController.setTimer((long)5);
        focusController.setSessionTimeRemaining(getSessionTime());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        focusController.startTimer(stage);
    }

    
}