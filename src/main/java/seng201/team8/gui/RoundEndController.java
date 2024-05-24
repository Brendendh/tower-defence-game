package seng201.team8.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team8.services.GameManager;
import seng201.team8.services.RoundEndService;

/**
 * The controller class for RoundEndController.
 * <p></p>
 * Responsible for the communication between the user input and the RoundEndService.
 */
public class RoundEndController {

    /**
     * The {@link GameManager} for accessing information and launching into new screens.
     */
    private final GameManager gameManager;
    /**
     * The {@link RoundEndService} for calculating rewards for the player and
     * deciding on the next screen.
     */
    private final RoundEndService roundEndService;

    @FXML
    private Button nextButton;
    @FXML
    private Label xpGainedLabel, pointsGainedLabel, moneyGainedLabel;
    @FXML
    private Label resultLabel;

    /**
     * The controller class for the {@link RoundEndController}.
     * <p></p>
     * Takes in a {@link GameManager}, creates a RoundEndService, and stores it
     * in the {@link RoundEndController}.
     * @param gameManager {@link GameManager}
     */
    public RoundEndController(GameManager gameManager) {
        this.gameManager = gameManager;
        roundEndService = new RoundEndService(gameManager);
    }

    /**
     * Runs when the {@link RoundEndController} is initialized. Sets the screen
     * up depending on if the player won or lost this round.
     */
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

    /**
     * Sets the screen up for player's victory.
     */
    private void setupVictoryScreen(){
        resultLabel.setText("Victory");
        nextButton.setText("Next Round");
    }

    /**
     * Sets the screen up for player's defeat.
     */
    private void setupDefeatScreen(){
        resultLabel.setText("Defeat");
        nextButton.setText("Back to Game Start");
    }

    /**
     * Displays the rewards the player will receive.
     * @param money {@link Integer}
     * @param points {@link Integer}
     * @param expPoints {@link Integer}
     */
    private void displayStats(int money, int points, int expPoints){
        xpGainedLabel.setText(String.valueOf(expPoints));
        pointsGainedLabel.setText(String.valueOf(points));
        moneyGainedLabel.setText(String.valueOf(money));
    }

    /**
     * Applies the rewards to the players {@link seng201.team8.models.dataRecords.InventoryData}
     * and {@link seng201.team8.models.dataRecords.GameData}.
     * @param money {@link Integer}
     * @param points {@link Integer}
     * @param expPoints {@link Integer}
     */
    private void applyStats(int money, int points, int expPoints){
        roundEndService.applyStats(money, points, expPoints);
    }

    /**
     * Determines where the player will go next.
     * <p></p>
     * If the player has won last round, the player will either, go to the victory screen if the
     * player has reached the target round, or randomly decide if the player will go to the
     * random event screen or the round selector screen.
     * <p></p>
     * If the player has lost last round, the player will be directed to the "Game Start" screen.
     */
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
