package seng201.team8.models;

public class Cart {
    private Integer targetAmount;
    private String resourceType;
    private Integer speed;
    private Integer amount;
    private Integer distance;
    public Cart(Integer targetAmount, String resourceType, Integer speed){
        this.targetAmount = targetAmount;
        this.resourceType = resourceType;
        this.speed = speed;
        this.amount = 0;
        this.distance = 0;
    }
    public void increaseAmount(Integer amountToAdd){
        amount += amountToAdd;
    }

    public void increaseDistance(Integer distanceToAdd){
        distance += distanceToAdd;
    }

    public Integer getTargetAmount() {
        return targetAmount;
    }

    public String getResourceType() {
        return resourceType;
    }

    public Integer getSpeed() {
        return speed;
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getAmount() {
        return amount;
    }
}
