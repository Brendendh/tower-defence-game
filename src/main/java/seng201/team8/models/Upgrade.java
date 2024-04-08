package seng201.team8.models;

public class Upgrade extends Item{
    private Effect effect;

    public Upgrade(Effect effect, int buyingPrice, Rarity rarity){
        super(buyingPrice, 0, rarity);
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }
}
