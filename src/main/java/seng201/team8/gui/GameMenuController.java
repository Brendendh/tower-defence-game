package seng201.team8.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team8.models.Resource;
import seng201.team8.services.GameManager;

import java.util.ArrayList;

/**
 * The Controller for the Game Menu Screen.
 * <p></p>
 * Used to set the buttons functionality to navigate to the Shop Screen, Inventory Screen or Game Play Screen.
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
     * launches to the Game Play Screen. Linked to a Button onAction event.
     */
    @FXML
    private void onNextClicked(){
        gameManager.getGameGUIManager().launchScreen("Game Play");
    }

    /**
     * launches to the Inventory Screen. Linked to a Button onAction event.
     */
    @FXML
    private void onInventoryClicked(){
        gameManager.getGameGUIManager().launchScreen("Inventory Screen");
    }

    public void initialize(){
        updateDifficultyLabel(gameManager.getGameData().getDifficulty());
        moneyLabel.setText("Money: "+gameManager.getGameData().getMoney());
        playerName.setText("Good luck, "+gameManager.getGameData().getPlayerName()+"!");
        pointLabel.setText("Point: "+gameManager.getGameData().getPoint());
        updateResourceDisplay();
        roundCounter.setText("Round "+gameManager.getGameData().getRound()+"/"+gameManager.getGameData().getTargetRound());
    }

    private void updateDifficultyLabel(Integer difficulty){
        if (difficulty == 0){
            difficultyLabel.setText("Difficulty: Normal");
        } else if (difficulty == 1) {
            difficultyLabel.setText("Difficulty: Hard");
        }
    }

    private void updateResourceDisplay(){
        ArrayList<Resource> resources = gameManager.getRoundResourceDisplay();
        String resourceString = "Possible cart resource type: ";
        for (Resource resource : resources){
            resourceString += resource + " ";
        }
        resourceType.setText(resourceString);
    }
}
