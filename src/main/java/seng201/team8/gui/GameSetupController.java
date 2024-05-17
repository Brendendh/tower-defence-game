package seng201.team8.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import seng201.team8.exceptions.NoSpaceException;
import seng201.team8.models.InventoryData;
import seng201.team8.models.Tower;
import seng201.team8.services.GameManager;
import seng201.team8.services.GameSetupService;
import seng201.team8.services.InventoryManager;

import java.util.List;

public class GameSetupController {
    private GameManager gameManager;
    private GameSetupService gameSetupService;
    public GameSetupController(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameSetupService = new GameSetupService();
        gameSetupService.setDifficulty(0);
        gameSetupService.setTargetRound(10);}

    @FXML
    private Button tower1Button, tower2Button, tower3Button, tower4Button, tower5Button;

    @FXML
    private Button selectedTower1Button, selectedTower2Button, selectedTower3Button;

    @FXML
    private Label resourceAmountLabel, cooldownLabel, resourceTypeLabel;

    @FXML
    private TextField towerNameTextField, playerNameTextField;

    @FXML
    private ImageView towerImage;

    private int selectedTowerIndex = -1;

    private Tower[] AddedTowers = new Tower[3];

    public void initialize() {
        initializeTowerButtons();

    }

    private void initializeTowerButtons(){
        List<Button> towerButtons = List.of(tower1Button, tower2Button, tower3Button, tower4Button, tower5Button, selectedTower1Button, selectedTower2Button, selectedTower3Button);

        for (int i = 0; i < towerButtons.size(); i++) {
            int finalI = i;
            towerButtons.get(i).setOnAction(event -> {
                if (finalI >= 5){
                    if (AddedTowers[finalI-5] != null){
                        updateStats(AddedTowers[finalI-5]);
                    }
                    else{
                        displayNull();
                    }
                } else {
                    updateStats(gameManager.getDefaultTowers()[finalI]);
                }
                selectedTowerIndex = finalI;
                towerButtons.forEach(button -> {if(button == towerButtons.get(finalI)) {
                    button.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
                } else {
                        button.setStyle("");
                    }
                });
            });
        }
    }

    private void updateStats(Tower tower) {
        resourceAmountLabel.setText(String.valueOf(tower.getTowerStats().getResourceAmount()));
        cooldownLabel.setText(String.valueOf(tower.getTowerStats().getCooldown()));
        resourceTypeLabel.setText(String.valueOf(tower.getTowerStats().getResourceType()));
        towerNameTextField.setText(tower.getName());
    }

    private void displayNull() {
        resourceAmountLabel.setText("None");
        cooldownLabel.setText("None");
        resourceTypeLabel.setText("None");
        towerNameTextField.setText("None");
    }

    @FXML
    private void on5RoundsClicked() {gameSetupService.setTargetRound(5);}
    @FXML
    private void on10RoundsClicked() {gameSetupService.setTargetRound(10);}
    @FXML
    private void on15RoundsClicked() {gameSetupService.setTargetRound(15);}
    @FXML
    private void onNormalClicked() {gameSetupService.setDifficulty(0);}
    @FXML
    private void onHardClicked() {gameSetupService.setDifficulty(1);}
    @FXML
    private void onStartGameClicked() {
        gameSetupService.setName(playerNameTextField.getText());
        gameManager.setGameData(gameSetupService.getGameData());
        InventoryData inventoryData = new InventoryData();
        InventoryManager inventoryManager = new InventoryManager(inventoryData);
        for(Tower tower : AddedTowers){
            try {
                inventoryManager.moveToMain(tower);
            } catch (NoSpaceException e) {
                throw new RuntimeException(e);
            }
        }
        gameManager.setInventoryManager(inventoryManager);
        gameManager.getGameGUIManager().launchScreen("Round Selector");
    }
    @FXML
    private void onAddTowerClicked() {
        if (selectedTowerIndex != -1) {
            for (int i = 0; i < AddedTowers.length; i++) {
                if (AddedTowers[i] == null) {
                    Tower tempTower = gameManager.getDefaultTowers()[selectedTowerIndex].clone();
                    tempTower.setName(towerNameTextField.getText());
                    AddedTowers[i] = tempTower;
                    return;
                }
            }
        }
    }
    @FXML
    private void onRemoveTowerClicked() {
        if (selectedTowerIndex >= 5) {
            AddedTowers[selectedTowerIndex-5] = null;
            displayNull();
        }
    }
}
