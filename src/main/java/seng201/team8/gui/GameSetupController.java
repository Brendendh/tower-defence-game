package seng201.team8.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import seng201.team8.models.dataRecords.GameData;
import seng201.team8.models.Tower;
import seng201.team8.services.GameManager;
import seng201.team8.services.GameSetupService;
import seng201.team8.services.InventoryManager;

import java.util.List;

public class GameSetupController {
    private final GameManager gameManager;
    private final GameSetupService gameSetupService;
    public GameSetupController(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameSetupService = new GameSetupService();
        this.gameSetupService.setTargetRound(-1);
        this.gameSetupService.setDifficulty(-1);
    }
    @FXML
    private Label difficultyLabel;
    @FXML
    private Button normalDifficultyButton, hardDifficultyButton;
    @FXML
    private Button round5Button, round10Button,round15Button;

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

    @FXML
    private Button renameTowerButton;

    private int selectedTowerIndex = -1;

    private final Tower[] addedTowers = new Tower[3];
    private List<Button> towerButtons;
    private List<Button> roundButtons;

    public void initialize() {
        initializeTowerButtons();
    }

    private void initializeTowerButtons(){
        this.towerButtons = List.of(tower1Button, tower2Button, tower3Button, tower4Button, tower5Button, selectedTower1Button, selectedTower2Button, selectedTower3Button);
        this.roundButtons = List.of(round5Button,round10Button,round15Button);
        for (int i = 0; i < towerButtons.size(); i++) {
            int finalI = i;
            towerButtons.get(i).setOnAction(event -> {
                updateSelectedTowerStat(finalI);
                selectedTowerIndex = finalI;
                colourSelectedTower(towerButtons, finalI);
            });
        }
    }

    private void updateSelectedTowerStat(int finalI) {
        if (finalI >= 5){
            if (addedTowers[finalI - 5] != null){
                updateStats(addedTowers[finalI-5]);
                displayTowerImage(addedTowers[finalI-5]);
                renameTowerButton.setDisable(false);
            } else{
                displayNull();
                towerImage.setImage(null);
                renameTowerButton.setDisable(true);
            }
        } else {
            renameTowerButton.setDisable(true);
            displayTowerImage(gameManager.getDefaultTowers()[finalI]);
            updateStats(gameManager.getDefaultTowers()[finalI]);
        }
    }

    private static void colourSelectedTower(List<Button> towerButtons, int finalI) {
        towerButtons.forEach(button -> {if(button == towerButtons.get(finalI)) {
            button.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
        } else {
                button.setStyle("");
            }
        });
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

    private void errorPopUp(String message) {
        Dialog<?> errorPane = new Dialog<>();
        errorPane.setContentText(message);
        errorPane.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        errorPane.show();
    }
    private void updateRoundButton(Button roundButton){ //yes I know this is stupid ugly code, but we are in a hurry
        for (Button button : roundButtons){
            if (button == roundButton){
                button.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
            }
            else{
                button.setStyle("");
            }
        }
    }
    @FXML
    private void on5RoundsClicked() {
        gameSetupService.setTargetRound(5);
        updateRoundButton(round5Button);
    }
    @FXML
    private void on10RoundsClicked() {
        gameSetupService.setTargetRound(10);
        updateRoundButton(round10Button);
    }
    @FXML
    private void on15RoundsClicked() {
        gameSetupService.setTargetRound(15);
        updateRoundButton(round15Button);
    }
    @FXML
    private void onNormalClicked() {
        gameSetupService.setDifficulty(0);
        normalDifficultyButton.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
        hardDifficultyButton.setStyle("");
        difficultyLabel.setText("Normal difficulty: All carts resource type is based on the player's tower's resource type");
    }
    @FXML
    private void onHardClicked() {
        gameSetupService.setDifficulty(1);
        hardDifficultyButton.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
        normalDifficultyButton.setStyle("");
        difficultyLabel.setText("Hard difficulty: At least one of the carts resource type is based on the player's tower's resource type");
    }
    @FXML
    private void onStartGameClicked() {
        GameData gameData = gameSetupService.createGameData();

        if(!gameSetupService.checkName(playerNameTextField.getText())){
            errorPopUp("Type a player name between 3 to 15 characters with no special characters.");
        } else if(gameData.getTargetRound() == -1) {
            errorPopUp("Select a target round.");
        } else if(gameData.getDifficulty() == -1) {
            errorPopUp("Select a difficulty.");
        } else if(!isAddedTowersFull()){
            errorPopUp("Select 3 towers.");
        } else {
            gameData.setName(playerNameTextField.getText());
            InventoryManager inventoryManager = gameSetupService.createInventoryManager(gameSetupService.createInventory(addedTowers));
            gameManager.setGameData(gameData);
            gameManager.setShopData(null);
            gameManager.setInventoryManager(inventoryManager);
            gameManager.getGameGUIManager().launchScreen("Round Selector");
        }
    }

    private boolean isAddedTowersFull(){
        for(Tower tower : addedTowers){
            if(tower == null){
                return false;
            }
        }
        return true;
    }

    @FXML
    private void onAddTowerClicked() {
        if (selectedTowerIndex != -1 && selectedTowerIndex < 5) {
            if(gameSetupService.checkName(towerNameTextField.getText())){
                addTowerToAddedTowers();
            } else {
                errorPopUp("Type a tower name between 3 to 15 characters with no special characters.");
            }
        } else {
            errorPopUp("Select a default tower before adding it to the selected towers.");
        }
    }

    private void addTowerToAddedTowers() {
        for (int i = 0; i < addedTowers.length; i++) {
            if (addedTowers[i] == null) {
                Tower tempTower = gameManager.getDefaultTowers()[selectedTowerIndex].clone();
                tempTower.setName(towerNameTextField.getText());
                addedTowers[i] = tempTower;
                towerButtons.get(i+5).setText(towerNameTextField.getText());
                return;
            }
        }
    }

    @FXML
    private void onRenameTowerClicked(){
        if (selectedTowerIndex != -1 && selectedTowerIndex >= 5) {
            if(gameSetupService.checkName(towerNameTextField.getText())){
                addedTowers[selectedTowerIndex-5].setName(towerNameTextField.getText());
            } else {
                errorPopUp("Type a tower name between 3 to 15 characters with no special characters.");
            }
        } else {
            errorPopUp("You can only rename a selected tower");
        }
    }

    @FXML
    private void onRemoveTowerClicked() {
        if (selectedTowerIndex >= 5) {
            addedTowers[selectedTowerIndex-5] = null;
            towerButtons.get(selectedTowerIndex).setText("");
            displayNull();
        } else {
            errorPopUp("You can not remove a default tower");
        }
    }

    private void displayTowerImage(Tower tower){
        Image image = new Image("/images/towers/" + tower.getTowerStats().getResourceType().name().toLowerCase() + ".jpg");
        towerImage.setImage(image);
    }
}
