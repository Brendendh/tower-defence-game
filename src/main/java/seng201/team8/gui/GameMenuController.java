package seng201.team8.gui;

import javafx.fxml.FXML;
import seng201.team8.services.GameManager;

public class GameMenuController {
    private GameManager gameManager;
    public GameMenuController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @FXML
    private void onShopClicked(){
        gameManager.getGameGUIManager().launchScreen("Shop Screen");
    }

    @FXML
    private void onNextClicked(){
        gameManager.getGameGUIManager().launchScreen("Loading Screen");
    }

    @FXML
    private void onInventoryClicked(){
        gameManager.getGameGUIManager().launchScreen("Inventory Screen");
    }
}
