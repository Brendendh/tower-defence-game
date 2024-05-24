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
     * The label that displays the game result.
     */
    @FXML
    private Label result;
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
     * to display the player stats by getting the data from {@link GameManager}
     *
     * @see GameManager#getGameData()
     */
    public void initialize(){
        int money = gameManager.getGameData().getMoney();
        int point = gameManager.getGameData().getPoint();
        String playerName = gameManager.getGameData().getPlayerName();
        int currentRoundNumber = gameManager.getGameData().getRound();
        int targetRoundNumber = gameManager.getGameData().getTargetRound();
        if (gameManager.getRoundWon()){
            result.setText("VICTORY");
            statLabel.setText(playerName+" won with "+money+" money and "+point+" points left after "+targetRoundNumber+" rounds.");
        }
        else{
            result.setText("DEFEAT");
            statLabel.setText(playerName+ " lost with "+money+" money and " +point+" points left , only reaching round " +
                    currentRoundNumber +" out of "+targetRoundNumber);
        }
    }
}
