package seng201.team8.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import seng201.team8.services.GameManager;
import seng201.team8.services.RandomEventsService;

import java.io.FileNotFoundException;
import java.util.Random;

/**
 * The Controller class that manages the gui elements of the Random Events screen and
 * communicates with the {@link RandomEventsService}.
 */
public class RandomEventScreenController {
    /**
     * The {@link Label} that displays the random event's name.
     * @see RandomEventScreenController#setEventName(int)
     */
    @FXML
    private Label eventName;
    /**
     * The {@link ImageView} that displays the current random event's image.
     * @see RandomEventScreenController#setImageDisplay(int)
     */
    @FXML
    private ImageView imageDisplay;
    /**
     * The {@link Label} that displays the description of the current
     * random event.
     */
    @FXML
    private Label loreTextField;
    /**
     * The {@link Button} that runs the method to switch scenes to the "Round Selector"
     * scene.
     * @see RandomEventScreenController#moveToRoundSelector(ActionEvent)
     */
    @FXML
    private Button proceedButton;
    /**
     * The game's {@link GameManager} to be used by the {@link RandomEventScreenController#randomEventsService} and
     * to launch the next screen via the Gui Manager.
     * @see GameManager#getGameGUIManager()
     */
    private final GameManager gameManager;
    /**
     * The {@link RandomEventsService} in charge of the logic behind the random events.
     */
    private final RandomEventsService randomEventsService;
    /**
     * An Array of the different name {@link String}s of different random events. The event name at index i corresponds
     * to the same event description in {@link RandomEventScreenController#eventLore}, the same event image link in
     * {@link RandomEventScreenController#imageURL} and the corresponding random event executed by
     * {@link RandomEventsService#executeRandomEvent(int i)}
     */
    private  final String[] eventNames = new String[]{"Generous Donation?","The Wandering Drunk Wizard","Force of Nature", "Morale Boost"};
    /**
     * An Array of the different description {@link String}s of different random events. The event description at index i corresponds
     * to the same event name in {@link RandomEventScreenController#eventNames}, the same event image link in
     * {@link RandomEventScreenController#imageURL} and the corresponding random event executed by
     * {@link RandomEventsService#executeRandomEvent(int i)}
     */
    private final String[] eventLore = new String[]{
            "The efficiency of your facilities has caught the attention of ?????. They have decided to support your efforts as a reward, with no strings tied of course. One of your tower level has increased by one",
            "Over the night a drunk wizard wandered into your facility and in their drunkenness, accidentally cast a spell on your towers. One of your tower resource type has been randomized",
            "A meteor has struck your towers, and although your towers were built by the capable hands of civil engineers graduated from UC, it could not withstand the impact. One of your towers is now destroyed",
            "Due to the success of today's work, your workers received a huge boost in morale and spent all night at the local pub. This newfound morale made your workers more efficient when working. One of your tower's resource produced per production has increased"};
    /**
     * An Array of the different image url {@link String}s of different random events. The event image url at index i corresponds
     * to the same event description in {@link RandomEventScreenController#eventLore}, the same event name in
     * {@link RandomEventScreenController#eventNames} and the corresponding random event executed by
     * {@link RandomEventsService#executeRandomEvent(int i)}
     */
    private final String[] imageURL = new String[]{
            "/images/randomEventsImages/generousDonation.jpg",
            "/images/randomEventsImages/drunkWizard.jpg",
            "/images/randomEventsImages/forceOfNature.jpg",
            "/images/randomEventsImages/moraleBoost.jpg"
    };

    /**
     * The constructor for the RandomEventScreenController that takes in the game's
     * {@link GameManager} as a parameter to be stored. A new instance of {@link RandomEventsService}
     * is created to handle the logic behind random events.
     * @param gameManager {@link GameManager}
     */
    public RandomEventScreenController(GameManager gameManager){
        this.gameManager = gameManager;
        this.randomEventsService = new RandomEventsService(this.gameManager);
    }

    /**
     * Uses the {@link RandomEventScreenController#gameManager} to access the GameGuiManager
     * to switch the scene to "Round Selector".
     * <p></p>
     * Is called when {@link RandomEventScreenController#proceedButton} is pressed by the player.
     * @see seng201.team8.services.GameGUIManager#launchScreen(String)
     * @param event
     */
    @FXML
    void moveToRoundSelector(ActionEvent event) {
        gameManager.getGameGUIManager().launchScreen("Round Selector");
    }

    /**
     * Sets the text of {@link RandomEventScreenController#eventName} to display the name of the event
     * obtained from indexing i from {@link RandomEventScreenController#eventNames}
     * @param i The event {@link Integer} index
     */
    private  void setEventName(int i ){
        eventName.setText(eventNames[i]);
        eventName.setTextFill(Color.color(1,0.3,0));
    }
    /**
     * Sets the image of {@link RandomEventScreenController#imageDisplay} to display the image of the event
     * obtained from indexing i from {@link RandomEventScreenController#imageURL}
     * <p></p>
     * Throws a {@link FileNotFoundException} if the image url is invalid.
     * @param i The event {@link Integer} index
     */
    private void setImageDisplay(int i) {
        Image image = new Image(imageURL[i]);
        imageDisplay.setImage(image);
    }

    /**
     * Immediately randomly selects a random event to be executed and calls {@link RandomEventsService} to execute the
     * event and update the visuals by calling {@link RandomEventScreenController#setEventName(int)} and
     * {@link RandomEventScreenController#setImageDisplay(int)}.
     * <p></p>
     * It then creates a {@link Timeline} to iteratively add the full event description text from
     * {@link RandomEventScreenController#eventLore} to {@link RandomEventScreenController#loreTextField} to simulate
     * a typing animation. Once the full text is displayed, the timeline is stopped. The player can skip the typing
     * animation and move on to the next screen by simply pressing {@link RandomEventScreenController#proceedButton}
     */
    public void initialize() {
        Random random = new Random();
        int randomEventNum = random.nextInt(4);
        randomEventsService.executeRandomEvent(randomEventNum);
        setImageDisplay(randomEventNum);
        setEventName(randomEventNum);
        String loreText = eventLore[randomEventNum];
        final IntegerProperty i = new SimpleIntegerProperty(0);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(0.05),
                event -> {
                    if (i.get() > loreText.length()){
                        timeline.stop();
                    }
                    else{
                        loreTextField.setText(loreText.substring(0,i.get()));
                        i.set(i.get()+1);
                    }
                }
        );
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

}
