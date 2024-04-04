package seng201.team8.models;

public class Round {
    private int distanceAllowed;
    private Cart[] carts;
    public Round(int numberOfCarts){
        carts = new Cart[numberOfCarts];
    }

    public int getCartNumber(){
        return carts.length;
    }

    public int getDistanceAllowed() {
        return distanceAllowed;
    }
    public int getCartSize(){
        return carts[0].getTargetAmount();
    }

    public String[] getCartResourceType(){
        int numberOfCarts = carts.length;
        String[] cartResourceTypes = new String[numberOfCarts];
        for (int i = 0; i <= numberOfCarts; i++){
            cartResourceTypes[i] = carts[i].getResourceType();
        }
        return cartResourceTypes;
    }
}
