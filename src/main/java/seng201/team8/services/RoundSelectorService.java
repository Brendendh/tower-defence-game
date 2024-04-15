package seng201.team8.services;

import seng201.team8.models.Cart;
import seng201.team8.models.InventoryData;
import seng201.team8.models.Round;
import seng201.team8.models.Tower;

import java.util.ArrayList;
import java.util.Random;

public class RoundSelectorService {
    private GameManager gameManager;
    private Round[] possibleRounds;
    private Random randomGenerator;
    private int currentRoundNumber;

    public RoundSelectorService(GameManager gameManager){
        this.gameManager = gameManager;
        this.currentRoundNumber = gameManager.getGameData().getRound();
        this.randomGenerator = new Random();
        createPossibleRounds();
    }
    public Round[] getPossibleRounds() {
        return possibleRounds;
    }

    public Round getSelectedRound(int selectedRoundIndex){
        return possibleRounds[selectedRoundIndex];
    }
    public void createPossibleRounds(){
        possibleRounds = new Round[3];
        for (int i = 0; i < 3; i++){
            possibleRounds[i] = generateRound();
        }
    }

    private Round generateRound(){
        int lowerBound = 3 + ((currentRoundNumber/6)*3);
        int upperBound = 5 + ((currentRoundNumber/6)*3);
        int numberOfCarts = randomGenerator.nextInt(lowerBound,upperBound);
        Round generatedRound = new Round(numberOfCarts);
        for (int i = 0; i < numberOfCarts; i++){
            generatedRound.addCart(generateCart(i));
        }
        return generatedRound;
    }

    private Cart generateCart(int numOfCartsSoFar){
        ArrayList<String> ownedResourceTypes = getPlayerOwnedResourceTypes();
        if (numOfCartsSoFar == 0){
            int randomOwnedResourceIndex = randomGenerator.nextInt(ownedResourceTypes.size());
            return new Cart(generateTargetAmount(), ownedResourceTypes.get(randomOwnedResourceIndex),generateSpeed());
        }
        else{
            int randomResourceIndex = randomGenerator.nextInt(gameManager.getDefaultResources().length);
            return new Cart(generateTargetAmount(), gameManager.getDefaultResources()[randomResourceIndex],generateSpeed());
        }
    }

    private ArrayList<String> getPlayerOwnedResourceTypes(){
        InventoryData inventoryData = gameManager.getInventoryManager().getInventoryData();
        ArrayList<String> ownedResourceTypes = new ArrayList<String>();
        for (Tower mainTower: inventoryData.getMainTowers()){
            if (!ownedResourceTypes.contains(mainTower.getTowerStats().getResourceType())){
                ownedResourceTypes.add(mainTower.getTowerStats().getResourceType());
            }
        }
        for (Tower reserveTower: inventoryData.getReserveTowers()){
            if (!ownedResourceTypes.contains(reserveTower.getTowerStats().getResourceType())){
                ownedResourceTypes.add(reserveTower.getTowerStats().getResourceType());
            }
        }
        return ownedResourceTypes;
    }
    private int generateTargetAmount(){
        int targetAmount;
        int lowerBound = 30 + (currentRoundNumber*5);
        int upperBound = 40 + (currentRoundNumber*5);
        targetAmount = randomGenerator.nextInt(lowerBound, upperBound + 1);
        return targetAmount;
    }

    private int generateSpeed(){
        return randomGenerator.nextInt(1+(currentRoundNumber/6),2+(currentRoundNumber/6));
    }
}
