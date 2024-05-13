package seng201.team8.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import seng201.team8.models.Tower;
import seng201.team8.models.Upgrade;
import seng201.team8.services.GameManager;
import seng201.team8.services.InventoryManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryController {
    InventoryManager inventoryManager;
    GameManager gameManager;
    public InventoryController(GameManager gameManager){
        this.gameManager = gameManager;
        inventoryManager = gameManager.getInventoryManager();
    }

    @FXML
    private Button mainTower1Button, mainTower2Button, mainTower3Button, mainTower4Button, mainTower5Button;

    @FXML
    private Button reserveTower1Button, reserveTower2Button, reserveTower3Button, reserveTower4Button, reserveTower5Button;

    @FXML
    private Label resourceAmountLabel, cooldownLabel, resourceTypeLabel;

    @FXML
    private TextField towerNameTextField;

    @FXML
    private ImageView towerImageView, upgradeImageView;

    @FXML
    private ListView<Upgrade> upgradesListView;

    private String selectedInventoryItemType;
    private int selectedInventoryItemIndex;

    private List<Button> towerButtons;

    private boolean movingTowers;
    private int fromTowerIndex;

    public void initialize(){
        // TODO: Remove Duplicated Code
        movingTowers = false;
        fromTowerIndex = -1;
        upgradesListView.setCellFactory(new UpgradeCellFactory());
        upgradesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        upgradesListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                selectedInventoryItemIndex = newIndex.intValue();
                selectedInventoryItemType = "Upgrade";
            }
        });
        updateUpgradeViewList();
        towerButtons = List.of(mainTower1Button, mainTower2Button, mainTower3Button, mainTower4Button, mainTower5Button, reserveTower1Button, reserveTower2Button, reserveTower3Button, reserveTower4Button, reserveTower5Button);
        initializeTowerButtons();
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
                if(!movingTowers) {
                    if (finalI >= 5) {
                        updateStats(inventoryManager.getInventoryData().getReserveTowers()[finalI - 5]);
                    } else {
                        updateStats(inventoryManager.getInventoryData().getMainTowers()[finalI]);
                    }
                } else {
                    moveTower();
                }
            });
        }
    }

    private void displayNull() {
        resourceAmountLabel.setText("None");
        cooldownLabel.setText("None");
        resourceTypeLabel.setText("None");
        towerNameTextField.setText("None");
    }

    @FXML
    private void onUseItemClicked(){

    }

    @FXML
    private void onMoveClicked(){
        movingTowers = true;
        selectedInventoryItemIndex = -1;
        fromTowerIndex = -1;
    }

    private void moveTower(){
        if (fromTowerIndex != -1) {
            if (selectedInventoryItemIndex != fromTowerIndex) {
                inventoryManager.swapTowers(selectedInventoryItemIndex, fromTowerIndex);
                displayNull();
            }
            movingTowers = false;
        } else {
            fromTowerIndex = selectedInventoryItemIndex;
        }
    }

}
