package seng201.team8.gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import seng201.team8.models.*;
import seng201.team8.services.GameManager;
import seng201.team8.services.InventoryManager;
import seng201.team8.services.RoundSelectorService;

import java.util.*;

public class RoundSelectorScreenController {

    @FXML
    private Button roundOption1;

    @FXML
    private Button roundOption2;

    @FXML
    private Button roundOption3;
    private int chosenRoundIndex;
    private GameManager gameManager;
    private RoundSelectorService roundSelectorService;
    private List<Button> roundButtons;
    private Round[] possibleRounds;

    private boolean roundNotChosen;

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

    public RoundSelectorScreenController(GameManager gameManager){
        roundNotChosen = true;
        this.gameManager = gameManager;
        this.roundSelectorService = new RoundSelectorService(this.gameManager);
        this.possibleRounds = this.roundSelectorService.getPossibleRounds();
    }

    private void errorPopUp(String errorMessage){
        Dialog errorPane = new Dialog();
        errorPane.setContentText(errorMessage);
        errorPane.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        errorPane.show();
    }

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

    private void updateButtonDisplay(Button button, Round round, int optionNumber){
        ArrayList<Resource> listOfResourceToDisplay = calculateResourceRatio(round);
        String buttonText = "Option "+optionNumber+"\n"+"Distance to travel: "+round.getDistanceAllowed()+"\n" +
                "Number of carts: "+round.getCartNumber()+"\n"+
                "Cart speed range: "+(1+(gameManager.getGameData().getRound())/6)+" to "+(2+(gameManager.getGameData().getRound())/6)+"\n"+
                "Estimated resources: ";
        for (Resource resource:listOfResourceToDisplay){
            buttonText = buttonText + resource+"\n"+"                   ";
        }
        button.setText(buttonText);

    }

    public void initialize(){
        roundButtons = List.of(roundOption1,roundOption2,roundOption3);
        updateRoundButtonsOnActionEvent();
    }

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
