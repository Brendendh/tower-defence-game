package seng201.team8.services;

import seng201.team8.models.*;

public class GameManager {
    private GameData gameData;
    final Tower[] defaultTowers = new Tower[]{new Tower("", new TowerStats(10,"Coal",5),10, Rarity.COMMON),
            new Tower("", new TowerStats(10,"Corn",5),10, Rarity.COMMON),
            new Tower("", new TowerStats(10,"Stone",5),10, Rarity.COMMON),
            new Tower("", new TowerStats(10,"Steel",5),10, Rarity.COMMON),
            new Tower("", new TowerStats(10,"Diamonds",5),10, Rarity.COMMON)};

    final Upgrade[] defaultUpgrades = new Upgrade[]{new Upgrade(new ExpBoost(10), Rarity.COMMON,10,1),
            new Upgrade(new CooldownReduction(10), Rarity.COMMON,10,1),
            new Upgrade(new ResourceAmountBoost(10), Rarity.COMMON,10,1)};

    public Tower[] getDefaultTowers() {
        return defaultTowers;
    }

    public Upgrade[] getDefaultUpgrades() {
        return defaultUpgrades;
    }
}
