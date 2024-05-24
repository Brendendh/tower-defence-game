package seng201.team8.services;

import seng201.team8.models.*;
import seng201.team8.models.dataRecords.InventoryData;

import java.util.ArrayList;
import java.util.Random;

/**
 * The service class for RoundSelectorService.
 * <p></p>
 * Responsible for handling the logic behind generating the three rounds the player can
 * choose from after they complete a round. The rounds generated have their difficulty
 * calculated based of the current round number and the chosen game difficulty by the player.
 *
 * @see Round
 */
public class RoundSelectorService {
    /**
     * The {@link GameManager} of the current game, stored by the service to access
     * the current round's number and difficulty.
     */
    private final GameManager gameManager;
    /**
     * An array of the 3 generated rounds, accessed by the RoundSelectorScreenController
     * in order to properly display them to the player.
     * @see seng201.team8.gui.RoundSelectorScreenController
     */
    private Round[] possibleRounds;
    /**
     * Used to generate random numbers for generating rounds.
     * @see Random
     */
    private final Random randomGenerator;
    /**
     * The current round number integer.
     */
    private final int currentRoundNumber;
    /**
     * The current game's difficulty.
     */
    private final int gameDifficulty; //0 = normal, 1 = hard

    /**
     * The constructor for the RoundSelectorService.
     * <p></p>
     * Takes in a {@link GameManager} in order to have access to the current round number and
     * game difficulty. The three possible rounds are immediately generated upon the service's
     * creation.
     * <p></p>
     * Called by RoundSelectorScreenController to generate the rounds.
     * @param gameManager {@link GameManager}
     * @see seng201.team8.gui.RoundSelectorScreenController
     */
    public RoundSelectorService(GameManager gameManager){
        this.gameManager = gameManager;
        this.currentRoundNumber = gameManager.getGameData().getRound();
        this.randomGenerator = new Random();
        this.gameDifficulty = this.gameManager.getGameData().getDifficulty();
        createPossibleRounds();
    }

    /**
     * Getter for {@link RoundSelectorService#possibleRounds}
     * @return {@link RoundSelectorService#possibleRounds}
     */
    public Round[] getPossibleRounds() {
        return possibleRounds;
    }

    /**
     * Returns the one of the generated rounds from {@link RoundSelectorService#possibleRounds}
     * by calling the round's index.
     * @param selectedRoundIndex the index of the selected round from {@link RoundSelectorService#possibleRounds}
     * @return {@link Round}
     */
    public Round getSelectedRound(int selectedRoundIndex){
        return possibleRounds[selectedRoundIndex];
    }

    /**
     * Called at the start of the service. Fills up {@link RoundSelectorService#possibleRounds}
     * with randomly generated rounds generated by {@link RoundSelectorService#generateRound()}
     */
    public void createPossibleRounds(){
        possibleRounds = new Round[3];
        for (int i = 0; i < 3; i++){
            possibleRounds[i] = generateRound();
        }
    }

    /**
     * Called by {@link RoundSelectorService#createPossibleRounds()} to generate a single {@link Round} .
     * <p></p>
     * It pseudo randomly decides the number of carts first based on round number, finds out the types of resources
     * the player's towers can produce by calling {@link RoundSelectorService#getPlayerOwnedResourceTypes()} and then calls
     * {@link RoundSelectorService#generateCart(int, ArrayList)} to generate each {@link Cart} which then
     * gets added to the generated round.
     * @return the generated {@link Round}
     */
    private Round generateRound(){
        int lowerBound = 3 + ((currentRoundNumber/6)*3);
        int upperBound = 5 + ((currentRoundNumber/6)*3);
        int numberOfCarts = randomGenerator.nextInt(lowerBound,upperBound);
        Round generatedRound = new Round(numberOfCarts);
        ArrayList<Resource> ownedResourceTypes = getPlayerOwnedResourceTypes();
        for (int i = 0; i < numberOfCarts; i++){
            generatedRound.addCart(generateCart(i, ownedResourceTypes));
        }
        return generatedRound;
    }

    /**
     * Generates a single {@link Cart}. Takes in two parameters, an Integer numOfCartsSoFar and
     * a list of the type of player's tower's {@link Resource}.
     * <p></p>
     * If the number of carts generated in the round so far is 0, it will generate a cart that shares the same resource
     * type as one of the player's owned towers. This is to make sure "impossible" rounds don't exist.
     * <p></p>
     * If the game's difficulty is set to normal, it will continue to set the generated cart's resource type to
     * the types that the player's tower can produce. However, if the game difficulty is set to hard and at least one
     * cart has already been generated, then it will randomly select a resource from the default pool of resources in
     * GameManager, regardless if the player has a tower of that resource type or not.
     * <p></p>
     * It then calls {@link RoundSelectorService#generateTargetAmount(Resource)} and {@link RoundSelectorService#generateSpeed()}
     * to generate the cart's target amount of resources and the cart's speed.
     * <p></p>
     * Returns the generated cart
     *
     * @param numOfCartsSoFar An {@link Integer} number of the number of carts generated so far.
     * @param ownedResourceTypes An {@link ArrayList} of the player's owned tower's resource types.
     * @return the generated {@link Cart}
     * @see GameManager#getDefaultResources()
     */
    private Cart generateCart(int numOfCartsSoFar, ArrayList<Resource> ownedResourceTypes){
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

    /**
     * Goes through all the player's {@link Tower}, both main and reserve and returns an {@link ArrayList}
     * of the tower resource types.
     * <p></p>
     * The ArrayList has a max size of 3 as no duplicate resource is added to ensure
     * equal probability when selecting a cart's resource type.
     * @return An {@link ArrayList} of player tower's resource types
     * @see Resource
     */
    public ArrayList<Resource> getPlayerOwnedResourceTypes(){
        InventoryData inventoryData = gameManager.getInventoryManager().getInventoryData();
        ArrayList<Resource> ownedResourceTypes = new ArrayList<>();
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

    /**
     * Generates the target amount integer for a {@link Cart} based on the current round number and cart's
     * {@link Resource} type.
     * @param resource the cart's resource type
     * @return {@link Integer}
     */
    private int generateTargetAmount(Resource resource){
        int targetAmount;
        int lowerBound = 200 + (currentRoundNumber*5);
        int upperBound = 210 + (currentRoundNumber*5);
        targetAmount = randomGenerator.nextInt(lowerBound/resource.getResourceValue(), (upperBound/resource.getResourceValue()) + 1);
        return targetAmount;
    }

    /**
     * Generates the speed integer for a {@link Cart} based on the current round number.
     * @return {@link Integer}
     */
    private int generateSpeed(){
        return randomGenerator.nextInt(1+(currentRoundNumber/6),2+(currentRoundNumber/6));
    }
}
