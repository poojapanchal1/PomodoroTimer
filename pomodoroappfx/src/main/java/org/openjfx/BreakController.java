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

public class BreakController {
    private Timeline timeline;
    //for back button from focus to cancel/reset the session
    @FXML
    private void switchToSecondary(ActionEvent event) throws IOException {

        timeline.stop();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("secondary.fxml"));
        Parent root = loader.load();

        SecondaryController controller = loader.getController();
        ObservableList<String> sessionTimes = FXCollections.observableArrayList("1 hr", "2 hr", "3 hr");
        controller.setSessionDropDown(sessionTimes);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    private int timeRemaining = 5;
    public void switchToFocus(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(App.class.getResource("focus.fxml"));
        Parent root = loader.load();
        root.getStylesheets().add(getClass().getResource("/org/style.css").toExternalForm());

        FocusController focusController = loader.getController();
        
        focusController.setGoalText(goalLabelText);
        
        focusController.setTimer(5);

        stage.getScene().setRoot(root);

        focusController.startTimer(stage);
    }

    @FXML
    private Label breakTimerLabel;

    @FXML
    private String goalLabelText;
   @FXML
    public void setGoalText(String goalText){
        goalLabelText = goalText;
    }
    @FXML
    public void setTimer(Integer time){
        breakTimerLabel.setText(time.toString());
    }

    public void startTimer(Stage stage){
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeRemaining--;
                setTimer(timeRemaining);
                if (timeRemaining <= 0) {
                    timeline.stop();
                    try{
                        switchToFocus(stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }));
        timeline.play();
    }
}
