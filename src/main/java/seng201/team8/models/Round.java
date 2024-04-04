package seng201.team8.models;

public class Round {
    private Integer distanceAllowed;
    private Cart[] carts;
    public Round(Integer numberOfCarts){
        carts = new Cart[numberOfCarts];
    }

    public Integer getCartNumber(){
        return carts.length;
    }

    public Integer getDistanceAllowed() {
        return distanceAllowed;
    }
    public Integer getCartSize(){
        return carts[0].getTargetAmount();
    }

    public String[] getCartResourceType(){
        int numberOfCarts = carts.length;
        String[] cartResourceTypes = new String[numberOfCarts];
        for (int i = 0; i < numberOfCarts; i++){
            cartResourceTypes[i] = carts[i].getResourceType();
        }
        return cartResourceTypes;
    }
}
