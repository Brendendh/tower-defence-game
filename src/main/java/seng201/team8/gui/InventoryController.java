package seng201.team8.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import seng201.team8.models.*;
import seng201.team8.services.GameManager;
import seng201.team8.services.InventoryManager;
import seng201.team8.services.TowerStatsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The controller class for InventoryController.
 * <p></p>
 * Responsible for the communication between the user input and the InventoryManager.
 */
public class InventoryController {

    /**
     * The {@link InventoryManager} for managing the players {@link seng201.team8.models.dataRecords.InventoryData}.
     */
    final InventoryManager inventoryManager;

    /**
     * The {@link GameManager} for accessing central information and launching new screens.
     */

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

    /**
     * The {@link Integer} which represents which {@link Upgrade} is selected by index.
     */
    private int selectedInventoryUpgradeIndex;

    /**
     * The {@link Integer} which represents which {@link Tower} is selected by index.
     */
    private int selectedInventoryTowerIndex;

    /**
     * The array of {@link Boolean} objects where the index represents the index of
     * the tower buttons and the {@link Boolean} objects represent if they are selected.
     * Used for multi-selection.
     */
    private boolean[] selectedTowerButtons;

    /**
     * The {@link List} of tower {@link Button}s.
     */
    private List<Button> towerButtons;

    /**
     * The {@link List} of utility {@link Button}s.
     * These include useItemButton, moveTowerButton and renameTowerButton.
     */
    private List<Button> utilityButtons;

    /**
     * The {@link Boolean} object which represents the state where the player is moving towers.
     */
    private boolean isMovingTowers;

    /**
     * The {@link Integer} which represents which tower index it will move from.
     */
    private int fromTowerIndex;

    /**
     * The {@link Boolean} object which represents the state where the player is applying upgrades.
     */
    private boolean isApplyingUpgrade;

    /**
     * The {@link Integer} which represents the index of the {@link Upgrade}
     * which is in the {@link seng201.team8.models.dataRecords.InventoryData}.
     */
    private int upgradeToApplyIndex;

    /**
     * The {@link List} of {@link Tower}s which represent the towers to apply
     * to.
     */
    private List<Tower> towersToApply;

    /**
     * The {@link Integer} which represents how many towers can be selected.
     * Used during application of upgrades.
     */
    private int maximumTargets;

    /**
     * The constructor for {@link InventoryController}
     * <p></p>
     * Takes in a {@link GameManager} and stores it in the InventoryController
     * @param gameManager {@link GameManager}
     */
    public InventoryController(GameManager gameManager){
        this.gameManager = gameManager;
        inventoryManager = gameManager.getInventoryManager();
    }

    /**
     * Runs when the {@link InventoryController} is initialized. Initializes the
     * {@link Tower} {@link Tower}s, {@link Upgrade} {@link ListView}, default values,
     * and {@link ImageView}s for the controller.
     */
    public void initialize(){
        initializeTowerButtons();
        initializeUpgradeListView();
        updateUpgradeViewList();
        initializeDefaultValues();
        renameTowerButton.setDisable(true);
        useItemButton.setDisable(true);
        displayTowerNull();
        displayUpgradeNull();
        styleTowerButtons();
    }

