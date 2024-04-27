package seng201.team8.gui;

import javafx.fxml.FXML;
import seng201.team8.services.GameManager;

public class GameStartController {
    private GameManager gameManager;
    public GameStartController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @FXML
    private void onStartClicked(){
    }

    @FXML
    private void onExitClicked(){}
}
