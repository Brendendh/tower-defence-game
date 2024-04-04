package seng201.team8.models;

public class Cart {
    private int targetAmount;
    private String resourceType;
    private int speed;
    private int amount;
    private int distance;
    public Cart(int targetAmount, String resourceType, int speed){
        this.targetAmount = targetAmount;
        this.resourceType = resourceType;
        this.speed = speed;
        this.amount = 0;
        this.distance = 0;
    }
    public void increaseAmount(int amountToAdd){
        amount += amountToAdd;
    }

    public void increaseDistance(int distanceToAdd){
        distance += distanceToAdd;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public String getResourceType() {
        return resourceType;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDistance() {
        return distance;
    }

    public int getAmount() {
        return amount;
    }
}