    /**
     * Initializes the {@link Upgrade} {@link ListView} with functionality
     * when the {@link ListView} elements are selected.
     */
    private void initializeUpgradeListView() {
        upgradesListView.setCellFactory(new UpgradeCellFactory());
        upgradesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        upgradesListView.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldIndex, newIndex) -> {
            selectedInventoryUpgradeIndex = newIndex.intValue();
            renameTowerButton.setDisable(true);
            useItemButton.setDisable(false);
            if(!isApplyingUpgrade){
                updateUpgradeStats(inventoryManager.getInventoryData().getUpgrades().get(selectedInventoryUpgradeIndex));
            }
        });
    }

    /**
     * Initializes the default values that are required before the
     * controller becomes functional.
     */
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

    /**
     * Updates the {@link Upgrade} {@link ListView}.
     */
    private void updateUpgradeViewList(){
        upgradesListView.setItems(FXCollections.observableArrayList(inventoryManager.getInventoryData().getUpgrades()));
    }


    /**
     * Updates the tower stat {@link Label}s and tower {@link ImageView} based on the given {@link Tower}.
     * If the given tower is null, the tower stat {@link Label}s are set to "None"
     * @param tower {@link Tower}
     */
    private void updateTowerStats(Tower tower) {
        if (tower != null) {
            rarityLabel.setText(String.valueOf(tower.getRarity()));
            resourceAmountLabel.setText(String.valueOf(tower.getTowerStats().getResourceAmount()));
            cooldownLabel.setText(String.valueOf(tower.getTowerStats().getCooldown()));
            resourceTypeLabel.setText(String.valueOf(tower.getTowerStats().getResourceType()));
            towerNameTextField.setText(tower.getName());
            levelLabel.setText(String.valueOf(tower.getLevel()));
            int levelRequirement;
            if(tower.getLevel() < 15){
                levelRequirement = TowerStatsManager.getLevelRequirements()[tower.getLevel()+1];
                expPointsLabel.setText(tower.getExperiencePoints() + "/" + levelRequirement);
            } else{
                expPointsLabel.setText("Max level reached.");

            }
            displayTowerImage(tower);
        } else{
            displayTowerNull();
        }
    }

    /**
     * Updates the upgrade stat {@link Label}s and upgrade {@link ImageView} based on the given {@link Upgrade}.
     * @param upgrade {@link Upgrade}
     */
    private void updateUpgradeStats(Upgrade upgrade) {
        upgradeNameLabel.setText(upgrade.getEffect().getEffectName());
        upgradeDescriptionLabel.setText(upgrade.toString());
        displayUpgradeImage(upgrade);
    }

    /**
     * Initializes the tower {@link Button}s with functionality
     * when the tower {@link Button}s are clicked.
     */
    private void initializeTowerButtons(){
        towerButtons = List.of(mainTower1Button, mainTower2Button, mainTower3Button, mainTower4Button, mainTower5Button,
                reserveTower1Button, reserveTower2Button, reserveTower3Button, reserveTower4Button, reserveTower5Button);
        for (int i = 0; i < towerButtons.size(); i++){
            int finalI = i;
            towerButtons.get(i).setOnAction(event ->{
                selectedInventoryTowerIndex = finalI;
                towerButtonClicked(finalI);
            });
        }
    }

    /**
     * Applies styling to each tower button depending on the towers
     * rarity and if it is selected or not.
     * Broken towers have their tower buttons text set to "DESTROYED".
     * Positions which are null in the main towers or reserve towers
     * have their tower buttons text set to "Empty"
     */
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
                if(tower.isBroken()) {
                    towerButtons.get(i).setText("DESTROYED");
                } else {
                    towerButtons.get(i).setText(tower.getName());
                }
            } else {
                towerButtons.get(i).setText("Empty");
            }
            if (selectedTowerButtons[i]) {
                style += "-fx-background-color: #b3b3b3; -fx-background-radius: 5;";
            }
            towerButtons.get(i).setStyle(style);
        }
    }

    /**
     * Functionality for when the tower button is clicked.
     * <p></p>
     * It checks for what the current state of the inventory it is in and run
     * the appropriate functionality depending on the state.
     * @param finalI {@link Integer}
     */
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

    /**
     * Updates the useItemButton depending on how many towers
     * are in the towersToApply {@link List} and the maximum number of
     * towers the player can choose.
     */
    private void updateUseItemButton(){
        if(towersToApply.isEmpty()){
            useItemButton.setText("Click here to cancel or select " + maximumTargets + " more towers");
        } else if(towersToApply.size() == maximumTargets) {
            useItemButton.setText("Click here to apply");
        } else {
            useItemButton.setText("Click here to apply or select " + (maximumTargets - towersToApply.size()) + " more towers");
        }
    }

    /**
     * Clears the selectedTowerButton array by creating a new one.
     */
    private void clearSelectedTowerButtons(){
        selectedTowerButtons = new boolean[towerButtons.size()];
    }

    /**
     * Displays the {@link Tower} {@link ImageView} which is dependent
     * on the given tower's {@link Resource} type.
     * If the tower is broken, a greyscale filter is applied over the image.
     * @param tower {@link Tower}
     */
    private void displayTowerImage(Tower tower){
        Image image = new Image("/images/towers/" + tower.getTowerStats().getResourceType().name().toLowerCase() + ".jpg");
        if(tower.isBroken()) {
            ColorAdjust brokenFilter = new ColorAdjust();
            brokenFilter.setSaturation(-1);
            towerImageView.setEffect(brokenFilter);
        } else {
            towerImageView.setEffect(null);
        }
        towerImageView.setImage(image);

    }

    /**
     * Displays the {@link Upgrade} {@link ImageView} which is dependent
     * on the given upgrade's {@link seng201.team8.models.effects.Effect}.
     * @param upgrade {@link Upgrade}
     */
    private void displayUpgradeImage(Upgrade upgrade){
        Image image = new Image("/images/upgrades/" + upgrade.getEffect().getEffectName().toLowerCase().replace(" ", "") + ".jpg");

        upgradeImageView.setImage(image);
    }


    /**
     * Displays all the {@link Tower} stat {@link Label}s to "None"
     * and sets the {@link Tower} {@link ImageView} image to null.
     */
    private void displayTowerNull() {
        rarityLabel.setText("None");
        resourceAmountLabel.setText("None");
        cooldownLabel.setText("None");
        resourceTypeLabel.setText("None");
        towerNameTextField.setText("None");
        levelLabel.setText("None");
        expPointsLabel.setText("None");
        towerImageView.setImage(null);
    }

    /**
     * Displays all the {@link Upgrade} {@link Label}s to "None"
     * and sets the {@link Upgrade} {@link ImageView} image to null.
     */
    private void displayUpgradeNull(){
        upgradeNameLabel.setText("None");
        upgradeDescriptionLabel.setText("None");
        upgradeImageView.setImage(null);
    }

    /**
     * Adds {@link Tower}s to towers to apply {@link List} or removes
     * them if the tower is already in the list.
     * If a new tower was to be added in a full {@link List}, it will
     * be rejected.
     * @param tower {@link Tower}
     * @return {@link Boolean}
     */
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

    /**
     * Checks if the currently selected item is an {@link Upgrade} and
     * not in an applying upgrade state. Then the inventory screen will get into
     * an applying upgrade state where the player has to select towers
     * up to the maximum target of the upgrade.
     * <p></p>
     * If the player is already in the applying upgrade state, the upgrade
     * will either be consumed and used on the selected towers, or none are
     * selected so the action will be canceled. Afterward, the player will
     * not be in the applying upgrade state/
     */
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
        displayTowerNull();
        clearSelectedTowerButtons();
        styleTowerButtons();
    }

    /**
     * Applies {@link Upgrade} to the selected towers and then gets removed
     * from the upgrades in the {@link seng201.team8.models.dataRecords.InventoryData}.
     */
    private void applyUpgrades() {
        inventoryManager.applyUpgradeTo(upgradeToApplyIndex, towersToApply);
        upgradeToApplyIndex = -1;
        updateUpgradeViewList();
        displayUpgradeNull();
        displayTowerNull();
        useItemButton.setDisable(true);
        selectedInventoryUpgradeIndex = -1;
    }

    /**
     * Checks if the player is in the moving tower state. If not, the player will
     * be in the moving tower state.
     * <p></p>
     * If the player is already in the moving tower state. The player will the not be in
     * the moving tower state.
     */
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

    /**
     * Renames the selected tower with the {@link String} in the tower name {@link TextField}.
     * If the name does not meet the conditions in {@link InventoryManager}, the tower does not
     * get renamed.
     */
    @FXML
    private void onRenameTowerClicked() {
        if (selectedInventoryTowerIndex != -1) {
            if(inventoryManager.checkName(towerNameTextField.getText())){
                renameTower();
            } else {
                errorPopUp("Type a tower name between 3 to 15 characters with no special characters.");
            }
        } else {
            errorPopUp("Please select a tower");
        }
        styleTowerButtons();
    }

    /**
     * Renames the tower at the {@link Integer} selectedInventoryTowerIndex with the
     * tower name {@link TextField}
     */
    private void renameTower() {
        if(selectedInventoryTowerIndex >= inventoryManager.getInventoryData().getMainTowers().length){
            inventoryManager.getInventoryData().getReserveTowers()[selectedInventoryTowerIndex-
                    inventoryManager.getInventoryData().getMainTowers().length].setName(towerNameTextField.getText());
        } else {
            inventoryManager.getInventoryData().getMainTowers()[selectedInventoryTowerIndex].setName(towerNameTextField.getText());
        }
    }

    /**
     * A pop-up message creator which displays a given {@link String} message and an "OK" button.
     * Used to give the player an indicator that the player is performing a wrong action.
     * @param message {@link String}
     */
    private void errorPopUp(String message) {
        Dialog<?> errorPane = new Dialog<>();
        errorPane.setContentText(message);
        errorPane.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        errorPane.show();
    }

    /**
     * Launches to the "Game Menu" Screen.
     */
    @FXML
    private void onReturnClicked(){
        gameManager.getGameGUIManager().launchScreen("Game Menu");
    }

    /**
     * If the player has not chosen the {@link Tower} to move from, that tower index
     * is stored in the fromTowerIndex.
     * <p></p>
     * If the player has already chosen the {@link Tower} to move from, the tower
     * that is selected swaps positions with the {@link Tower} to move from. The player
     * will not be in the moving tower state.
     */
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

    /**
     * Disables the utility buttons except for the given {@link Button}
     * @param button {@link Button}
     */
    private void disableOtherButtons(Button button){
        for (Button b : utilityButtons) {
            if(b != button){
                b.setDisable(true);
            }
        }
    }


}
