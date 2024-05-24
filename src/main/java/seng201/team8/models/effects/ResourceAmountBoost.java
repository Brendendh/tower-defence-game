package seng201.team8.models.effects;

import seng201.team8.models.Rarity;
import seng201.team8.models.Tower;
import seng201.team8.models.Upgrade;

import java.util.List;

/**
 * Increases the resource produced by a {@link Tower} by a flat integer amount
 * <br><br>
 * One of the possible effects an {@link Upgrade} can have. The added resource amount
 * scales based on {@link Rarity}.
 */
public class ResourceAmountBoost implements Effect, Cloneable{
    /**
     * The integer value representing the increase in the
     * tower's resource production amount
     */
    private int boostAmount;

    /**
     * Constructor for the ResourceAmountBoost {@link Effect}
     * @param boostAmount An Integer value to set {@link ResourceAmountBoost#boostAmount} to
     */
    public ResourceAmountBoost(int boostAmount){
        this.boostAmount = boostAmount;
    }
    /**
     * Increase the resource production amount
     * of each {@link Tower} in towers by {@link ResourceAmountBoost#boostAmount}.
     * @param towers a List of Towers to apply the effect to
     */
    @Override
    public void affects(List<Tower> towers) {
        for (Tower tower: towers){
            tower.getTowerStats().setResourceAmount(tower.getTowerStats().getResourceAmount() + boostAmount);
        }
    }

    /**
     * Creates and returns a deep copy of this effect.
     * @return {@link ResourceAmountBoost}
     * @see Cloneable
     */
    @Override
    public ResourceAmountBoost clone(){
        try{
            return (ResourceAmountBoost) super.clone();
        }
        catch (CloneNotSupportedException e){
            return new ResourceAmountBoost(this.getBoostAmount());
        }
    }

    /**
     * Getter for the integer boost amount
     * @return {@link ResourceAmountBoost#boostAmount}
     */
    public int getBoostAmount() {
        return boostAmount;
    }

    /**
     * Sets the boost amount to the inputted Integer boostAmount
     * @param boostAmount An Integer value to set {@link ResourceAmountBoost#boostAmount} to
     */
    public void setBoostAmount(int boostAmount){this.boostAmount = boostAmount;}

    /**
     * Returns the name of the effect
     * @return the string "Resource Amount Boost"
     */
    public String getEffectName(){
        return "Resource Amount Boost";
    }

    /**
     * Returns a string description of the effect including the {@link ResourceAmountBoost#boostAmount}
     * @return a String
     */
    public String toString(){
        return "Increases the resource production amount of towers by " + boostAmount;
    }
}
