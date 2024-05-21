package seng201.team8.services;

import seng201.team8.gui.*;
import seng201.team8.models.Scene;

import java.util.HashMap;
import java.util.function.Consumer;

public class GameGUIManager {
    private Scene currentScene;
    private HashMap<String, Scene> scenes;
    private Consumer<GameManager> screenLauncher;
    private Runnable clearPane;
    private GameManager gameManager;

    public GameGUIManager(Consumer<GameManager> screenLauncher, Runnable clearPane, GameManager gameManager){
        this.screenLauncher = screenLauncher;
        this.clearPane = clearPane;
        this.gameManager = gameManager;
        createScenes();
    }

    public void createScenes(){
        scenes = new HashMap<>();
        try {
            scenes.put("Game Start", new Scene("Game Start", "GameStartScreen.fxml", GameStartController.class.getConstructor(GameManager.class)));
            scenes.put("Game Setup", new Scene("Game Setup", "GameSetupScreen.fxml", GameSetupController.class.getConstructor(GameManager.class)));
            scenes.put("Game Menu", new Scene("Game Menu", "GameMenuScreen.fxml", GameMenuController.class.getConstructor(GameManager.class)));
            scenes.put("Shop Screen", new Scene("Shop Screen", "ShopScreen.fxml", ShopScreenController.class.getConstructor(GameManager.class)));
            scenes.put("Round Selector", new Scene("Round Selector", "RoundSelectorScreen.fxml",RoundSelectorScreenController.class.getConstructor(GameManager.class)));
            scenes.put("Random Event", new Scene("Random Event","RandomEventScreen.fxml",RandomEventScreenController.class.getConstructor(GameManager.class)));
            scenes.put("Inventory Screen", new Scene("Inventory Screen", "InventoryScreen.fxml", InventoryController.class.getConstructor(GameManager.class)));
            scenes.put("Round End", new Scene("Round End", "RoundEvaluationScreen.fxml", RoundEndController.class.getConstructor(GameManager.class)));
            scenes.put("Game Play", new Scene("Game Play", "GamePlayScreen.fxml", GamePlayController.class.getConstructor(GameManager.class)));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void launchScreen(String sceneKey){
        clearPane.run();
        this.currentScene = scenes.get(sceneKey);
        this.screenLauncher.accept(this.gameManager);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
