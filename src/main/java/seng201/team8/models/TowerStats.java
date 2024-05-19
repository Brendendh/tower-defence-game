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
     *
     * @param resourceAmount
     * @param resourceType
     * @param cooldown
     */
    public TowerStats(int resourceAmount, Resource resourceType, int cooldown){
        this.resourceAmount = resourceAmount;
        this.resourceType = resourceType;
        this.cooldown = cooldown;
    }

    public int getResourceAmount() {
        return resourceAmount;
    }

    public Resource getResourceType() {
        return resourceType;
    }

    public int getCooldown(){
        return cooldown;
    }

    public void setResourceAmount(int resourceAmount) {
        this.resourceAmount = resourceAmount;
    }

    public void setResourceType(Resource resourceType) {
        this.resourceType = resourceType;
    }

    public void setCooldown(int cooldown) {
        if (cooldown >= 1){
            this.cooldown = cooldown;
        }
    }

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
