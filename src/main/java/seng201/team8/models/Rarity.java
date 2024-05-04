package seng201.team8.models;

import javafx.scene.paint.Color;

public enum Rarity {
    COMMON(1, Color.color(0,0,0)),
    RARE(2, Color.color(1,0.6470588235,0)),
    EPIC(3, Color.color(0.6274509804,0.1254901961,0.9411764706));

    private final int rarityStatMultiplier;
    private final Color rarityTextColor;
    Rarity(int rarityStatMultiplier, Color rarityTextColor){
        this.rarityStatMultiplier = rarityStatMultiplier;
        this.rarityTextColor = rarityTextColor;
    }

    public Color getRarityTextColor() {
        return rarityTextColor;
    }

    public int getRarityStatMultiplier() {
        return rarityStatMultiplier;
    }
}
