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

public class CommonController {

    public Timeline timeline;

    public long timeRemaining;

    public long sessionTimeRemaining;

    public int switchCount;

    @FXML
    public Label sessionTimerLabel;

    @FXML
    public Label focusTimerLabel;

    @FXML
    public Label goalLabel;

    @FXML
    private String goalLabelText;

    @FXML
    public void setSwitchCount(int count){
        switchCount = count;
    }

    @FXML
    public void setTimer(Long time, Label timerLabel){
        timeRemaining = time;
        timerLabel.setText(formatTimeString(time));
    }

    @FXML
    public void setSessionTimeRemaining(Long time){
        sessionTimeRemaining = time;
        sessionTimerLabel.setText(formatTimeString(time));
    }

    @FXML
    public Long getSessionTimeRemaining(){
        String[] parts = sessionTimerLabel.getText().split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        return (long) hours * 3600 + minutes * 60 + seconds;
    }

    @FXML
    public String formatTimeString(Long time){
        long hrs = time / 3600;
        long mins = (time % 3600) / 60;
        long secs = time % 60;
        if (hrs == 0){
            return String.format("%02d:%02d", mins, secs);
        }
        return String.format("%02d:%02d:%02d", hrs, mins, secs);
    }

    @FXML
    public void switchToSecondary(ActionEvent event) throws IOException {
        timeline.stop();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("secondary.fxml"));
        Parent root = loader.load();

        SecondaryController controller = loader.getController();
        ObservableList<String> sessionTimes = FXCollections.observableArrayList("1 hr", "2 hrs", "3 hrs");
        controller.setSessionDropDown(sessionTimes);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    public void switchToFocus(Stage stage, Long sessionTimeRemaining, long originalTime, int switchCount) throws IOException {

        FXMLLoader loader = new FXMLLoader(App.class.getResource("focus.fxml"));
        Parent root = loader.load();

        FocusController focusController = loader.getController();
        focusController.setGoalText(goalLabelText);
        focusController.setTimer(originalTime, focusTimerLabel);
        focusController.setSessionTimeRemaining(sessionTimeRemaining);
        focusController.setSwitchCount(switchCount);

        stage.getScene().setRoot(root);
        focusController.startTimer(stage);
    }

    public void startTimer(Stage stage, Runnable onTimerEnd){
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
                    onTimerEnd.run();
                    // try{
                    //     switchCount ++;
                    //     switchToBreak(stage, goalLabel.getText(), sessionTimeRemaining, originalTime, switchCount);
                    // } catch (IOException e) {
                    //     e.printStackTrace();
                    // }
                }
            }
        }));
        timeline.play();
    }
}
