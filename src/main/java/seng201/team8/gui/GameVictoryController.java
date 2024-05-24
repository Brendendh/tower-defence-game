package seng201.team8.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team8.services.GameManager;

/**
 * The Controller class responsible for handling the GUI elements of the
 * Game Victory screen.
 */
public class GameVictoryController {
    /**
     * The label that is meant to display the player's game stats.
     * Its text is set upon initialization.
     */
    @FXML
    private Label statLabel;
    /**
     * The game {@link GameManager}
     */
    private final GameManager gameManager;

    /**
     * The constructor for controller. Takes in a {@link GameManager} as a parameter
     * to store and access the game's player data and Gui Manager to launch the Game Start
     * Screen.
     * @see seng201.team8.services.GameGUIManager
     * @see seng201.team8.models.dataRecords.GameData
     * @param gameManager current game {@link GameManager}
     */
    public GameVictoryController(GameManager gameManager){
        this.gameManager = gameManager;
    }

    /**
     * Calls the gui manager's launch screen method from the {@link GameManager} to
     * launch the "Game Start" screen.
     * @see {@link seng201.team8.services.GameGUIManager#launchScreen(String)}
     */
    @FXML
    void moveToGameStartScreen() {
        gameManager.getGameGUIManager().launchScreen("Game Start");
    }

    /**
     * Is ran upon screen initialization and immediately sets the text for {@link GameVictoryController#statLabel}
     * to display the amount of money and points the player has left by getting the data from {@link GameManager}
     *
     * @see GameManager#getGameData()
     */
    public void initialize(){
        int money = gameManager.getGameData().getMoney();
        int point = gameManager.getGameData().getPoint();
        statLabel.setText("Won with "+money+" money and " +point+" points left");
    }
}
