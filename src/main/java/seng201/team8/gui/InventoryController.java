package seng201.team8.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import seng201.team8.models.*;
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
    private Label resourceAmountLabel, cooldownLabel, resourceTypeLabel, levelLabel, expPointsLabel;

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
    private int selectedInventoryItemIndex;

    private List<Button> towerButtons;
    private List<Button> utilityButtons;

    private boolean isMovingTowers;
    private int fromTowerIndex;

    private boolean isApplyingUpgrade;
    private int upgradeToApplyIndex;
    private ArrayList<Tower> towersToApply;
    private int maximumTargets;

    public InventoryController(GameManager gameManager){
        this.gameManager = gameManager;
        inventoryManager = gameManager.getInventoryManager();
        inventoryManager.getInventoryData().getUpgrades().add(new Upgrade(new ResourceAmountBoost(10), Rarity.COMMON, 10, 2));
    }

    public void initialize(){
        // TODO: Remove Duplicated Code
        initializeDefaultValues();
        renameTowerButton.setDisable(true);
        upgradesListView.setCellFactory(new UpgradeCellFactory());
        upgradesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        upgradesListView.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldIndex, newIndex) -> {
            selectedInventoryItemIndex = newIndex.intValue();
            selectedInventoryItemType = "Upgrade";
            renameTowerButton.setDisable(true);
            updateUpgradeStats(inventoryManager.getInventoryData().getUpgrades().get(selectedInventoryItemIndex));
        });
        updateUpgradeViewList();
        
        initializeTowerButtons();
    }

    private void initializeDefaultValues() {
        isMovingTowers = false;
        isApplyingUpgrade = false;
        fromTowerIndex = -1;
        upgradeToApplyIndex = -1;
        maximumTargets = -1;
        towersToApply = new ArrayList<>();
        utilityButtons = List.of(useItemButton, moveTowerButton, renameTowerButton);
        towerButtons = List.of(mainTower1Button,
                mainTower2Button,
                mainTower3Button,
                mainTower4Button,
                mainTower5Button,
                reserveTower1Button,
                reserveTower2Button,
                reserveTower3Button,
                reserveTower4Button,
                reserveTower5Button);
    }

    private void updateUpgradeViewList(){
        upgradesListView.setItems(FXCollections.observableArrayList(inventoryManager.getInventoryData().getUpgrades()));
    }

    private void updateTowerStats(Tower tower) {
        if (tower != null) {
            resourceAmountLabel.setText(String.valueOf(tower.getTowerStats().getResourceAmount()));
            cooldownLabel.setText(String.valueOf(tower.getTowerStats().getCooldown()));
            resourceTypeLabel.setText(String.valueOf(tower.getTowerStats().getResourceType()));
            towerNameTextField.setText(tower.getName());
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
        for (int i = 0; i < towerButtons.size(); i++){
            int finalI = i;
            towerButtons.get(i).setOnAction(event ->{
                selectedInventoryItemType = "Tower";
                selectedInventoryItemIndex = finalI;
                renameTowerButton.setDisable(false);
                towerButtonClicked(finalI);
            });
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
            applyUpgrade(tower);
        } else {
            updateTowerStats(tower);
            renameTowerButton.setDisable(tower == null);
        }

    }

    private void displayTowerNull() {
        resourceAmountLabel.setText("None");
        cooldownLabel.setText("None");
        resourceTypeLabel.setText("None");
        towerNameTextField.setText("None");
        levelLabel.setText("None");
        expPointsLabel.setText("None");
    }

    private void applyUpgrade(Tower tower){
        if(tower != null) {
            if (towersToApply.contains(tower)) {
                towersToApply.remove(tower);
            } else {
                if (towersToApply.size() < maximumTargets) {
                    towersToApply.add(tower);
                }
            }
            if(towersToApply.isEmpty()){
                useItemButton.setText("Click here to cancel or select" + maximumTargets + " more towers");
            } else {
                useItemButton.setText("Click here to apply or select " + (maximumTargets - towersToApply.size()) + " more towers");
            }
        }
    }

    @FXML
    private void onUseItemClicked() {
        if (Objects.equals(selectedInventoryItemType, "Upgrade") && !isApplyingUpgrade) {
            isApplyingUpgrade = true;
            disableOtherButtons(useItemButton);
            upgradeToApplyIndex = selectedInventoryItemIndex;
            maximumTargets = inventoryManager.getInventoryData().getUpgrades().get(upgradeToApplyIndex).getMaximumTargets();
            useItemButton.setText("Click here to cancel or select" + maximumTargets + " more towers");
        } else {
            if (!towersToApply.isEmpty()) {
                inventoryManager.applyUpgradeTo(upgradeToApplyIndex, towersToApply);
                upgradeToApplyIndex = -1;
                updateUpgradeViewList();
            }
            isApplyingUpgrade = false;
            towersToApply.clear();
            enableAllButtons();
            useItemButton.setText("Use Item");
        }
    }

    @FXML
    private void onMoveClicked(){
        if(!isMovingTowers) {
            isMovingTowers = true;
            disableOtherButtons(moveTowerButton);
            moveTowerButton.setText("Click here to cancel or select a tower to move");
        } else {
            moveTowerButton.setText("Move Tower");
            isMovingTowers = false;
            enableAllButtons();
        }
        fromTowerIndex = -1;
        selectedInventoryItemIndex = -1;
    }

    @FXML
    private void onRenameTowerClicked() {
        if (selectedInventoryItemIndex != -1 && Objects.equals(selectedInventoryItemType, "Tower")) {
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
        if(selectedInventoryItemIndex >= 5){
            inventoryManager.getInventoryData().getReserveTowers()[selectedInventoryItemIndex-5].setName(towerNameTextField.getText());
        } else {
            inventoryManager.getInventoryData().getMainTowers()[selectedInventoryItemIndex].setName(towerNameTextField.getText());
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
            if (selectedInventoryItemIndex != fromTowerIndex) {
                inventoryManager.swapTowers(selectedInventoryItemIndex, fromTowerIndex);
                displayTowerNull();
            }
            isMovingTowers = false;
            enableAllButtons();
            moveTowerButton.setText("Move Tower");
        } else {
            fromTowerIndex = selectedInventoryItemIndex;
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

    private void enableAllButtons(){
        for (Button b : utilityButtons) {
            b.setDisable(false);
        }
    }

}
