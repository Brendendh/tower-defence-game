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

/**
 * The controller class for GameSetupController.
 * <p></p>
 * Responsible for the communication between the user input and the GameSetupService.
 */
public class GameSetupController {
    /**
     * The {@link GameManager} for launching screens and storing information.
     */
    private final GameManager gameManager;
    /**
     * The {@link GameSetupService} for creating {@link GameData} and {@link InventoryManager}
     */
    private final GameSetupService gameSetupService;

    /**
     * The constructor for {@link GameSetupController}
     * <p></p>
     * Takes in a {@link GameManager} object and creates a {@link GameSetupService} which are stored
     * in the GameSetupController.
     * @param gameManager {@link GameManager}
     */
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

    /**
     * An {@link Integer} value which represents
     * the current selected tower button.
     * -1 : None Selected
     */
    private int selectedTowerIndex = -1;

    /**
     * An array of {@link Tower}s which represents
     * the towers to be added when the game setup
     * is completed.
     */
    private final Tower[] addedTowers = new Tower[3];
    /**
     * A {@link List} of {@link Button} representing default towers.
     * Used to loop through to style the buttons.
     */
    private List<Button> towerButtons;
    /**
     * A {@link List} of {@link Button} representing round number options.
     * Used to loop through to style the buttons.
     */
    private List<Button> roundButtons;

    /**
     * Runs when initialized.
     */
    public void initialize() {
        initializeTowerButtons();
    }

    /**
     * Initializes tower buttons to be able to:
     *  - Update the stat table with the respective {@link Tower}
     *  - Updates the {@link Integer} selectedTowerIndex to the button index
     *  - Colours the selected tower button
     */
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

    /**
     * Updates the {@link ImageView} and stat {@link Label}s with the selected {@link Tower}'s stat.
     * If there is no tower in the added towers space, displays Nothing.
     * @param finalI {@link Integer}
     */
    private void updateSelectedTowerStat(int finalI) {
        if (finalI >= gameManager.getDefaultTowers().length){
            if (addedTowers[finalI - gameManager.getDefaultTowers().length] != null){
                updateStats(addedTowers[finalI-5]);
                displayTowerImage(addedTowers[finalI-5]);
                renameTowerButton.setDisable(false);
            } else{
                displayNull();
                renameTowerButton.setDisable(true);
            }
        } else {
            renameTowerButton.setDisable(true);
            displayTowerImage(gameManager.getDefaultTowers()[finalI]);
            updateStats(gameManager.getDefaultTowers()[finalI]);
        }
    }

    /**
     * Loops through the tower buttons and colours the one that is selected. Other button colours are removed by
     * setting the style to "".
     * @param towerButtons a {@link List} of {@link Button}s
     * @param finalI {@link Integer}
     */
    private static void colourSelectedTower(List<Button> towerButtons, int finalI) {
        towerButtons.forEach(button -> {if(button == towerButtons.get(finalI)) {
            button.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
        } else {
                button.setStyle("");
            }
        });
    }

    /**
     * Updates the tower stats {@link Label}s and tower name {@link TextField} based on the given {@link Tower}.
     * @param tower {@link Tower}
     */
    private void updateStats(Tower tower) {
        resourceAmountLabel.setText(String.valueOf(tower.getTowerStats().getResourceAmount()));
        cooldownLabel.setText(String.valueOf(tower.getTowerStats().getCooldown()));
        resourceTypeLabel.setText(String.valueOf(tower.getTowerStats().getResourceType()));
        towerNameTextField.setText(tower.getName());
    }

