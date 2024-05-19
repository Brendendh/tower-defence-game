package seng201.team8.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *  This class starts the JavaFX application window.
 */
public class FXWindow extends Application {

    /**
     * Opens the gui with the fxml content specified in resources/fxml/FXWrapper.fxml.
     * @param primaryStage The current fxml stage, handled by JavaFX
     * @throws IOException if there is an issue loading the fxml file
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/FXWrapper.fxml"));
        Parent root = baseLoader.load();
        FXWrapper fxWrapper = baseLoader.getController();
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("FX Wrapper");
        primaryStage.setScene(scene);
        primaryStage.show();
        fxWrapper.init(primaryStage);
    }

    /**
     * Launches the FXML application.
     * @param args String[]
     */
    public static void launchWrapper(String[] args){
        launch(args);
    }
}
