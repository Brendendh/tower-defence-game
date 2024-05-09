package seng201.team8.gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML
    private Button selectRoundButton;

    private int chosenRoundIndex;
    private GameManager gameManager;
    private RoundSelectorService roundSelectorService;
    private List<Button> roundButtons;
    private Round[] possibleRounds;

    @FXML
    void proceedToRoundEvaluation(ActionEvent event) {
        gameManager.setRound(possibleRounds[chosenRoundIndex]);
        gameManager.getGameGUIManager().launchScreen("Shop Screen");
    }

    public RoundSelectorScreenController(GameManager gameManager){
        InventoryManager inventoryManager = new InventoryManager(new InventoryData());
        ArrayList<Upgrade> upgrades = new ArrayList<>();
        upgrades.add(new Upgrade(new ExpBoost(10), Rarity.COMMON, 10, 1));
        upgrades.add(new Upgrade(new CooldownReduction(10), Rarity.COMMON, 10, 1));
        upgrades.add(new Upgrade(new ResourceAmountBoost(10), Rarity.RARE, 10, 1));
        upgrades.add(new Upgrade(new RepairTower(), Rarity.EPIC, 10, 1));
        inventoryManager.getInventoryData().setUpgrades(upgrades);
        Tower[] mainTowers = new Tower[]{new Tower("Hi", new TowerStats(10, Resource.CORN, 1 ), 10, Rarity.COMMON),null,
                new Tower("Brenden", new TowerStats(35, Resource.CORN, 3), 15, Rarity.EPIC),null,null};
        Tower[] reserveTowers = new Tower[]{null,null,null,
                new Tower("Firefly is a good dps", new TowerStats(35, Resource.CORN, 3), 15, Rarity.RARE),null};
        GameData gameData = new GameData();
        gameData.setRound(13);
        gameData.setMoney(50);
        gameData.setPoint(150);
        inventoryManager.getInventoryData().setMainTowers(mainTowers);
        inventoryManager.getInventoryData().setReserveTowers(reserveTowers);
        this.gameManager = new GameManager(gameData,inventoryManager);
        this.roundSelectorService = new RoundSelectorService(this.gameManager);
        this.possibleRounds = this.roundSelectorService.getPossibleRounds();
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
        for (int i = 0; i < roundButtons.size(); i++){
            int finalI = i;
            updateButtonDisplay(roundButtons.get(i),possibleRounds[i], i + 1);
            roundButtons.get(i).setOnAction(event ->{
                chosenRoundIndex = finalI;
            });
        }
    }

}
