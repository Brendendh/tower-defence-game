package seng201.team8.models;

public class Upgrade extends Item{
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

}
