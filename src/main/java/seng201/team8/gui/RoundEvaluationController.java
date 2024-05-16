package seng201.team8.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team8.services.GameManager;
import seng201.team8.services.RoundEvaluationService;

public class RoundEvaluationController {

    private GameManager gameManager;
    private RoundEvaluationService roundEvaluationService;

    @FXML
    private Button nextButton;
    private Label xpGainedLabel, pointsGainedLabel, moneyGainedLabel;
    private Label resultLabel;

    private boolean result;

    public RoundEvaluationController(GameManager gameManager) {
        this.gameManager = gameManager;
        roundEvaluationService = new RoundEvaluationService(gameManager);
        result = roundEvaluationService.evaluateRound();
        if(result){
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
        if(result){
            gameManager.getGameData().setRound(gameManager.getGameData().getRound() + 1);
            gameManager.getGameGUIManager().launchScreen("Game Menu");
        } else {
            // gameManager.getGameData().launchScreen("Game Result");
        }
    }


}
