package org.openjfx;

import java.io.IOException;

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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BreakController extends CommonController {

    @FXML
    private String goalLabelText;

    @FXML
    private Label nextScreenLabel;

   @FXML
    public void setGoalText(String goalText){
        goalLabelText = goalText;
    }
  
    public void setNextScreenLabel(int switchCount){
        if (switchCount == 4){
            nextScreenLabel.setText("until your longer break");
        } else {
            nextScreenLabel.setText("until focus time");
        }
    }

    public void switchToLongBreak(Stage stage, Long sessionTimeRemaining, int switchCount) throws IOException {

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
                switchToFocus(stage, sessionTimeRemaining, originalTime, switchCount);
            } else {
                switchCount = 0;
                switchToLongBreak(stage, getSessionTimeRemaining(), switchCount);
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        timeline.play();
    }
}
