package seng201.team8.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team8.services.GameManager;

public class GameVictoryController {

    @FXML
    private Label statLabel;

    private final GameManager gameManager;

    public GameVictoryController(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @FXML
    void moveToGameStartScreen() {
        gameManager.getGameGUIManager().launchScreen("Game Start");
    }

    public void initialize(){
        int money = gameManager.getGameData().getMoney();
        int point = gameManager.getGameData().getPoint();
        statLabel.setText("Won with "+money+" money and " +point+" points left");
    }
}
