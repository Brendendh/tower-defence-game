package seng201.team8.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seng201.team8.models.Scene;
import seng201.team8.services.GameManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * The class for switching between Scenes.
 * <p></p>
 * Used by the GameGUIManager to launch to a specific Scene.
 * <p></p>
 * A controller will utilize the FXWrapper through the GameGUIManager by clearing
 * the mainPane and integrating the elements in a FXML file stored in the Scene.
 *
 * @see seng201.team8.services.GameGUIManager
 */
public class FXWrapper {

    /**
     * A Pane object which will display the fxml content specified through launchScreen.
     */
    @FXML
    private Pane mainPane;

    /**
     * The stage created from the FXWindow. To set the title of the window.
     */
    private Stage stage;

    /**
     * Stores the stage from the FXWindow and creates a new GameManager.
     */
    public void init(Stage stage) {
        this.stage = stage;
        new GameManager(this::launchScreen, this::clearPane);
    }

    /**
     * Loads the title, controller and fxml content from the Scene object and adds it to the mainPane.
     * clearPane should be executed first before executing launchScreen.
     *
     * @param gameManager the GameManager for the next Scene details and to be referenced by the new controller
     */
    @FXML
    public void launchScreen(GameManager gameManager){
        try {
            Scene scene = gameManager.getGameGUIManager().getCurrentScene();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + scene.getFxmlPath()));
            fxmlLoader.setControllerFactory(param -> {
                try {
                    return scene.getControllerCreator().newInstance(gameManager);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });

           Parent setupParent = fxmlLoader.load();
           mainPane.getChildren().add(setupParent);
           stage.setTitle(scene.getSceneName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the mainPane to prepare to display new fxml content.
     */
    public void clearPane() {
        mainPane.getChildren().removeAll(mainPane.getChildren());
    }
}
