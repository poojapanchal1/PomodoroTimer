package org.openjfx;

import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FocusController extends CommonController {

    @FXML
    private Label goalLabel;

    @FXML
    private Label breakTimerLabel;

    @FXML
    public Label focusTimerLabel;

    @FXML
    public void setGoalText(String goalText){
        goalLabel.setText(goalText);
    }

    @Override
    public void switchToSecondary(ActionEvent event) throws IOException {
        super.switchToSecondary(event);
    }

    public void switchToBreak(Stage stage, String goalText, Long sessionTimeRemaining, long originalTime, int switchCount, Label breakTimerLabel) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(App.class.getResource("break.fxml"));
        Parent root = loader.load();

        BreakController breakController = loader.getController();
        breakController.setGoalText(goalText);
        breakController.setTimer(originalTime, breakTimerLabel);
        breakController.setSessionTimeRemaining(sessionTimeRemaining);
        breakController.setSwitchCount(switchCount);
        
        stage.getScene().setRoot(root);
        breakController.startTimer(stage);
    }

    public void startTimer(Stage stage){
        long originalTime = timeRemaining;
        super.startTimer(stage, () -> {
            try {
                switchCount++;
                switchToBreak(stage, goalLabel.getText(), sessionTimeRemaining, originalTime, switchCount, breakTimerLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
