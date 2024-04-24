package seng201.team8.models;

public class Upgrade extends Item implements Cloneable{
    private Effect effect;
    private int maximumTargets;

    public Upgrade(Effect effect, Rarity rarity, int buyingPrice, int maximumTargets){
        super(buyingPrice * rarity.rarityStatMultiplier, 20, rarity);
        this.maximumTargets = maximumTargets;
        this.effect = effect;
        updateValues(this.effect, rarity);
    }

    public Effect getEffect() {
        return effect;
    }
    public int getMaximumTargets(){return maximumTargets;}
    private void updateValues(Effect effect, Rarity rarity){
        if (effect.getClass() == ExpBoost.class){
            ((ExpBoost) effect).setBoostAmount(((ExpBoost) effect).getBoostAmount() * rarity.rarityStatMultiplier);
        }
        if (effect.getClass() == CooldownReduction.class){
            ((CooldownReduction) effect).setReductionAmount(((CooldownReduction) effect).getReductionAmount() * rarity.rarityStatMultiplier);
        }
        if (effect.getClass() == ResourceAmountBoost.class){
            ((ResourceAmountBoost) effect).setBoostAmount(((ResourceAmountBoost) effect).getBoostAmount() * rarity.rarityStatMultiplier);
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
        return clonedUpgrade;
    }

}
