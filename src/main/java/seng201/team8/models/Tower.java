package seng201.team8.models;

import seng201.team8.models.dataRecords.InventoryData;

/**
 * The model for the Tower objects.
 * <p>
 * Extends the Item class as it is considered a type of Item
 * <p></p>
 * Used as a part of InventoryData where an array of towers is added and stored for rounds.
 * <p></p>
 * A Tower fills up the non-full carts with its respective resource type
 * every time the cooldown time ends before the carts reach the end of the track.
 *
 * @see InventoryData
 * @see Item
 */

public class Tower extends Item implements Cloneable {

    /**
     * A TowerStats object which contains the towers resource amount per cooldown,
     * resource type and cooldown time.
     */
    private TowerStats towerStats;

    /**
     * An Integer value that represents the tower's level. Used to determine the tower stats
     * respective to the level value.
     */
    private int level;

    /**
     * An Integer value that represents the tower's experience points. Once it reaches a certain
     * threshold, the tower is considered to level up.
     */
    private int experiencePoints;

    /**
     * A Boolean value that represents if the tower is broken or not. If a tower is broken,
     * it can not be used in rounds.
     */
    private boolean isBroken;

    /**
     * A String that represents the towers name.
     */
    private String name;

    /**
     * The constructor for a Tower object.
     * <p></p>
     * Takes in the name of the tower, the {@link TowerStats} (which contains the resource amount, resource type and cooldown),
     * buying price for the shop and rarity for different tiers of a certain tower.
     * <p></p>
     * The initial selling price is set based on the buying price of the tower.
     * The value of the selling price will then increase further when the tower levels up.
     * <p></p>
     * Calls the {@link Item} super constructor to set the Tower's rarity, selling price and buying price based on the values
     * taken in.
     * @param name        the String for the towers name.
     * @param towerStats  the TowerStats object for the tower attributes necessary for rounds.
     * @param buyingPrice the Integer value of how much the tower costs in shops.
     * @param rarity      the Rarity Enum rarity type that the tower is.
     * @see Item
     */
    public Tower(String name, TowerStats towerStats, int buyingPrice, Rarity rarity) {
        //Item constructor is called to set the tower's buying, selling price and rarity.
        super(buyingPrice * rarity.getRarityStatMultiplier(), 5 * rarity.getRarityStatMultiplier(), rarity);
        this.level = 0;
        this.experiencePoints = 0;
        this.name = name;
        this.towerStats = towerStats;
        this.towerStats.setResourceAmount(this.towerStats.getResourceAmount() * rarity.getRarityStatMultiplier());
    }

    /**
     * Returns the TowerStats of the tower.
     *
     * @return TowerStats
     */
    public TowerStats getTowerStats() {
        return towerStats;
    }

    /**
     * Returns the level of the tower.
     *
     * @return Integer
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the experience points of the tower.
     *
     * @return Integer
     */
    public int getExperiencePoints() {
        return experiencePoints;
    }

    /**
     * Returns the name of the tower.
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns True if the tower is broken.
     *
     * @return Boolean
     */
    public boolean isBroken() {
        return isBroken;
    }

    /**
     * Sets the TowerStats of the tower to the given TowerStats object.
     *
     * @param towerStats a TowerStats object
     */
    public void setTowerStats(TowerStats towerStats) {
        this.towerStats = towerStats;
    }

    /**
     * Sets the level of the tower to the given value.
     *
     * @param level an Integer value
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Sets the experience points of the tower to the given value.
     *
     * @param experiencePoints an Integer value
     */
    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    /**
     * Sets the name of the tower to the given String.
     *
     * @param name a String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the broken state of the tower to the given value.
     *
     * @param isBroken a Boolean value
     */
    public void setBroken(boolean isBroken) {
        this.isBroken = isBroken;
    }

    /**
     * Returns a deep copy of the tower.
     * @return {@link Tower}
     * @see Cloneable
     */

    public Tower clone() {
        Tower clonedTower;
        try {
            clonedTower = (Tower) super.clone();
        } catch (CloneNotSupportedException e) {
            clonedTower = new Tower(this.getName(), this.getTowerStats(), this.getBuyingPrice(), this.getRarity());
        }
        clonedTower.towerStats = this.towerStats.clone();
        return clonedTower;
    }

}
