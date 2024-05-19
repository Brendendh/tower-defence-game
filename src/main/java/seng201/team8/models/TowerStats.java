package seng201.team8.models;

/**
 * A model class for the TowerStats class.
 * <p></p>
 * In charge of holding the important attributes of a {@link Tower}
 * such as:
 * <p>
 *     The tower's resource production amount, {@link TowerStats#resourceAmount}
 * <p>
 *     The tower's resource type, {@link TowerStats#resourceType}
 * <p>
 *     The tower's production cooldown, {@link TowerStats#cooldown}
 * <p></p>
 * It is created and taken in as a parameter by the constructor of a Tower
 * @see Tower#Tower(String, TowerStats, int, Rarity) 
 */

public class TowerStats implements Cloneable{
    /**
     * An integer representing how much resource is produce per production
     * by the tower
     */
    private int resourceAmount;
    /**
     * A {@link Resource} enum representing the type of resource the tower
     * produces
     */
    private Resource resourceType;
    /**
     * An integer representing the number of turns required for a tower
     * to produce a production of resources.
     */
    private int cooldown;

    /**
     *The constructor for {@link TowerStats}
     * <p></p>
     * Takes in the parameters resourceAmount which is the number of resources
     * produced by the tower per production, resourceType which is the type of
     * resources that the tower produces and cooldown, the number of turns it
     * takes for a tower to produce a production of resource.
     * @param resourceAmount {@link Integer}
     * @param resourceType {@link Resource}
     * @param cooldown {@link Integer}
     */
    public TowerStats(int resourceAmount, Resource resourceType, int cooldown){
        this.resourceAmount = resourceAmount;
        this.resourceType = resourceType;
        this.cooldown = cooldown;
    }

    /**
     * Getter for the number of resources produced by the tower per production
     * @return {@link TowerStats#resourceAmount}
     */
    public int getResourceAmount() {
        return resourceAmount;
    }

    /**
     * Getter for the type of resource produced by the tower
     * @return {@link TowerStats#resourceType}
     */
    public Resource getResourceType() {
        return resourceType;
    }

    /**
     * Getter for the production cooldown of the towers
     * @return {@link TowerStats#cooldown
     */
    public int getCooldown(){
        return cooldown;
    }

    /**
     * Sets the tower's {@link TowerStats#resourceAmount} to the
     * specified Integer resourceAmount
     * @param resourceAmount {@link Integer}
     */
    public void setResourceAmount(int resourceAmount) {
        this.resourceAmount = resourceAmount;
    }

    /**
     * Takes in a Resource parameter to set the tower's
     * {@link TowerStats#resourceType} to
     * @param resourceType {@link Resource}
     */

    public void setResourceType(Resource resourceType) {
        this.resourceType = resourceType;
    }
    /**
     * Sets the tower's {@link TowerStats#cooldown} to the
     * specified Integer cooldown.
     * @param cooldown {@link Integer}
     */
    public void setCooldown(int cooldown) {
        if (cooldown >= 1){
            this.cooldown = cooldown;
        }
    }

    /**
     * Returns a deep copy of the TowerStats
     * @return {@link TowerStats}
     * @see Cloneable
     */
    @Override
    public TowerStats clone(){
        try{
            return (TowerStats) super.clone();
        }
        catch (CloneNotSupportedException e){
            return new TowerStats(this.getResourceAmount(), this.getResourceType(), this.getCooldown());
        }
    }
}
