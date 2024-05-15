package seng201.team8.models;

import java.util.ArrayList;

/**
 * Reduces the cooldown of tower's resource production
 * <p></p>
 * One of the possible effects an Upgrade can have. The maximum number of targetable towers
 * scales based on Rarity.
 * @see Upgrade
 */
public class CooldownReduction implements Effect, Cloneable{
    /**
     * An Integer amount to reduce the tower's cooldown by
     */
    private int reductionAmount;

    /**
     * The constructor for the CooldownReduction effect. Takes in a specified reduction amount
     * <p></p>
     * @param reductionAmount An Integer amount to set the reductionAmount to
     * @see #reductionAmount
     */
    public CooldownReduction(int reductionAmount){
        this.reductionAmount = reductionAmount;
    }

    /**
     * Applies the
     * @param towers
     */
    @Override
    public void affects(ArrayList<Tower> towers) {
        for(Tower tower: towers){
            if (tower.getTowerStats().getCooldown() > 1){
                tower.getTowerStats().setCooldown(tower.getTowerStats().getCooldown() - reductionAmount);
            }
        }
    }

    @Override
    public CooldownReduction clone(){
        try {
            return (CooldownReduction) super.clone();
        }
        catch (CloneNotSupportedException e){
            return new CooldownReduction(this.getReductionAmount());
        }
    }

    public int getReductionAmount() {
        return reductionAmount;
    }
    public void setReductionAmount(int reductionAmount){this.reductionAmount = reductionAmount;}

    public String getEffectName(){
        return "Cooldown Reduction";
    }
    public String toString(){
        return "Reduces cooldown of towers by "+reductionAmount;
    }
}
