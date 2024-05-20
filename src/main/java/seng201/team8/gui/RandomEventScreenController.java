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
import seng201.team8.models.*;
import seng201.team8.services.GameManager;
import seng201.team8.services.InventoryManager;
import seng201.team8.services.RandomEventsService;

import javax.imageio.stream.FileImageInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Random;

public class RandomEventScreenController {

    @FXML
    private Label eventName;

    @FXML
    private ImageView imageDisplay;

    @FXML
    private Label loreTextField;

    @FXML
    private Button proceedButton;

    private GameManager gameManager;
    private RandomEventsService randomEventsService;
    private Random random;
    private int randomEventNum;
    private boolean finishedTyping;
    private  final String[] eventNames = new String[]{"Generous Donation","The Wandering Drunk Wizard","Force of Nature", "Morale Boost"};

    private final String[] eventLore = new String[]{
            "The Lord of the territory is pleased with the output of your facility. They have decided to support your efforts by upgrading one of your tower's level by one",
            "Over the night a drunk wizard wandered into your facility and in their drunkenness, accidentally casted a spell on your towers. One of your tower's resource type has been randomized",
            "An earthquake has struck your towers, and although your towers were built by the capable hands of civil engineers graduated from UC, it could not withstand the might of nature. One of your towers is now destroyed",
            "Due to the success of today's work, your workers received a huge boost in morale and spent all night at the local tavern. This newfound morale made your workers more efficient when working. One of your tower's resource produced per production has increased"};
    private final String[] imageURL = new String[]{
            "src/main/resources/images/randomEventsImages/generousDonation.jpg",
            "src/main/resources/images/randomEventsImages/drunkWizard.jpg",
            "src/main/resources/images/randomEventsImages/forceOfNature.jpg",
            "src/main/resources/images/randomEventsImages/moraleBoost.jpg"
    };
    public RandomEventScreenController(GameManager gameManager){
        finishedTyping = false;
        this.gameManager = gameManager;
        this.randomEventsService = new RandomEventsService(this.gameManager);
    }

    @FXML
    void moveToRoundSelector(ActionEvent event) {
        gameManager.getGameGUIManager().launchScreen("Round Selector");
    }

    private  void setEventName(int i ){
        eventName.setText(eventNames[i]);
        eventName.setTextFill(Color.color(1,0.3,0));
    }
    private void setImageDisplay(int i) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(imageURL[i]));
        imageDisplay.setImage(image);
    }
    private void typingText(int eventNum){
        String lore = eventLore[eventNum];
        for (int i = 0; i < lore.length();i++){
            loreTextField.setText(loreTextField.getText()+lore.charAt(i));
        }
        finishedTyping = true;

    }

    public void initialize() throws FileNotFoundException {
        random = new Random();
        randomEventNum = random.nextInt(4);
        randomEventsService.executeRandomEvent(randomEventNum);
        setImageDisplay(randomEventNum);
        setEventName(randomEventNum);
        String loreText = eventLore[randomEventNum];
        final IntegerProperty i = new SimpleIntegerProperty(0);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(0.1),
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
