package seng201.team8.models;

public class RarityData {
    private Rarity[] earlyGameRarity;
    private Rarity[] midGameRarity;
    private Rarity[] lateGameRarity;

    public RarityData(){
        this.earlyGameRarity = new Rarity[]{Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.RARE,Rarity.RARE, Rarity.RARE};
        this.midGameRarity = new Rarity[]{Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.RARE,Rarity.RARE,Rarity.RARE,Rarity.EPIC,Rarity.EPIC};
        this.lateGameRarity = new Rarity[]{Rarity.COMMON, Rarity.COMMON,Rarity.COMMON,Rarity.RARE,Rarity.RARE,Rarity.RARE,Rarity.RARE,Rarity.EPIC,Rarity.EPIC,Rarity.EPIC};
    }
    public Rarity[] getEarlyGameRarity() {
        return earlyGameRarity;
    }

    public Rarity[] getMidGameRarity() {
        return midGameRarity;
    }

    public Rarity[] getLateGameRarity() {
        return lateGameRarity;
    }


}
