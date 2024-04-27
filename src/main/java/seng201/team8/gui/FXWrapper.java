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


public class FXWrapper {

    @FXML
    private Pane mainPane;

    private Stage stage;

    public void init(Stage stage) {
        this.stage = stage;
        new GameManager(this::launchScreen, this::clearPane);
    }

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

    public void clearPane() {
        mainPane.getChildren().removeAll(mainPane.getChildren());
    }
}
