package seng201.team8.models;

/**
 * The model for the Cart objects.
 * <p></p>
 * Used as a part of Round where a list of carts is added and stored in the Round class object.
 * <p></p>
 * Once a cart travels a certain amount of distance without being filled up completely, the
 * round is considered lost.
 * @see Round
 */

public class Cart {
    /**
     * An Integer value that determines the cart's capacity.
     * <p></p>
     * The cart is considered filled once its amount reaches or exceeds the targetAmount.
     */
    private int targetAmount;
    /**
     * A Resource value that determines which type of resource the cart will accept.
     * Only towers of the same resource type fill up the cart of said resource type.
     */
    private Resource resourceType;
    /**
     * An Integer value representing the cart's speed. Determines how much distance the cart
     * traverses each turn.
     */
    private int speed;
    /**
     * An Integer value representing the cart's current resource amount. Once it reaches/exceeds
     * targetAmount, the cart is considered filled
     */
    private int amount;
    /**
     * An Integer value representing the cart's current distance from the starting point.
     * The starting distance of a cart is always 0 at the first turn. Once the distance is equal or larger
     * than the Round's distanceAllowed and the cart is not filled, the round is considered lost.
     */
    private int distance;

    /**
     * The constructor for a Cart object.
     * <p></p>
     * Takes in the number of resources needed to fill up the cart, the resource type that
     * the cart accepts and the speed value of the cart
     * @param targetAmount the Integer number of resources needed to fill up the cart.
     * @param resourceType the Resource Enum resource type that the cart accepts
     * @param speed the Integer value of how much the cart's distance increases per turn
     */
    public Cart(int targetAmount, Resource resourceType, int speed){
        this.targetAmount = targetAmount;
        this.resourceType = resourceType;
        this.speed = speed;
        this.amount = 0;
        this.distance = 0;
    }

    /**
     * Returns the cart's target amount of resources
     * @return Integer
     */
    public int getTargetAmount() {
        return targetAmount;
    }

    /**
     * returns the Resource type of the cart
     * @return Resource Enumerator
     */
    public Resource getResourceType() {
        return resourceType;
    }

    /**
     * returns the speed of the cart
     * @return Integer
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * returns the current distance of the cart
     * @return Integer
     */
    public int getDistance() {
        return distance;
    }

    /**
     * returns the current resource amount of the cart
     * @return Integer
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the resourceAmount of the cart to the specified value
     * @param amount an Integer value
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Sets the current distance of the cart to the specified value
     * @param distance an Integer value
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }
}
