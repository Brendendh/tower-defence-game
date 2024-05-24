package seng201.team8.gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import seng201.team8.models.*;
import seng201.team8.services.GameManager;
import seng201.team8.services.InventoryManager;
import seng201.team8.services.RoundSelectorService;

import java.util.*;

/**
 * The controller class that handles communication between the GUI elements of the round selector screen and
 * the {@link RoundSelectorService}.
 */
public class RoundSelectorScreenController {
    /**
     * The {@link Button} for the first generated {@link Round}
     */
    @FXML
    private Button roundOption1;
    /**
     * The {@link Button} for the second generated {@link Round}
     */
    @FXML
    private Button roundOption2;
    /**
     * The {@link Button} for the third generated {@link Round}
     */
    @FXML
    private Button roundOption3;
    /**
     * The index for the chosen {@link Round}
     */
    private int chosenRoundIndex;
    /**
     * The game's current {@link GameManager}
     */
    private GameManager gameManager;
    /**
     * The {@link RoundSelectorService} created upon initialization to handle
     * the generation of {@link Round}s.
     */
    private RoundSelectorService roundSelectorService;
    /**
     * A {@link List} containing all the {@link Round} {@link Button}
     */
    private List<Button> roundButtons;
    /**
     * An Array containing all the generated {@link Round} by the {@link RoundSelectorScreenController#roundSelectorService}
     */
    private Round[] possibleRounds;
    /**
     * A boolean to keep track if a round has been chosen or not.
     */
    private boolean roundNotChosen;

    /**
     * Called when the "proceed to next round" button is pressed.
     * <p></p>
     * If a {@link Round} has not yet been chosen, calls {@link RoundSelectorScreenController#errorPopUp(String)} to notify
     * the player to do so.
     * <p></p>
     * Else, updates the round information in the {@link GameManager} and switches the screen to "Game Menu".
     * @param event
     */
    @FXML
    void proceedToGameMenuScreen(ActionEvent event) {
        if (roundNotChosen){
            errorPopUp("Please select a round first");
        }
        else{
            gameManager.setRound(possibleRounds[chosenRoundIndex]);
            gameManager.setRoundResourceDisplay(calculateResourceRatio(possibleRounds[chosenRoundIndex]));
            gameManager.getGameGUIManager().launchScreen("Game Menu");
        }
    }

    /**
     * The constructor for RoundSelectorScreenController.
     * <p></p>
     * Takes in a {@link GameManager} to store and initializes a new {@link RoundSelectorService} and generates the possible
     * rounds.
     * @param gameManager
     */
    public RoundSelectorScreenController(GameManager gameManager){
        roundNotChosen = true;
        this.gameManager = gameManager;
        this.roundSelectorService = new RoundSelectorService(this.gameManager);
        this.possibleRounds = this.roundSelectorService.getPossibleRounds();
    }
    /**
     * The main {@link Exception} handling method used to handle all thrown exceptions.
     * <p></p>
     * Creates a new {@link DialogPane} popup to display the error message to the player.
     * @param errorMessage the error message {@link String} to display
     */
    private void errorPopUp(String errorMessage){
        Dialog errorPane = new Dialog();
        errorPane.setContentText(errorMessage);
        errorPane.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        errorPane.show();
    }

    /**
     * Takes in a {@link Round} and returns an ArrayList of {@link Resource} representing a simplified ratio of
     * resource types required by the {@link Cart}. If a particular resource is more needed, it will be represented in the ArrayList.
     * @param round {@link Round{}
     * @return {@link ArrayList}
     */
    private ArrayList<Resource> calculateResourceRatio(Round round){
        float ratio = (float) 3.0/round.getCartNumber();
        Map<Resource,Integer> resourceCounter = new HashMap<>();
        ArrayList<Resource> avaliableResource = new ArrayList<>();
        for (Cart cart: round.getCarts()){
            if (resourceCounter.containsKey(cart.getResourceType())){
                int resourceCount = resourceCounter.get(cart.getResourceType());
                resourceCounter.put(cart.getResourceType(), resourceCount+1);
            }
            else{
                avaliableResource.add(cart.getResourceType());
                resourceCounter.put(cart.getResourceType(),1);
            }
        }
        ArrayList<Resource> resourceDisplayList = new ArrayList<>();
        for (Resource resource:avaliableResource){
            int numOfCartsOfThisRss = Math.round(resourceCounter.get(resource) * ratio);
            if (numOfCartsOfThisRss == 0){numOfCartsOfThisRss = 1;}
            for (int i = 0; i < numOfCartsOfThisRss; i++){
                resourceDisplayList.add(resource);
            }
        }
        return resourceDisplayList;
    }

    /**
     * Takes in a {@link Button}, {@link Round} and {@link Integer optionNumber} as parameters.
     * <p></p>
     * Sets the display text of the button to display information about the round and the option number.
     * @param button {@link Button}
     * @param round {@link Round}
     * @param optionNumber {@link Integer}
     */
    private void updateButtonDisplay(Button button, Round round, int optionNumber){
        ArrayList<Resource> listOfResourceToDisplay = calculateResourceRatio(round);
        String buttonText = "Option "+optionNumber+"\n"+"Distance to travel: "+round.getDistanceAllowed()+"\n" +
                "Number of carts: "+round.getCartNumber()+"\n"+
                "Cart speed range: "+(1+(gameManager.getGameData().getRound())/6)+" to "+(2+(gameManager.getGameData().getRound())/6)+"\n"+
                "Estimated resources: ";
        for (Resource resource:listOfResourceToDisplay){
            buttonText = buttonText + resource+"\n"+"                                  ";
        }
        button.setText(buttonText);

    }

    /**
     * Groups all the round buttons into {@link RoundSelectorScreenController#roundButtons}
     * and then calls {@link RoundSelectorScreenController#updateRoundButtonsOnActionEvent()}
     */
    public void initialize(){
        roundButtons = List.of(roundOption1,roundOption2,roundOption3);
        updateRoundButtonsOnActionEvent();
    }

    /**
     * Calls {@link RoundSelectorScreenController#updateButtonDisplay(Button, Round, int)} for each {@link Button} in
     * {@link RoundSelectorScreenController#roundButtons}.
     * <p></p>
     * Then makes sure that each button properly updates {@link RoundSelectorScreenController#chosenRoundIndex} and
     * has a "toggled" pressed effect when selected to make it easier for the player to know which round is currently
     * selected.
     */
    private void updateRoundButtonsOnActionEvent() {
        for (int i = 0; i < roundButtons.size(); i++){
            int finalI = i;
            updateButtonDisplay(roundButtons.get(i),possibleRounds[i], i + 1);
            roundButtons.get(i).setOnAction(event ->{
                roundNotChosen = false;
                chosenRoundIndex = finalI;
                roundButtons.forEach(button -> {
                    if (button == roundButtons.get(finalI)) {
                        button.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
                    } else {
                        button.setStyle("");
                    }
                });
            });
        }
    }
}
