package seng201.team8.services;

import seng201.team8.models.*;

import java.util.ArrayList;
import java.util.Random;

public class RoundSelectorService {
    private final GameManager gameManager;
    private Round[] possibleRounds;
    private final Random randomGenerator;
    private final int currentRoundNumber;
    private final int gameDifficulty; //0 = normal, 1 = hard

    public RoundSelectorService(GameManager gameManager){
        this.gameManager = gameManager;
        this.currentRoundNumber = gameManager.getGameData().getRound();
        this.randomGenerator = new Random();
        this.gameDifficulty = this.gameManager.getGameData().getDifficulty();
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
        ArrayList<Resource> ownedResourceTypes = getPlayerOwnedResourceTypes();
        if (numOfCartsSoFar == 0 || gameDifficulty == 0){
            int randomOwnedResourceIndex = randomGenerator.nextInt(ownedResourceTypes.size());
            Resource chosenResource = ownedResourceTypes.get(randomOwnedResourceIndex);
            return new Cart(generateTargetAmount(chosenResource), chosenResource,generateSpeed());
        }
        else{
            int randomResourceIndex = randomGenerator.nextInt(gameManager.getDefaultResources().length);
            Resource chosenResource = gameManager.getDefaultResources()[randomResourceIndex];
            return new Cart(generateTargetAmount(chosenResource), chosenResource,generateSpeed());
        }
    }

    private ArrayList<Resource> getPlayerOwnedResourceTypes(){
        InventoryData inventoryData = gameManager.getInventoryManager().getInventoryData();
        ArrayList<Resource> ownedResourceTypes = new ArrayList<Resource>();
        for (Tower mainTower: inventoryData.getMainTowers()){
            if (mainTower != null){
                if (!ownedResourceTypes.contains(mainTower.getTowerStats().getResourceType())){
                    ownedResourceTypes.add(mainTower.getTowerStats().getResourceType());
                }
            }
        }
        for (Tower reserveTower: inventoryData.getReserveTowers()){
            if (reserveTower != null) {
                if (!ownedResourceTypes.contains(reserveTower.getTowerStats().getResourceType())) {
                    ownedResourceTypes.add(reserveTower.getTowerStats().getResourceType());
                }
            }
        }
        return ownedResourceTypes;
    }
    private int generateTargetAmount(Resource resource){
        int targetAmount;
        int lowerBound = 200 + (currentRoundNumber*5);
        int upperBound = 210 + (currentRoundNumber*5);
        targetAmount = randomGenerator.nextInt(lowerBound/resource.getResourceValue(), (upperBound/resource.getResourceValue()) + 1);
        return targetAmount;
    }

    private int generateSpeed(){
        return randomGenerator.nextInt(1+(currentRoundNumber/6),2+(currentRoundNumber/6));
    }
}
