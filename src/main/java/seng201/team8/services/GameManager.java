package seng201.team8.services;

import seng201.team8.models.*;

public class GameManager {
    private GameData gameData;
    final private Tower[] defaultTowers = new Tower[]{
            new Tower("", new TowerStats(10,"Coal",5),10, Rarity.COMMON),
            new Tower("", new TowerStats(10,"Corn",5),10, Rarity.COMMON),
            new Tower("", new TowerStats(10,"Stone",5),10, Rarity.COMMON),
            new Tower("", new TowerStats(10,"Steel",5),10, Rarity.COMMON),
            new Tower("", new TowerStats(10,"Diamonds",5),10, Rarity.COMMON)};

    final private Upgrade[] defaultUpgrades = new Upgrade[]{
            new Upgrade(new ExpBoost(10), Rarity.COMMON,10,1),
            new Upgrade(new CooldownReduction(10), Rarity.COMMON,10,1),
            new Upgrade(new ResourceAmountBoost(10), Rarity.COMMON,10,1)};

    final private Rarity[] earlyGameRarity = new Rarity[]{Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.RARE,Rarity.RARE, Rarity.RARE};
    final private Rarity[] midGameRarity = new Rarity[]{Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.RARE,Rarity.RARE,Rarity.RARE,Rarity.EPIC,Rarity.EPIC};
    final private Rarity[] lateGameRarity = new Rarity[]{Rarity.COMMON, Rarity.COMMON,Rarity.COMMON,Rarity.RARE,Rarity.RARE,Rarity.RARE,Rarity.RARE,Rarity.EPIC,Rarity.EPIC,Rarity.EPIC};
    public Tower[] getDefaultTowers() {
        return defaultTowers;
    }

    public Upgrade[] getDefaultUpgrades() {
        return defaultUpgrades;
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

    public GameData getGameData() {
        return gameData;
    }
}
