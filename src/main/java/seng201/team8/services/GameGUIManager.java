package seng201.team8.services;

import seng201.team8.gui.*;
import seng201.team8.models.Scene;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * The service class for GameGUIManager. Created with the GameManager.
 * <p></p>
 * Serves as hub that keeps tracks and stores the graphical information of the game. Such as the current game scene,
 * scenes that are available in the game and the functions to create and clear screens. Frequently accessed by other
 * controllers in order to delete the current screen and create a new different screen for players to navigate.
 */
public class GameGUIManager {
    /**
     * The current {@link Scene} the game is in.
     */
    private Scene currentScene;
    /**
     * The Hashmap which stores the String scene names as keys and
     * links to a {@link Scene} element.
     */
    private HashMap<String, Scene> scenes;
    /**
     * The Consumer object which accepts a GameManager. Used to
     * launch a new screen through {@link FXWrapper}.
     */
    private final Consumer<GameManager> screenLauncher;
    /**
     * The Runnable object which clears the screen as preparation to
     * create a new screen.
     */
    private final Runnable clearPane;
    /**
     * The {@link GameManager} object to deliver the reference for controllers to access.
     */
    private final GameManager gameManager;

    /**
     * The constructor for {@link GameGUIManager}.
     * <p></p>
     * Takes in a Consumer object which launches a screen, a Runnable object which clears the screen and a GameManager object.
     * These get stored in the GameGUIManager.
     * @param screenLauncher Consumer object which runs the launchScreen method in the FXWrapper with the parameter, GameManager.
     * @param clearPane Runnable object which clears the screen to create a new Screen.
     * @param gameManager {@link GameManager}
     */
    public GameGUIManager(Consumer<GameManager> screenLauncher, Runnable clearPane, GameManager gameManager){
        this.screenLauncher = screenLauncher;
        this.clearPane = clearPane;
        this.gameManager = gameManager;
        createScenes();
    }

    /**
     * Generates a HashMap with the {@link String} {@link Scene} names as keys and {@link Scene} objects as values.
     * Throws a {@link RuntimeException} if there is no controller constructor with the GameManager as the class.
     */
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
            scenes.put("Round End", new Scene("Round End", "RoundEndScreen.fxml", RoundEndController.class.getConstructor(GameManager.class)));
            scenes.put("Round Evaluation", new Scene("Round Evaluation", "RoundEvaluationScreen.fxml", RoundEvaluationController.class.getConstructor(GameManager.class)));
            scenes.put("Game Result", new Scene("Game Result", "GameVictoryScreen.fxml",GameVictoryController.class.getConstructor(GameManager.class)));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Launches a new screen with the given {@link String} sceneKey.
     * <p></p>
     * Clears the current screen, gets the {@link Scene} object with the
     * {@link String} sceneKey and then launches the new screen with the
     * respective {@link Scene} object.
     * @param sceneKey {@link String}
     */
    public void launchScreen(String sceneKey){
        clearPane.run();
        this.currentScene = scenes.get(sceneKey);
        this.screenLauncher.accept(this.gameManager);
    }

    /**
     * Getter for {@link GameGUIManager#currentScene}.
     * @return {@link GameGUIManager#currentScene}
     */
    public Scene getCurrentScene() {
        return currentScene;
    }

}
