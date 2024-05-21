package seng201.team8.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team8.services.GameManager;
import seng201.team8.services.RoundEndService;

public class RoundEndController {

    private final GameManager gameManager;
    private final RoundEndService roundEndService;

    @FXML
    private Button nextButton;
    @FXML
    private Label xpGainedLabel, pointsGainedLabel, moneyGainedLabel;
    @FXML
    private Label resultLabel;

    public RoundEndController(GameManager gameManager) {
        this.gameManager = gameManager;
        roundEndService = new RoundEndService(gameManager);
    }

    public void initialize() {
        if(gameManager.getRoundWon()){
            setupVictoryScreen();
        } else {
            setupDefeatScreen();
        }
        int money = roundEndService.getMoney();
        int points = roundEndService.getPoints();
        int expPoints = roundEndService.getExpPoints();
        displayStats(money, points, expPoints);
        applyStats(money, points, expPoints);
    }

    private void setupVictoryScreen(){
        resultLabel.setText("Victory");
        nextButton.setText("Next Round");
    }

    private void setupDefeatScreen(){
        resultLabel.setText("Defeat");
        nextButton.setText("Show Results");
    }

    private void displayStats(int money, int points, int expPoints){
        // Change values on the screen
        xpGainedLabel.setText(String.valueOf(expPoints));
        pointsGainedLabel.setText(String.valueOf(points));
        moneyGainedLabel.setText(String.valueOf(money));
    }

    private void applyStats(int money, int points, int expPoints){
        // Add stats to player and towers
        roundEndService.applyStats(money, points, expPoints);
    }

    @FXML
    private void onNextClicked(){
        if(gameManager.getRoundWon()){
            gameManager.getGameData().setRound(gameManager.getGameData().getRound() + 1);
            if(gameManager.getGameData().getRound() > gameManager.getGameData().getTargetRound()){
                gameManager.getGameGUIManager().launchScreen("Game Victory");
            }
            else if (roundEndService.isRandomEventPlayed()) {
                gameManager.getGameGUIManager().launchScreen("Random Event");
            } else{
                gameManager.getGameGUIManager().launchScreen("Round Selector");
            }
        } else {
            gameManager.getGameGUIManager().launchScreen("Game Start");
        }
    }


}
