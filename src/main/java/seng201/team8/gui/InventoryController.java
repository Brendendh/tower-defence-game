package seng201.team8.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import seng201.team8.models.*;
import seng201.team8.services.GameManager;
import seng201.team8.services.InventoryManager;

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
    private Label resourceAmountLabel, cooldownLabel, resourceTypeLabel;

    @FXML
    private Button useItemButton, moveTowerButton;

    @FXML
    private TextField towerNameTextField;

    @FXML
    private ImageView towerImageView, upgradeImageView;

    @FXML
    private ListView<Upgrade> upgradesListView;

    private String selectedInventoryItemType;
    private int selectedInventoryItemIndex;

    private List<Button> towerButtons;

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
        upgradesListView.setCellFactory(new UpgradeCellFactory());
        upgradesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        upgradesListView.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldIndex, newIndex) -> {
            selectedInventoryItemIndex = newIndex.intValue();
            selectedInventoryItemType = "Upgrade";
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

    private void updateStats(Tower tower) {
        if (tower != null) {
            resourceAmountLabel.setText(String.valueOf(tower.getTowerStats().getResourceAmount()));
            cooldownLabel.setText(String.valueOf(tower.getTowerStats().getCooldown()));
            resourceTypeLabel.setText(String.valueOf(tower.getTowerStats().getResourceType()));
            towerNameTextField.setText(tower.getName());
        } else{
            displayNull();
        }
    }

    private void initializeTowerButtons(){
        for (int i = 0; i < towerButtons.size(); i++){
            int finalI = i;
            towerButtons.get(i).setOnAction(event ->{
                selectedInventoryItemType = "Tower";
                selectedInventoryItemIndex = finalI;
                towerButtonClicked(finalI);
            });
        }
    }

    private void towerButtonClicked(int finalI) {
        if(isMovingTowers) {
            moveTower();
        } else if(isApplyingUpgrade){
            if (finalI >= 5) {
                applyUpgrade(inventoryManager.getInventoryData().getReserveTowers()[finalI - 5]);
            } else {
                applyUpgrade(inventoryManager.getInventoryData().getMainTowers()[finalI]);
            }
        }else {
            if (finalI >= 5) {
                updateStats(inventoryManager.getInventoryData().getReserveTowers()[finalI - 5]);
            } else {
                updateStats(inventoryManager.getInventoryData().getMainTowers()[finalI]);
            }
        }
    }

    private void displayNull() {
        resourceAmountLabel.setText("None");
        cooldownLabel.setText("None");
        resourceTypeLabel.setText("None");
        towerNameTextField.setText("None");
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
        }
    }

    @FXML
    private void onUseItemClicked() {
        if (Objects.equals(selectedInventoryItemType, "Upgrade") && !isApplyingUpgrade) {

            isApplyingUpgrade = true;
            moveTowerButton.setDisable(true);
            upgradeToApplyIndex = selectedInventoryItemIndex;
            maximumTargets = inventoryManager.getInventoryData().getUpgrades().get(upgradeToApplyIndex).getMaximumTargets();

        } else {

            if (!towersToApply.isEmpty()) {
                inventoryManager.applyUpgradeTo(upgradeToApplyIndex, towersToApply);
                upgradeToApplyIndex = -1;
                updateUpgradeViewList();
            }

            isApplyingUpgrade = false;
            towersToApply.clear();
            moveTowerButton.setDisable(false);
        }
    }

    @FXML
    private void onMoveClicked(){
        isMovingTowers = true;
        selectedInventoryItemIndex = -1;
        fromTowerIndex = -1;
        useItemButton.setDisable(true);
    }

    @FXML
    private void onReturnClicked(){
        gameManager.getGameGUIManager().launchScreen("Game Menu");
    }

    private void moveTower(){
        if (fromTowerIndex != -1) {
            if (selectedInventoryItemIndex != fromTowerIndex) {
                inventoryManager.swapTowers(selectedInventoryItemIndex, fromTowerIndex);
                displayNull();
            }
            isMovingTowers = false;
            useItemButton.setDisable(false);
        } else {
            fromTowerIndex = selectedInventoryItemIndex;
        }
    }

}
