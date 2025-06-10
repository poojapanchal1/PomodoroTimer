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

public class FocusController {
    private Timeline timeline;
    //for back button from focus to cancel/reset the session
    @FXML
    private void switchToSecondary(ActionEvent event) throws IOException {

        timeline.stop();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("secondary.fxml"));
        Parent root = loader.load();

        SecondaryController controller = loader.getController();
        ObservableList<String> sessionTimes = FXCollections.observableArrayList("1 hr", "2 hrs", "3 hrs");
        controller.setSessionDropDown(sessionTimes);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    public void switchToBreak(Stage stage, String goalText, Long sessionTimeRemaining, long originalTime, int switchCount) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(App.class.getResource("break.fxml"));
        Parent root = loader.load();

        BreakController breakController = loader.getController();
        breakController.setGoalText(goalText);
        breakController.setTimer(originalTime);
        breakController.setSessionTimeRemaining(sessionTimeRemaining);
        breakController.setSwitchCount(switchCount);
        
        stage.getScene().setRoot(root);
        breakController.startTimer(stage);
    }

    private long timeRemaining;

    private long sessionTimeRemaining;

    private int switchCount;

    @FXML
    private Label goalLabel;

    @FXML
    private Label sessionTimerLabel;

    @FXML
    private Label focusTimerLabel;

    @FXML
    public void setGoalText(String goalInput) {
        goalLabel.setText(goalInput);
    }

    @FXML
    public void setTimer(Long time){
        timeRemaining = time;
        focusTimerLabel.setText(formatTimeString(time));
    }

    @FXML
    public void setSessionTimeRemaining(Long time){
        sessionTimeRemaining = time;
        sessionTimerLabel.setText(formatTimeString(time));
    }

    @FXML
    public Long getSessionTimeRemaining(){
        return Long.parseLong(sessionTimerLabel.getText());
    }

    public void setSwitchCount(int count){
        switchCount = count;
    }

    @FXML
    private String formatTimeString(Long time){
        long hrs = time / 3600;
        long mins = (time % 3600) / 60;
        long secs = time % 60;
        if (hrs == 0){
            return String.format("%02d:%02d", mins, secs);
        }
        return String.format("%02d:%02d:%02d", hrs, mins, secs);
    }

    public void startTimer(Stage stage){
        long originalTime = timeRemaining;
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeRemaining --;
                sessionTimeRemaining --;
                setTimer(timeRemaining);
                setSessionTimeRemaining(sessionTimeRemaining);
                if (timeRemaining <= 0) {
                    timeline.stop();
                    try{
                        switchCount ++;
                        switchToBreak(stage, goalLabel.getText(), sessionTimeRemaining, originalTime, switchCount);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }));
        timeline.play();
    }
}
