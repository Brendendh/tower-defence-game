package seng201.team8.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team8.models.Resource;
import seng201.team8.services.GameManager;

import java.util.ArrayList;

/**
 * The Controller for the Game Menu Screen.
 * <p></p>
 * Used to set the buttons functionality to navigate to the Shop Screen, Inventory Screen or Round Evaluation Screen.
 */

public class GameMenuController {
    /**
     * The GameManager object to launch at different screen.
     */
    private final GameManager gameManager;
    @FXML
    private Label difficultyLabel;

    @FXML
    private Label moneyLabel;

    @FXML
    private Label playerName;

    @FXML
    private Label pointLabel;

    @FXML
    private Label resourceType;

    @FXML
    private Label roundCounter;

    /**
     * The constructor for a GameMenuController object.
     * <p></p>
     * Takes in a GameManager object.
     *
     * @param gameManager the GameManager for launching screens.
     */
    public GameMenuController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * launches to the Shop Screen. Linked to a Button onAction event.
     */
    @FXML
    private void onShopClicked(){
        gameManager.getGameGUIManager().launchScreen("Shop Screen");
    }

    /**
     * launches to the Round Evaluation Screen. Linked to a Button onAction event.
     */
    @FXML
    private void onNextClicked(){
        gameManager.getGameGUIManager().launchScreen("Round Evaluation");
    }

    /**
     * launches to the Inventory Screen. Linked to a Button onAction event.
     */
    @FXML
    private void onInventoryClicked(){
        gameManager.getGameGUIManager().launchScreen("Inventory Screen");
    }

    /**
     * Sets the label in the game menu screen to display:
     * <p>
     * The current game's difficulty by calling {@link GameMenuController#updateDifficultyLabel(Integer)}
     * <p></p>
     * The player's current money and point amount
     * <p></p>
     * The player's current name
     * <p></p>
     * The current round count
     * <p></p>
     * and the upcoming round's {@link Resource} types required by calling {@link GameMenuController#updateResourceDisplay()}
     */
    public void initialize(){
        updateDifficultyLabel(gameManager.getGameData().getDifficulty());
        moneyLabel.setText("Money: "+gameManager.getGameData().getMoney());
        playerName.setText("Good luck, "+gameManager.getGameData().getPlayerName()+"!");
        pointLabel.setText("Point: "+gameManager.getGameData().getPoint());
        updateResourceDisplay();
        roundCounter.setText("Round "+gameManager.getGameData().getRound()+"/"+gameManager.getGameData().getTargetRound());
    }

    /**
     * Sets the label to display the game's difficulty mode based on the {@link Integer} parameter.
     * <p></p>
     * If the Integer = 0, the difficulty label is set to "Normal"
     * <p>
     *     If the Integer = 1, the difficulty label is set to "Hard"
     * </p>
     * @param difficulty the difficulty {@link Integer}
     */
    private void updateDifficultyLabel(Integer difficulty){
        if (difficulty == 0){
            difficultyLabel.setText("Difficulty: Normal");
        } else if (difficulty == 1) {
            difficultyLabel.setText("Difficulty: Hard");
        }
    }

    /**
     * Sets the label to display the upcoming required {@link Resource} types.
     */
    private void updateResourceDisplay(){
        ArrayList<Resource> resources = gameManager.getRoundResourceDisplay();
        StringBuilder resourceString = new StringBuilder("Possible cart resource type: ");
        for (Resource resource : resources){
            resourceString.append(resource).append(" ");
        }
        resourceType.setText(resourceString.toString());
    }
}
