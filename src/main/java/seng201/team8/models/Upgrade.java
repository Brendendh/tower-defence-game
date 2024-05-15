package seng201.team8.models;

import javax.print.DocFlavor;

public class Upgrade extends Item implements Cloneable{
    private Effect effect;
    private int maximumTargets;
    private String effectName;

    public Upgrade(Effect effect, Rarity rarity, int buyingPrice, int maximumTargets){
        super(buyingPrice * rarity.getRarityStatMultiplier(), 5 * rarity.getRarityStatMultiplier(), rarity);
        if (effect.getClass() == RepairTower.class || effect.getClass() == CooldownReduction.class){
            this.maximumTargets = maximumTargets * rarity.getRarityStatMultiplier();
        }
        else{
            this.maximumTargets = maximumTargets;
        }
        this.effect = effect;
        updateValues(this.effect, rarity);
    }

    public Effect getEffect() {
        return effect;
    }
    public int getMaximumTargets(){return maximumTargets;}
    private void updateValues(Effect effect, Rarity rarity){
        if (effect.getClass() == ExpBoost.class){
            ((ExpBoost) effect).setBoostAmount(((ExpBoost) effect).getBoostAmount() * rarity.getRarityStatMultiplier());
        }
        if (effect.getClass() == ResourceAmountBoost.class){
            ((ResourceAmountBoost) effect).setBoostAmount(((ResourceAmountBoost) effect).getBoostAmount() * rarity.getRarityStatMultiplier());
        }
    }

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

    public String toString(){
        return effect.getEffectName() +" "+ effect.toString() + " up to "+maximumTargets+" Towers";
    }
}
