package seng201.team8.models;

public enum Rarity {
    COMMON(1), RARE(2), EPIC(3);

    final int rarityStatMultiplier;
    Rarity(int rarityStatMultiplier){
        this.rarityStatMultiplier = rarityStatMultiplier;
    }
}
