package seng201.team8.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import seng201.team8.models.Tower;
import seng201.team8.services.GameManager;
import seng201.team8.services.GameSetupService;

import java.util.List;

public class GameSetupController {
    private GameManager gameManager;
    private GameSetupService gameSetupService;
    public GameSetupController(GameManager gameManager) {this.gameManager = gameManager; this.gameSetupService = new GameSetupService();}

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

    @FXML
    private void on5RoundsClicked() {}
    @FXML
    private void on10RoundsClicked() {}
    @FXML
    private void on15RoundsClicked() {}
    @FXML
    private void onNormalClicked() {}
    @FXML
    private void onHardClicked() {}
    @FXML
    private void onStartGameClicked() {}
    @FXML
    private void onAddTowerClicked() {}
    @FXML
    private void onRemoveTowerClicked() {}
}
