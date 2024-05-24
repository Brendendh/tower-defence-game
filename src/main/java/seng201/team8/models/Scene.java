package seng201.team8.models;

import java.lang.reflect.Constructor;

/**
 * The model for the Scene objects.
 * <br><br>
 * Used as a part of GameGUIManager which stores the scenes in a HashMap and utilized for
 * launching into different screens in the game.
 *
 * @see seng201.team8.services.GameGUIManager
 */

public class Scene {
    /**
     * A String that represents the fxml file path that is stored in the "/resources/fxml/".
     */
    private final String fxmlPath;

    /**
     * A String that represents the name of this scene and to be used for the title of the window.
     */
    private final String sceneName;

    /**
     * A Constructor for a controller which will be used to create a new controller
     * when the respective screen is launched through FXWrapper.
     */
    private final Constructor<?> controllerCreator;

    /**
     * The constructor for a Scene object.
     * <br><br>
     * Takes in the name, fxml path and controller constructor for this scene.
     *
     * @param sceneName the String for the scene name
     * @param fxmlPath the String for the scene fxml file path
     * @param controllerCreator the Constructor for the scene controller
     */
    public Scene(String sceneName, String fxmlPath, Constructor<?> controllerCreator) {
        this.sceneName = sceneName;
        this.fxmlPath = fxmlPath;
        this.controllerCreator = controllerCreator;
    }

    /**
     * Returns the fxml path of the scene.
     *
     * @return String
     */
    public String getFxmlPath() {return fxmlPath;}

    /**
     * Returns the name of the scene.
     *
     * @return String
     */
    public String getSceneName() {return sceneName;}

    /**
     * Returns the constructor
     *
     * @return Constructor
     */
    public Constructor<?> getControllerCreator() {return controllerCreator;}
}
