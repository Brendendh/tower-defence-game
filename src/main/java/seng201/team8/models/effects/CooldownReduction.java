package seng201.team8.models.effects;

import seng201.team8.models.Tower;
import seng201.team8.models.Upgrade;

import java.util.List;

/**
 * Reduces the cooldown of tower's resource production
 * <br><br>
 * One of the possible effects an Upgrade can have. The maximum number of targetable towers
 * scales based on Rarity.
 * @see Upgrade
 */
public class CooldownReduction implements Effect, Cloneable{
    /**
     * An Integer amount to reduce the tower's cooldown by
     */
    private final int reductionAmount;

    /**
     * The constructor for the CooldownReduction effect. Takes in a specified reduction amount
     * <br><br>
     * @param reductionAmount An Integer amount to set the reductionAmount to
     * @see #reductionAmount
     */
    public CooldownReduction(int reductionAmount){
        this.reductionAmount = reductionAmount;
    }

    /**
     * Reduces the cooldown of each tower by the reductionAmount
     * <br><br>
     * Does not reduce the tower's cooldown below 1.
     * @param towers a List of Towers to apply the effect to
     * @see Tower
     */
    @Override
    public void affects(List<Tower> towers) {
        for(Tower tower: towers){
            tower.getTowerStats().setCooldown(Math.max(tower.getTowerStats().getCooldown() - reductionAmount, 1));
        }
    }

    /**
     * Creates and returns a deep copy of this effect.
     * @return {@link CooldownReduction}
     * @see Cloneable
     */
    @Override
    public CooldownReduction clone(){
        try {
            return (CooldownReduction) super.clone();
        }
        catch (CloneNotSupportedException e){
            return new CooldownReduction(this.getReductionAmount());
        }
    }

    /**
     * Returns the reductionAmount of the effect
     * @return the reductionAmount of the effect
     * @see #reductionAmount
     */
    public int getReductionAmount() {
        return reductionAmount;
    }

    /**
     *Returns the effect name
     * @return the string "Cooldown Reduction"
     */
    public String getEffectName(){
        return "Cooldown Reduction";
    }

    /**
     * Returns a description of the effect along with
     * the reduction amount of the effect
     * @return the string representation of the effect
     */
    public String toString(){
        return "Reduces cooldown of towers by "+reductionAmount;
    }
}
