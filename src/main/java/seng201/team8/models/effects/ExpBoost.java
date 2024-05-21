package seng201.team8.models.effects;

import seng201.team8.models.Rarity;
import seng201.team8.models.Tower;
import seng201.team8.models.Upgrade;
import seng201.team8.services.TowerStatsManager;

import java.util.ArrayList;
/**
 * Increases the exp value of a {@link Tower} by a flat integer amount
 * <p></p>
 * One of the possible effects an {@link Upgrade} can have. The added exp value
 * scales based on {@link Rarity}.
 */
public class ExpBoost implements Effect, Cloneable{
    /**
     * An Integer amount of exp added to a tower.
     */
    private int boostAmount;
    /**
     * A service used to handle all things tower levelling related.
     * <p>
     *     Nessecary since adding exp to a tower could result in the tower
     *     levelling up
     * </p>
     * @see TowerStatsManager
     */
    private TowerStatsManager towerStatsManager;

    /**
     * The constructor for ExpBoost.
     * <p></p>
     * Takes in an Integer boostAmount to set the added exp value to.
     * <p></p>
     * Creates a new instance
     * of TowerStatsManager to handle the exp and levelling calculations.
     * @param boostAmount the Integer amount of exp to be added to towers
     * @see TowerStatsManager
     */
    public ExpBoost(int boostAmount){
        this.boostAmount = boostAmount;
        this.towerStatsManager = new TowerStatsManager();
    }

    /**
     * Increase the exp value of each tower by boostAmount.
     * @param towers an ArrayList of Towers to apply the effect to
     * @see Tower
     */
    @Override
    public void affects(ArrayList<Tower> towers) {
        for(Tower tower: towers){
            towerStatsManager.addExp(tower, boostAmount);
        }
    }

    /**
     * Creates and returns a deep copy of this effect.
     * @return {@link ExpBoost}
     * @see Cloneable
     */
    @Override
    public ExpBoost clone(){
        try{
            return (ExpBoost) super.clone();
        }
        catch (CloneNotSupportedException e){
            return new ExpBoost(this.getBoostAmount());
        }
    }

    /**
     * Returns the boostAmount value
     * @return {@link ExpBoost#boostAmount}
     */
    public int getBoostAmount(){
        return boostAmount;
    }

    /**
     * Sets the {@link ExpBoost#boostAmount} value to the specified amount
     * @param boostAmount the specified Integer value to set the boostAmount to
     */
    public void setBoostAmount(int boostAmount){
        this.boostAmount = boostAmount;
    }

    /**
     *Returns the effect name
     * @return the string "ExpBoost"
     */
    public String getEffectName(){
        return "ExpBoost";
    }

    /**
     * Returns a description of the effect along with
     * the reduction amount of the effect
     * @return the string representation of the effect
     */
    public String toString() {
        return "Adds "+ boostAmount + " exp";
    }

}