    /**
     * Updates the tower stats {@link Label}s and tower name {@link TextField} to "None".
     * The tower {@link ImageView} image is set to null.
     */
    private void displayNull() {
        resourceAmountLabel.setText("None");
        cooldownLabel.setText("None");
        resourceTypeLabel.setText("None");
        towerNameTextField.setText("None");
        towerImage.setImage(null);
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
     * Loops through the round buttons and colours the one that is selected. Other button colours are removed by
     * setting the style to "".
     * @param roundButton {@link Button}
     */
    private void updateRoundButton(Button roundButton){
        for (Button button : roundButtons){
            if (button == roundButton){
                button.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
            }
            else{
                button.setStyle("");
            }
        }
    }

    /**
     * Sets the target round to 5 in the {@link GameSetupService} and updates the round buttons
     * with the selected button coloured.
     */
    @FXML
    private void on5RoundsClicked() {
        gameSetupService.setTargetRound(5);
        updateRoundButton(round5Button);
    }

    /**
     * Sets the target round to 10 in the {@link GameSetupService} and updates the round buttons
     * with the selected button coloured.
     */
    @FXML
    private void on10RoundsClicked() {
        gameSetupService.setTargetRound(10);
        updateRoundButton(round10Button);
    }

    /**
     * Sets the target round to 15 in the {@link GameSetupService} and updates the round buttons
     * with the selected button coloured.
     */
    @FXML
    private void on15RoundsClicked() {
        gameSetupService.setTargetRound(15);
        updateRoundButton(round15Button);
    }

    /**
     * Sets the Difficulty to "Normal" in the {@link GameSetupService}, updates the difficulty buttons
     * with the selected button coloured and changes the difficulty label text with rules.
     */
    @FXML
    private void onNormalClicked() {
        gameSetupService.setDifficulty(0);
        normalDifficultyButton.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
        hardDifficultyButton.setStyle("");
        difficultyLabel.setText("Normal difficulty: All carts resource type is based on the player's tower's resource type");
    }

    /**
     * Sets the Difficulty to "Hard" in the {@link GameSetupService}, updates the difficulty buttons
     * with the selected button coloured and changes the difficulty label text with rules.
     */
    @FXML
    private void onHardClicked() {
        gameSetupService.setDifficulty(1);
        hardDifficultyButton.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
        normalDifficultyButton.setStyle("");
        difficultyLabel.setText("Hard difficulty: At least one of the carts resource type is based on the player's tower's resource type");
    }

    /**
     * Checks if the player has:
     *   - Written a player name which matches the conditions in the {@link GameSetupService#checkName(String)}
     *   - Selected a target round
     *   - Selected a difficulty
     *   - Selected 3 towers
     * If all those conditions are met, the first round starts.
     */
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
            runFirstRound(gameData);
        }
    }

    /**
     * Stores the {@link GameData} and the {@link InventoryManager} in the {@link GameManager} and then
     * launches the "Round Selector" Screen.
     * @param gameData {@link GameData}
     */
    private void runFirstRound(GameData gameData) {
        gameData.setName(playerNameTextField.getText());
        InventoryManager inventoryManager = new InventoryManager(gameSetupService.createInventory(addedTowers));
        gameManager.setGameData(gameData);
        gameManager.setShopData(null);
        gameManager.setInventoryManager(inventoryManager);
        gameManager.getGameGUIManager().launchScreen("Round Selector");
    }

    /**
     * Checks if every space in the added {@link Tower} array is filled.
     * Returns false if there is at least one space that is null.
     * @return {@link Boolean}
     */
    private boolean isAddedTowersFull(){
        for(Tower tower : addedTowers){
            if(tower == null){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the selected {@link Tower} is a default tower and if the custom tower name meets the conditions.
     * If these conditions are equal to true, the tower gets added to the added tower array.
     */
    @FXML
    private void onAddTowerClicked() {
        if (selectedTowerIndex != -1 && selectedTowerIndex < gameManager.getDefaultTowers().length) {
            if(gameSetupService.checkName(towerNameTextField.getText())){
                addTowerToAddedTowers();
            } else {
                errorPopUp("Type a tower name between 3 to 15 characters with no special characters.");
            }
        } else {
            errorPopUp("Select a default tower before adding it to the selected towers.");
        }
    }

    /**
     * Clones the selected default {@link Tower} and adds it to the added towers array.
     * This loops through the added towers array and if there are no space left, it does not get added to the array.
     */
    private void addTowerToAddedTowers() {
        for (int i = 0; i < addedTowers.length; i++) {
            if (addedTowers[i] == null) {
                Tower tempTower = gameManager.getDefaultTowers()[selectedTowerIndex].clone();
                tempTower.setName(towerNameTextField.getText());
                addedTowers[i] = tempTower;
                towerButtons.get(i+gameManager.getDefaultTowers().length).setText(towerNameTextField.getText());
                return;
            }
        }
    }

    /**
     * Renames the selected tower with the {@link String} in the tower name {@link TextField}.
     * If the name does not meet the conditions in {@link GameSetupService} or if the selected
     * {@link Tower} is not an added tower, the tower does not get renamed.
     */
    @FXML
    private void onRenameTowerClicked(){
        if (selectedTowerIndex != -1 && selectedTowerIndex >= gameManager.getDefaultTowers().length) {
            if(gameSetupService.checkName(towerNameTextField.getText())){
                addedTowers[selectedTowerIndex-gameManager.getDefaultTowers().length].setName(towerNameTextField.getText());
            } else {
                errorPopUp("Type a tower name between 3 to 15 characters with no special characters.");
            }
        } else {
            errorPopUp("You can only rename a selected tower");
        }
    }

    /**
     * Removes the selected added {@link Tower} from the added tower array and updates the
     * tower stat {@link Label}s and tower {@link ImageView} to null. If the selected tower is a default
     * it does not get removed.
     */
    @FXML
    private void onRemoveTowerClicked() {
        if (selectedTowerIndex >= gameManager.getDefaultTowers().length) {
            addedTowers[selectedTowerIndex-gameManager.getDefaultTowers().length] = null;
            towerButtons.get(selectedTowerIndex).setText("");
            displayNull();
        } else {
            errorPopUp("You can not remove a default tower");
        }
    }

    /**
     * Displays the {@link Tower} {@link ImageView} which is dependent
     * on the given towers {@link seng201.team8.models.Resource} type.
     * @param tower {@link Tower}
     */
    private void displayTowerImage(Tower tower){
        Image image = new Image("/images/towers/" + tower.getTowerStats().getResourceType().name().toLowerCase() + ".jpg");
        towerImage.setImage(image);
    }
}
