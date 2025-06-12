package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BreakController extends CommonController {

    @FXML
    private Label nextScreenLabel;

    @Override
    public void setTimer(Long time) {
        super.setTimer(time);
    }

    private String goalText;

    public void setGoalText(String goalText){
        this.goalText = goalText;
    }
    
    public String getGoalText() {
        return goalText;
    }
  
    public void setNextScreenLabel(int switchCount){
        if (switchCount == 4){
            nextScreenLabel.setText("until your longer break");
        } else {
            nextScreenLabel.setText("until focus time");
        }
    }

    @Override
    public void setSessionTimeRemaining(Long time) {
        super.setSessionTimeRemaining(time);
    }

    @Override
    public Long getSessionTimeRemaining() {
        return super.getSessionTimeRemaining();
    }

    @Override
    public void setSwitchCount(int count) {
        super.setSwitchCount(count);
    }

    @Override
    public void switchToFocus(Stage stage, Long sessionTimeRemaining, long originalTime, int switchCount, String goalLabel)
            throws IOException {
        super.switchToFocus(stage, sessionTimeRemaining, originalTime, switchCount, goalLabel);
    }

    public void switchToLongBreak(Stage stage, Long sessionTimeRemaining, long originalTime, int switchCount) throws IOException {

        FXMLLoader loader = new FXMLLoader(App.class.getResource("longbreak.fxml"));
        Parent root = loader.load();

        LongBreakController longBreakController = loader.getController();
        longBreakController.setTimer((long) 10);
        longBreakController.setSessionTimeRemaining(sessionTimeRemaining);
        longBreakController.setSwitchCount(switchCount);

        stage.getScene().setRoot(root);
        longBreakController.startTimer(stage);
    }

    public void startTimer(Stage stage){
        long originalTime = timeRemaining;
        super.startTimer(stage, () -> {
            try {
                if (switchCount < 4){
                switchToFocus(stage, sessionTimeRemaining, originalTime, switchCount, goalText);
            } else {
                switchCount = 0;
                switchToLongBreak(stage, getSessionTimeRemaining(), originalTime, switchCount);
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
