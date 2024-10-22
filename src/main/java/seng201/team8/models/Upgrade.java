package seng201.team8.models;

import seng201.team8.models.dataRecords.InventoryData;
import seng201.team8.models.effects.*;

/**
 * The model for Upgrade class
 * <br><br>
 *  Extends the {@link Item} class as it is a type of Item the player can have.
 * <br><br>
 * Generated by the ShopManager and sold in the Shop. Stored in the player's InventoryData
 * in an ArrayList when bought.
 * <br><br>
 * An Upgrade has an {@link Effect} which determines its effect on towers. The strength of some
 * effect scales with the Upgrade's {@link Rarity}. An Upgrade also has an Integer {@link Upgrade#maximumTargets}
 * which determines how many towers can be affected by the Upgrade at once.
 * <br><br>
 * @see seng201.team8.services.ShopManager
 * @see InventoryData
 */

public class Upgrade extends Item implements Cloneable{
    /**
     * The {@link Effect} which the Upgrade has that is applied
     * onto the towers when the Upgrade is used.
     */
    private Effect effect;
    /**
     * An Integer which represents the maximum number of towers
     * that can be affected by the Upgrade's effect.
     * <br><br>
     * For some effect types, this can scale with the Upgrade's
     * {@link Rarity}
     */
    private final int maximumTargets;

    /**
     * The constructor for an Upgrade
     * <br><br>
     * Takes in the Upgrade's effect, rarity, buying price and maximum targets as
     * parameters. The Upgrade's selling price is set based on the original buying price
     * of the Upgrade by calling the {@link Item} super constructor.
     *
     * @param effect {@link Upgrade#effect}
     * @param rarity {@link Rarity}
     * @param buyingPrice An {@link Integer} determining the Upgrade's price
     * @param maximumTargets An {@link Integer} that sets the value of {@link Upgrade#maximumTargets}
     */

    public Upgrade(Effect effect, Rarity rarity, int buyingPrice, int maximumTargets){
        //Calls the Item constructor to set the Upgrade's buying price,selling price and rarity.
        super(buyingPrice * rarity.getRarityStatMultiplier(), 5 * rarity.getRarityStatMultiplier(), rarity);
        //Scaling for cooldown reduction and repair towers is increasing the maximum targets instead of increasing stats
        if (effect.getClass() == RepairTower.class || effect.getClass() == CooldownReduction.class){
            this.maximumTargets = maximumTargets * rarity.getRarityStatMultiplier();
        }
        else{
            this.maximumTargets = maximumTargets;
        }
        this.effect = effect;
        //scales the values for ExpBoost and ResourceBoost based on rarity
        updateValues(this.effect, rarity);
    }

    /**
     * Getter for the Upgrade's effect
     * @return {@link Upgrade#effect}
     */
    public Effect getEffect() {
        return effect;
    }

    /**
     * Getter for the maximum number of towers the Upgrade's effect
     * can be applied to.
     * @return {@link Upgrade#maximumTargets}
     */
    public int getMaximumTargets(){return maximumTargets;}

    /**
     * Takes in an Effect and Rarity parameter and
     * updates the boost value of {@link ExpBoost} and {@link ResourceAmountBoost}
     * based on the Upgrade's rarity.
     * <br><br>
     * Called by the Upgrade's constructor.
     * @param effect {@link Effect}
     * @param rarity {@link Rarity}
     */
    private void updateValues(Effect effect, Rarity rarity){
        if (effect.getClass() == ExpBoost.class){
            ((ExpBoost) effect).setBoostAmount(((ExpBoost) effect).getBoostAmount() * rarity.getRarityStatMultiplier());
        }
        if (effect.getClass() == ResourceAmountBoost.class){
            ((ResourceAmountBoost) effect).setBoostAmount(((ResourceAmountBoost) effect).getBoostAmount() * rarity.getRarityStatMultiplier());
        }
    }

    /**
     * Returns the Upgrades effect value.
     * <br><br>
     * Returns the boost amount for {@link ResourceAmountBoost} and {@link ExpBoost}
     * <br><br>
     * Returns the cooldown reduction amount for {@link CooldownReduction}
     * @return {@link Integer}
     */

    public int getBoostAmount(){
        int amount = 0;
        if (effect.getClass() == ExpBoost.class){
            amount = ((ExpBoost) effect).getBoostAmount();
        }
        if (effect.getClass() == CooldownReduction.class){
            amount =((CooldownReduction) effect).getReductionAmount();
        }
        if (effect.getClass() == ResourceAmountBoost.class){
            amount =((ResourceAmountBoost) effect).getBoostAmount();
        }
        return amount;
    }

    /**
     * Returns a deep copy of the Upgrade
     * @return {@link Upgrade}
     * @see Cloneable
     */
    @Override
    public Upgrade clone(){
        Upgrade clonedUpgrade;
        try{
            clonedUpgrade = (Upgrade) super.clone();
        }
        catch (CloneNotSupportedException e){
            clonedUpgrade = new Upgrade(this.getEffect(),this.getRarity(), this.getBuyingPrice(), this.getMaximumTargets());
        }
        if (clonedUpgrade.getEffect().getClass() == ExpBoost.class){
            clonedUpgrade.effect = ((ExpBoost) this.effect).clone();
        }
        if (clonedUpgrade.getEffect().getClass() == CooldownReduction.class){
            clonedUpgrade.effect = ((CooldownReduction) this.effect).clone();
        }
        if (clonedUpgrade.getEffect().getClass() == ResourceAmountBoost.class){
            clonedUpgrade.effect = ((ResourceAmountBoost) this.effect).clone();
        }
        if (clonedUpgrade.getEffect().getClass() == RepairTower.class){
            clonedUpgrade.effect = ((RepairTower) this.effect).clone();
        }
        return clonedUpgrade;
    }

    /**
     * Returns the string description of an Upgrade based on its effect and maximum targets.
     * @return {@link String}
     */
    public String toString(){
        return effect.getEffectName() +" "+ effect.toString() + " up to "+maximumTargets+" Towers";
    }
}
