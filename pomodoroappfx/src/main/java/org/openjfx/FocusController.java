package org.openjfx;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FocusController extends CommonController{

    @FXML
    private Label sessionTimerLabel;

    @FXML
    private Label goalLabel;

    @FXML
    public void setGoalText(String goalInput) {
        goalLabel.setText(goalInput);
    }

    @FXML
    public void setSessionTimeRemaining(Long time){
        sessionTimeRemaining = time;
        sessionTimerLabel.setText(formatTimeString(time));
    }

    public void setSwitchCount(int count){
        switchCount = count;
    }

    @Override
    public void setTimer(Long time) {
        super.setTimer(time);
    }

    public void startTimer(Stage stage){
        long originalTime = timeRemaining;
        super.startTimer(stage, () -> {
            try{
                switchCount ++;
                switchToBreak(stage, goalLabel.getText(), sessionTimeRemaining, originalTime, switchCount);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void switchToBreak(Stage stage, String goalLabel, Long sessionTimeRemaining, long originalTime, int switchCount) throws IOException {
    
        FXMLLoader loader = new FXMLLoader(App.class.getResource("break.fxml"));
        Parent root = loader.load();

        BreakController breakController = loader.getController();
        breakController.setGoalText(goalLabel);
        breakController.setTimer(originalTime);
        breakController.setSessionTimeRemaining(sessionTimeRemaining);
        breakController.setSwitchCount(switchCount);
        breakController.setNextScreenLabel(switchCount);

        stage.getScene().setRoot(root);

        breakController.startTimer(stage);
    }

     @Override
    public void switchToSecondary(ActionEvent event) throws IOException {
        super.switchToSecondary(event);
    }
}