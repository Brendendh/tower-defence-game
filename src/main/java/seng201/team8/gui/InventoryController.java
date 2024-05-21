package seng201.team8.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import seng201.team8.models.*;
import seng201.team8.models.effects.ResourceAmountBoost;
import seng201.team8.services.GameManager;
import seng201.team8.services.InventoryManager;
import seng201.team8.services.TowerStatsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryController {

    final InventoryManager inventoryManager;

    final GameManager gameManager;

    @FXML
    private Button mainTower1Button, mainTower2Button, mainTower3Button, mainTower4Button, mainTower5Button;

    @FXML
    private Button reserveTower1Button, reserveTower2Button, reserveTower3Button, reserveTower4Button, reserveTower5Button;

    @FXML
    private Label rarityLabel,resourceAmountLabel, cooldownLabel, resourceTypeLabel, levelLabel, expPointsLabel;

    @FXML
    private Button useItemButton, moveTowerButton, renameTowerButton;

    @FXML
    private Label upgradeNameLabel, upgradeDescriptionLabel;

    @FXML
    private TextField towerNameTextField;

    @FXML
    private ImageView towerImageView, upgradeImageView;

    @FXML
    private ListView<Upgrade> upgradesListView;

    private String selectedInventoryItemType;
    private int selectedInventoryUpgradeIndex;
    private int selectedInventoryTowerIndex;
    private boolean[] selectedTowerButtons;

    private List<Button> towerButtons;
    private List<Button> utilityButtons;

    private boolean isMovingTowers;
    private int fromTowerIndex;

    private boolean isApplyingUpgrade;
    private int upgradeToApplyIndex;
    private List<Tower> towersToApply;
    private int maximumTargets;


    public InventoryController(GameManager gameManager){
        this.gameManager = gameManager;
        inventoryManager = gameManager.getInventoryManager();
        inventoryManager.addUpgrade(new Upgrade(new ResourceAmountBoost(10),Rarity.COMMON, 10, 2));
    }

    public void initialize(){
        initializeTowerButtons();
        initializeUpgradeListView();
        updateUpgradeViewList();
        initializeDefaultValues();
        renameTowerButton.setDisable(true);
        displayTowerNull();
        displayUpgradeNull();
        styleTowerButtons();
    }

    private void initializeUpgradeListView() {
        upgradesListView.setCellFactory(new UpgradeCellFactory());
        upgradesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        upgradesListView.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldIndex, newIndex) -> {
            selectedInventoryUpgradeIndex = newIndex.intValue();
            selectedInventoryItemType = "Upgrade";
            renameTowerButton.setDisable(true);
            if(!isApplyingUpgrade){
                updateUpgradeStats(inventoryManager.getInventoryData().getUpgrades().get(selectedInventoryUpgradeIndex));
            }
        });
    }

    private void initializeDefaultValues() {
        isMovingTowers = false;
        isApplyingUpgrade = false;
        fromTowerIndex = -1;
        upgradeToApplyIndex = -1;
        selectedInventoryTowerIndex = -1;
        selectedInventoryUpgradeIndex = -1;
        maximumTargets = -1;
        towersToApply = new ArrayList<>();
        utilityButtons = List.of(useItemButton, moveTowerButton, renameTowerButton);
        selectedTowerButtons = new boolean[towerButtons.size()];
    }

    private void updateUpgradeViewList(){
        upgradesListView.setItems(FXCollections.observableArrayList(inventoryManager.getInventoryData().getUpgrades()));
    }

    private void updateTowerStats(Tower tower) {
        if (tower != null) {
            rarityLabel.setText(String.valueOf(tower.getRarity()));
            resourceAmountLabel.setText(String.valueOf(tower.getTowerStats().getResourceAmount()));
            cooldownLabel.setText(String.valueOf(tower.getTowerStats().getCooldown()));
            resourceTypeLabel.setText(String.valueOf(tower.getTowerStats().getResourceType()));
            //TODO: UPDATE THE DESTROYED DISPLAY
            if (tower.isBroken()){
                towerNameTextField.setText("DESTROYED");
            } else{
                towerNameTextField.setText(tower.getName());
            }
            levelLabel.setText(String.valueOf(tower.getLevel()));
            int levelRequirement;
            if(tower.getLevel() < 15){
                levelRequirement = TowerStatsManager.getLevelRequirements()[tower.getLevel()+1];
            } else{
                levelRequirement = TowerStatsManager.getLevelRequirements()[tower.getLevel()];

            }
            expPointsLabel.setText(tower.getExperiencePoints() + "/" + levelRequirement);
        } else{
            displayTowerNull();
        }
    }

    private void updateUpgradeStats(Upgrade upgrade) {
        upgradeNameLabel.setText(upgrade.getEffect().getEffectName());
        upgradeDescriptionLabel.setText(upgrade.toString());
    }

    private void initializeTowerButtons(){
        towerButtons = List.of(mainTower1Button, mainTower2Button, mainTower3Button, mainTower4Button, mainTower5Button,
                reserveTower1Button, reserveTower2Button, reserveTower3Button, reserveTower4Button, reserveTower5Button);
        for (int i = 0; i < towerButtons.size(); i++){
            int finalI = i;
            towerButtons.get(i).setOnAction(event ->{
                selectedInventoryItemType = "Tower";
                selectedInventoryTowerIndex = finalI;
                towerButtonClicked(finalI);
            });
        }
    }

    private void styleTowerButtons(){
        Tower tower;
        for (int i = 0; i < towerButtons.size(); i++){
            if(i < 5){
                tower = inventoryManager.getInventoryData().getMainTowers()[i];
            } else {
                tower = inventoryManager.getInventoryData().getReserveTowers()[i-5];
            }
            String style = "-fx-border-radius: 5;";
            if(tower != null) {
                style += "-fx-border-color: " + tower.getRarity().getRarityTextColor() + ";  -fx-border-width: 5;";
            }
            if (selectedTowerButtons[i]) {
                style += "-fx-background-color: #b3b3b3; -fx-background-radius: 5;";
            }
            towerButtons.get(i).setStyle(style);
        }
    }

    private void towerButtonClicked(int finalI) {
        Tower tower;
        if (finalI >= 5) {
            tower = inventoryManager.getInventoryData().getReserveTowers()[finalI-5];
        } else {
            tower = inventoryManager.getInventoryData().getMainTowers()[finalI];
        }

        if(isMovingTowers) {
            moveTower();
        } else if(isApplyingUpgrade){
            selectedTowerButtons[finalI] = applyUpgrade(tower);
            updateUseItemButton();
        } else {
            updateTowerStats(tower);
            clearSelectedTowerButtons();
            selectedTowerButtons[finalI] = true;
            renameTowerButton.setDisable(tower == null);
        }
        styleTowerButtons();

    }

    private void updateUseItemButton(){
        if(towersToApply.isEmpty()){
            useItemButton.setText("Click here to cancel or select " + maximumTargets + " more towers");
        } else if(towersToApply.size() == maximumTargets) {
            useItemButton.setText("Click here to apply");
        } else {
            useItemButton.setText("Click here to apply or select " + (maximumTargets - towersToApply.size()) + " more towers");
        }
    }
    private void clearSelectedTowerButtons(){
        selectedTowerButtons = new boolean[towerButtons.size()];
    }
    private void displayTowerNull() {
        rarityLabel.setText("None");
        resourceAmountLabel.setText("None");
        cooldownLabel.setText("None");
        resourceTypeLabel.setText("None");
        towerNameTextField.setText("None");
        levelLabel.setText("None");
        expPointsLabel.setText("None");
    }

    private void displayUpgradeNull(){
        upgradeNameLabel.setText("None");
        upgradeDescriptionLabel.setText("None");
    }

    private boolean applyUpgrade(Tower tower){
        if(tower != null) {
            if (towersToApply.contains(tower)) {
                towersToApply.remove(tower);
                return false;
            } else {
                if (towersToApply.size() < maximumTargets) {
                    towersToApply.add(tower);
                    return true;
                }
            }
        }
        return false;
    }

    @FXML
    private void onUseItemClicked() {
        if (selectedInventoryUpgradeIndex != -1 && !isApplyingUpgrade) {
            isApplyingUpgrade = true;
            disableOtherButtons(useItemButton);
            upgradeToApplyIndex = selectedInventoryUpgradeIndex;
            maximumTargets = inventoryManager.getInventoryData().getUpgrades().get(upgradeToApplyIndex).getMaximumTargets();
            updateUseItemButton();
        } else {
            if (!towersToApply.isEmpty()) {
                applyUpgrades();
            }
            isApplyingUpgrade = false;
            towersToApply.clear();
            moveTowerButton.setDisable(false);
            useItemButton.setText("Use Item");
        }
        clearSelectedTowerButtons();
        styleTowerButtons();
    }

    private void applyUpgrades() {
        inventoryManager.applyUpgradeTo(upgradeToApplyIndex, towersToApply);
        upgradeToApplyIndex = -1;
        updateUpgradeViewList();
        displayUpgradeNull();
        displayTowerNull();
        selectedInventoryUpgradeIndex = -1;
    }

    @FXML
    private void onMoveClicked(){
        if(!isMovingTowers) {
            isMovingTowers = true;
            disableOtherButtons(moveTowerButton);
            displayTowerNull();
            clearSelectedTowerButtons();
            moveTowerButton.setText("Click here to cancel or select a tower to move");
        } else {
            moveTowerButton.setText("Move Tower");
            isMovingTowers = false;
            useItemButton.setDisable(false);
        }
        styleTowerButtons();
        fromTowerIndex = -1;
        selectedInventoryTowerIndex = -1;
    }

    @FXML
    private void onRenameTowerClicked() {
        if (selectedInventoryTowerIndex != -1 && Objects.equals(selectedInventoryItemType, "Tower")) {
            if(inventoryManager.checkName(towerNameTextField.getText())){
                renameTower();
            } else {
                errorPopUp("Type a tower name between 3 to 15 characters with no special characters.");
            }
        } else {
            errorPopUp("Please select a tower");
        }
    }

    private void renameTower() {
        if(selectedInventoryTowerIndex >= 5){
            inventoryManager.getInventoryData().getReserveTowers()[selectedInventoryTowerIndex-5].setName(towerNameTextField.getText());
        } else {
            inventoryManager.getInventoryData().getMainTowers()[selectedInventoryTowerIndex].setName(towerNameTextField.getText());
        }
    }

    private void errorPopUp(String message) {
        Dialog<?> errorPane = new Dialog<>();
        errorPane.setContentText(message);
        errorPane.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        errorPane.show();
    }

    @FXML
    private void onReturnClicked(){
        gameManager.getGameGUIManager().launchScreen("Game Menu");
    }

    private void moveTower(){
        if (fromTowerIndex != -1) {
            if (selectedInventoryTowerIndex != fromTowerIndex) {
                inventoryManager.swapTowers(selectedInventoryTowerIndex, fromTowerIndex);
                displayTowerNull();
            }
            isMovingTowers = false;
            useItemButton.setDisable(false);
            moveTowerButton.setText("Move Tower");
            clearSelectedTowerButtons();
        } else {
            fromTowerIndex = selectedInventoryTowerIndex;
            selectedTowerButtons[fromTowerIndex] = true;
            moveTowerButton.setText("Click here to cancel or select a tower to move to");
        }
    }

    private void disableOtherButtons(Button button){
        for (Button b : utilityButtons) {
            if(b != button){
                b.setDisable(true);
            }
        }
    }

}
