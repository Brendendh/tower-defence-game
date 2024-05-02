package seng201.team8.services;

import seng201.team8.gui.GameMenuController;
import seng201.team8.gui.GameStartController;
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
            scenes.put("Game Menu", new Scene("Game Menu", "GameMenuScreen.fxml", GameMenuController.class.getConstructor(GameManager.class)));
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
