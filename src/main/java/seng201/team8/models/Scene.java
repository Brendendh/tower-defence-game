package seng201.team8.models;

import seng201.team8.services.GameManager;

import java.lang.reflect.Constructor;

public class Scene {
    private String fxmlPath;
    private String sceneName;
    private final Constructor<?> controllerCreator;

    public Scene(String sceneName, String fxmlPath, Constructor<?> controllerCreator) {
        this.sceneName = sceneName;
        this.fxmlPath = fxmlPath;
        this.controllerCreator = controllerCreator;
    }

    public String getFxmlPath() {return fxmlPath;}

    public String getSceneName() {return sceneName;}

    public Constructor<?> getControllerCreator() {return controllerCreator;}
}
