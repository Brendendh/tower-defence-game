package seng201.team8.models;

import javafx.scene.paint.Color;

public enum Rarity {
    COMMON(1, Color.color(0,0,0)),
    RARE(2, Color.color(255,165,0)),
    EPIC(3, Color.color(160,32,240));

    final int rarityStatMultiplier;
    final Color rarityTextColor;
    Rarity(int rarityStatMultiplier, Color rarityTextColor){
        this.rarityStatMultiplier = rarityStatMultiplier;
        this.rarityTextColor = rarityTextColor;
    }
}
