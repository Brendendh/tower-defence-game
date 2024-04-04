package seng201.team8;

import seng201.team8.gui.MainWindow;

/**
 * Default entry point class
 * @author seng201 teaching team
 */
public class App {

    /**
     * Entry point which runs the javaFX application
     * Due to how JavaFX works we must call MainWindow.launchWrapper() from here,
     * trying to run MainWindow itself will cause an error
     * @param args program arguments from command line
     */
    public static void main(String[] args) {
        MainWindow.launchWrapper(args);
    }
}
