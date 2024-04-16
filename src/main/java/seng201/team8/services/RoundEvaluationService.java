package seng201.team8.services;

import seng201.team8.models.Cart;
import seng201.team8.models.Round;
import seng201.team8.models.Tower;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class RoundEvaluationService {

    private Round roundData;
    private GameManager gameManager;
    private Tower[] mainTowers;
    private HashMap<String, Integer> resourcesProduced;
    private int counter = 0;
    private Cart[] carts;

    public RoundEvaluationService(GameManager gameManager){
        this.gameManager = gameManager;
        this.roundData = gameManager.getRound();
        this.resourcesProduced = createResourcesProduced(gameManager.getDefaultResources());
        this.carts = roundData.getCarts();
        Arrays.sort(carts, Comparator.comparing(Cart::getSpeed).reversed());
    }

    private HashMap<String, Integer> createResourcesProduced(String[] resources){
        HashMap<String, Integer> tempResourcesProduced = new HashMap<String, Integer>();
        for(String resource:resources){
            tempResourcesProduced.put(resource, 0);
        }
        return tempResourcesProduced;
    }

    public boolean evaluateRound(){
        while(didCartReach()) {
            counter += 1;
            produceResources();
            fillCarts();
            if (areAllCartsFull()) {
                return true;
            }
            advanceCarts();
        }
        return false;
    }

    private void produceResources(){
        for(Tower maintower:mainTowers){
            if(counter % maintower.getTowerStats().getCooldown() == 0){
                resourcesProduced.put(maintower.getTowerStats().getResourceType(), maintower.getTowerStats().getResourceAmount());
            }
        }
    }

    private void fillCarts(){
        for(Cart cart:carts){
            if(!isCartFull(cart)){
                fillCart(cart);
            }
        }
    }

    private void fillCart(Cart cart){
        int cartAmountDifference = cart.getTargetAmount() - cart.getAmount();
        if(cartAmountDifference > resourcesProduced.get(cart.getResourceType())){
            cart.setAmount(cart.getAmount() - resourcesProduced.get(cart.getResourceType()));
            resourcesProduced.put(cart.getResourceType(), 0);
        }
        else{
            cart.setAmount(0);
            resourcesProduced.put(cart.getResourceType(), resourcesProduced.get(cart.getResourceType()) - cartAmountDifference);
        }
    }

    private void advanceCarts(){
        for(Cart cart:carts){
            if(!isCartFull(cart)){
                cart.setDistance(cart.getDistance() + cart.getSpeed());
            }
        }
    }

    private boolean isCartFull(Cart cart){
        return cart.getAmount() >= cart.getTargetAmount();
    }

    private boolean didCartReach(){
        for(Cart cart:carts){
            if(cart.getDistance() > roundData.getDistanceAllowed()){
                return true;
            }
        }
        return false;
    }

    private boolean areAllCartsFull(){
        for(Cart cart:carts){
            if(!isCartFull(cart)){
                return false;
            }
        }
        return true;
    }


}
