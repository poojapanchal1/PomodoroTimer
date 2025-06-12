package org.openjfx;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LongBreakController extends CommonController {
    
    @Override
    public void switchToSecondary(ActionEvent event) throws IOException {
        super.switchToSecondary(event);
    }
    @Override
    public void switchToFocus(Stage stage, Long sessionTimeRemaining, long originalTime, int switchCount, String goalLabel)
            throws IOException {
        super.switchToFocus(stage, sessionTimeRemaining, originalTime, switchCount, goalLabel);
    }

    @Override
    public void setSwitchCount(int count) {
        super.setSwitchCount(count);
    }

    @FXML
    private Label longBreakSessionTimerLabel;

    private String goalText;

    public void setGoalText(String goalText){
        this.goalText = goalText;
    }
    
    public String getGoalText() {
        return goalText;
    }

    @Override
    public void setTimer(Long time) {
        super.setTimer(time);
    }

    @Override
    public void setSessionTimeRemaining(Long time) {
        super.setSessionTimeRemaining(time);
    }

    @Override
    public Long getSessionTimeRemaining() {
        return super.getSessionTimeRemaining();
    }

    public void startTimer(Stage stage){
        super.startTimer(stage, () -> {
            try{
                if (switchCount < 4){
                    long defaultTimer = (long) 5;
                    switchToFocus(stage, sessionTimeRemaining, defaultTimer, switchCount, goalText);
                } else {
                    switchCount = 0;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

