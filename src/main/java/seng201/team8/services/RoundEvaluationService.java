package seng201.team8.services;

import seng201.team8.models.Cart;
import seng201.team8.models.Resource;
import seng201.team8.models.Round;
import seng201.team8.models.Tower;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;

public class RoundEvaluationService {

    private final Round roundData;
    private final Tower[] mainTowers;
    private final EnumMap<Resource, Integer> resourcesProduced;
    private boolean[] towerProduced = new boolean[5];
    private int counter = 0;
    private final Cart[] carts;

    public RoundEvaluationService(GameManager gameManager){
        this.roundData = gameManager.getRound();
        this.resourcesProduced = createResourcesProduced(gameManager.getDefaultResources());
        this.carts = roundData.getCarts();
        this.mainTowers = gameManager.getInventoryManager().getInventoryData().getMainTowers();
        Arrays.sort(carts, Comparator.comparing(Cart::getSpeed).reversed());
    }

    private EnumMap<Resource, Integer> createResourcesProduced(Resource[] resources){
        EnumMap<Resource, Integer> tempResourcesProduced = new EnumMap<>(Resource.class);
        for(Resource resource:resources){
            tempResourcesProduced.put(resource, 0);
        }
        return tempResourcesProduced;
    }

    public void produceResources(){
        towerProduced = new boolean[5];
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

    public void fillCarts(){
        for(Cart cart:carts){
            if(isCartNotFull(cart)){
                fillCart(cart);
            }
        }
    }

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

    public void advanceCarts(){
        for(Cart cart:carts){
            if(isCartNotFull(cart)){
                cart.setDistance(cart.getDistance() + cart.getSpeed());
            }
        }
    }

    private boolean isCartNotFull(Cart cart){
        return cart.getAmount() < cart.getTargetAmount();
    }

    public boolean didCartReach(){
        for(Cart cart:carts){
            if(cart.getDistance() >= roundData.getDistanceAllowed()){
                return true;
            }
        }
        return false;
    }

    public boolean areAllCartsFull(){
        for(Cart cart:carts){
            if(isCartNotFull(cart)){
                return false;
            }
        }
        return true;
    }

    public Cart[] getCarts() {
        return carts;
    }

    public EnumMap<Resource, Integer> getResourcesProduced() {
        return resourcesProduced;
    }

    public void incrementCounter() {
        counter += 1;
    }

    public boolean[] getTowerProduced() {
        return towerProduced;
    }
}
