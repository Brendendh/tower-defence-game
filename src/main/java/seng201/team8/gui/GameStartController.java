package seng201.team8.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import seng201.team8.services.GameManager;

/**
 * The controller class for GameStartController.
 * <p></p>
 * Manages the button inputs in the GameStartScreen.
 */
public class GameStartController {
    /**
     * The {@link GameManager} object for launching
     * to another screen.
     */
    private final GameManager gameManager;

    /**
     * The constructor for the {@link GameStartController}
     * <p></p>
     * Takes in a {@link GameManager} object and stores it in the GameStartController.
     * @param gameManager {@link GameManager}
     */
    public GameStartController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Launches to the "Game Setup" Screen.
     */
    @FXML
    private void onStartClicked(){
        gameManager.getGameGUIManager().launchScreen("Game Setup");
    }

    /**
     * Exits the application.
     */
    @FXML
    private void onExitClicked(){
        Platform.exit();
    }
}
