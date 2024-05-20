package seng201.team8.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team8.services.GameManager;
import seng201.team8.services.RoundEndService;

public class RoundEvaluationController {

    private final GameManager gameManager;
    private final RoundEndService roundEndService;

    @FXML
    private Button nextButton;
    @FXML
    private Label xpGainedLabel, pointsGainedLabel, moneyGainedLabel;
    @FXML
    private Label resultLabel;

    public RoundEvaluationController(GameManager gameManager) {
        this.gameManager = gameManager;
        roundEndService = new RoundEndService();
    }

    public void initialize() {
        if(gameManager.getGameWon()){
            setupVictoryScreen();
        } else {
            setupDefeatScreen();
        }

        setupStats();
        applyStats();
    }

    private void setupVictoryScreen(){
        resultLabel.setText("Victory");
        nextButton.setText("Next Round");
    }

    private void setupDefeatScreen(){
        resultLabel.setText("Defeat");
        nextButton.setText("Show Results");
    }

    private void setupStats(){
        // Change values on the screen
    }

    private void applyStats(){
        // Add stats to player and towers
    }

    @FXML
    private void onNextClicked(){
        if(gameManager.getGameWon()){
            gameManager.getGameData().setRound(gameManager.getGameData().getRound() + 1);
            if(roundEndService.isRandomEventPlayed()) {
                gameManager.getGameGUIManager().launchScreen("Random Event");
            } else{
                gameManager.getGameGUIManager().launchScreen("Round Selector");
            }
        } else {
            // gameManager.getGameData().launchScreen("Game Result");
        }
    }


}
