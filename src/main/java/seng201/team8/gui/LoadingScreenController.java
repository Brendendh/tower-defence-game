package seng201.team8.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import seng201.team8.services.GameManager;

import java.util.Random;

public class LoadingScreenController {

    @FXML
    private Label loadingText;

    @FXML
    private Label randomTextLabel;

    private GameManager gameManager;
    private Random random;

    final private String[] randomMessages = new String[]{"Getting towers ready","Filling up carts","Producing resources","Taking a 15 minute break"};

    public LoadingScreenController(GameManager gameManager){
        this.gameManager = gameManager;
    }
    private void moveToRoundEvaluationScreen(){
        gameManager.getGameGUIManager().launchScreen("Round Evaluation");
    }

    public void initialize(){
        random = new Random();
        String loadingTextTemplate = "Loading...";
        int loadingDuration = random.nextInt(4,7);
        final IntegerProperty currentIndex = new SimpleIntegerProperty(7);
        final IntegerProperty currentTime = new SimpleIntegerProperty(0);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(1),
                event -> {
                    if (currentTime.get() > loadingDuration){
                        timeline.stop();
                        moveToRoundEvaluationScreen();
                    }
                    else{
                        loadingText.setText(loadingTextTemplate.substring(0, currentIndex.get()));
                        randomTextLabel.setText(randomMessages[random.nextInt(0,4)]);
                        if (currentIndex.get() >= loadingTextTemplate.length()){
                            currentIndex.set(7);
                        }
                        else{
                            currentIndex.set(currentIndex.get() + 1);
                        }
                        currentTime.set(currentTime.get()+1);
                    }

                }
        );
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

}

