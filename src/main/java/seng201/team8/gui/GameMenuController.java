package seng201.team8.gui;

import javafx.fxml.FXML;
import seng201.team8.services.GameManager;

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
}
