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
    }

    @FXML
    private void onNextClicked(){}

    @FXML
    private void onInventoryClicked(){}
}
