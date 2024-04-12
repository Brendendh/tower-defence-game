package seng201.team8.models;

public class Upgrade extends Item{
    private Effect effect;
    private int maximumTargets;

    public Upgrade(Effect effect, Rarity rarity, int buyingPrice, int maximumTargets){
        super(buyingPrice, 0, rarity);
        this.maximumTargets = maximumTargets;
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }
}
