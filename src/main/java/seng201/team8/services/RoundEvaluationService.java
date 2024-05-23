package seng201.team8.services;

import seng201.team8.models.Cart;
import seng201.team8.models.Resource;
import seng201.team8.models.Round;
import seng201.team8.models.Tower;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;

/**
 * The service class for RoundEvaluationService. Created when the
 * RoundEvaluationController is created.
 * <p></p>
 * Used to simulate the game logic of creating resources from towers,
 * filling the carts with the resources and advancing carts. There are
 * also methods to check if the round finished.
 */
public class RoundEvaluationService {

    /**
     * The {@link Round} for accessing the
     * round details.
     */
    private final Round roundData;
    /**
     * The array of {@link Tower}s which represents the main towers.
     */
    private final Tower[] mainTowers;
    /**
     * The {@link EnumMap} which stores {@link Resource} as keys and
     * {@link Integer} the amount of resources produced as values.
     */
    private final EnumMap<Resource, Integer> resourcesProduced;
    /**
     * The array of {@link Boolean} objects which stores which
     * towers has produced this round by its index in the main towers.
     */
    private boolean[] towerProduced;
    /**
     * The {@link Integer} value which represents the current turn in the round.
     */
    private int counter = 0;
    /**
     * The array of {@link Cart}s which represents the carts in the round.
     */
    private final Cart[] carts;

    /**
     * The constructor for {@link RoundEvaluationService}.
     * <p></p>
     * Takes in a {@link GameManager} object and stores it in the RoundEvaluationService. Sorts the carts in speed
     * descending speed order to fill the fastest first.
     * @param gameManager {@link GameManager}
     */
    public RoundEvaluationService(GameManager gameManager){
        this.roundData = gameManager.getRound();
        this.resourcesProduced = createResourcesProduced(gameManager.getDefaultResources());
        this.carts = roundData.getCarts();
        this.mainTowers = gameManager.getInventoryManager().getInventoryData().getMainTowers();
        this.towerProduced = new boolean[mainTowers.length];
        Arrays.sort(carts, Comparator.comparing(Cart::getSpeed).reversed());
    }

    /**
     * Creates a {@link EnumMap} of resources produced this turn.
     * @param resources an array of {@link Resource}s
     * @return {@link EnumMap}
     */
    private EnumMap<Resource, Integer> createResourcesProduced(Resource[] resources){
        EnumMap<Resource, Integer> tempResourcesProduced = new EnumMap<>(Resource.class);
        for(Resource resource:resources){
            tempResourcesProduced.put(resource, 0);
        }
        return tempResourcesProduced;
    }

    /**
     * Produces an {@link Integer} amount of {@link Resource}s based on the towers conditions and stats.
     * Sets the towerProduced array elements to true if a tower did produce this turn.
     */
    public void produceResources(){
        towerProduced = new boolean[mainTowers.length];
        for(int i = 0; i < mainTowers.length; i++){
            if(mainTowers[i] != null) {
                if ((counter % mainTowers[i].getTowerStats().getCooldown()) == 0 && !mainTowers[i].isBroken()) {
                    resourcesProduced.put(mainTowers[i].getTowerStats().getResourceType(),
                            resourcesProduced.get(mainTowers[i].getTowerStats().getResourceType()) + mainTowers[i].getTowerStats().getResourceAmount());
                    towerProduced[i] = true;
                }
            }
        }
    }

    /**
     * Fills all the carts up with the resources produced.
     * Goes through each {@link Cart} and checks if the carts are not full before filling them.
     */
    public void fillCarts(){
        for(Cart cart:carts){
            if(isCartNotFull(cart)){
                fillCart(cart);
            }
        }
    }

    /**
     * Fills the cart to the max or with what's left in the resources produced.
     * Checks if the cart gets overfilled to prevent wasting resources.
     * @param cart {@link Cart}
     */
    public void fillCart(Cart cart){
        int cartAmountDifference = cart.getTargetAmount() - cart.getAmount();
        if(cartAmountDifference > resourcesProduced.get(cart.getResourceType())){
            cart.setAmount(cart.getAmount() + resourcesProduced.get(cart.getResourceType()));
            resourcesProduced.put(cart.getResourceType(), 0);
        }
        else{
            cart.setAmount(cart.getTargetAmount());
            resourcesProduced.put(cart.getResourceType(), resourcesProduced.get(cart.getResourceType()) - cartAmountDifference);
        }
    }

    /**
     * Advances the {@link Cart}s by their individual cart speed.
     * Does not advance if the carts are full.
     */
    public void advanceCarts(){
        for(Cart cart:carts){
            if(isCartNotFull(cart)){
                cart.setDistance(cart.getDistance() + cart.getSpeed());
            }
        }
    }

    /**
     * Returns true if the cart is not full.
     * @param cart {@link Cart}
     * @return {@link Boolean}
     */
    private boolean isCartNotFull(Cart cart){
        return cart.getAmount() < cart.getTargetAmount();
    }

    /**
     * Returns true if the cart reached the end.
     * @return {@link Boolean}
     */
    public boolean didCartReach(){
        for(Cart cart:carts){
            if(cart.getDistance() >= roundData.getDistanceAllowed()){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if all the carts reached the end.
     * @return {@link Boolean}
     */
    public boolean areAllCartsFull(){
        for(Cart cart:carts){
            if(isCartNotFull(cart)){
                return false;
            }
        }
        return true;
    }

    /**
     * Getter for {@link RoundEvaluationService#carts}.
     * @return an array of {@link Cart}s
     */
    public Cart[] getCarts() {
        return carts;
    }

    /**
     * Getter for {@link RoundEvaluationService#resourcesProduced}
     * <p></p>
     * For testing purposes.
     * @return an {@link EnumMap} of {@link Resource}s as keys and the {@link Integer} amount produced as values.
     */
    public EnumMap<Resource, Integer> getResourcesProduced() {
        return resourcesProduced;
    }

    /**
     * Increments the turn counter by one.
     */
    public void incrementCounter() {
        counter += 1;
    }

    /**
     * Getter for {@link RoundEvaluationService#towerProduced}.
     * @return an Array of {@link boolean} objects
     */
    public boolean[] getTowerProduced() {
        return towerProduced;
    }

    /**
     * Getter for {@link RoundEvaluationService#counter}
     * @return {@link Integer}
     */
    public int getCounter() {
        return counter;
    }
}
