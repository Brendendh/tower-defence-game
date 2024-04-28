package seng201.team8.services;

import seng201.team8.models.*;

import java.util.HashMap;
import java.util.function.Consumer;

public class GameManager {
    private InventoryManager inventoryManager;
    private GameData gameData;
    private Round round;
    private RarityData rarityData = new RarityData();
    private GameGUIManager gameGUIManager;
    final private String[] defaultResources = new String[]{"Coal", "Corn", "Stone", "Steel", "Diamonds"};
    final private Tower[] defaultTowers = new Tower[]{
            new Tower("", new TowerStats(10, "Coal", 5), 10, Rarity.COMMON),
            new Tower("", new TowerStats(10, "Corn", 5), 10, Rarity.COMMON),
            new Tower("", new TowerStats(10, "Stone", 5), 10, Rarity.COMMON),
            new Tower("", new TowerStats(10, "Steel", 5), 10, Rarity.COMMON),
            new Tower("", new TowerStats(10, "Diamonds", 5), 10, Rarity.COMMON)};

    final private Upgrade[] defaultUpgrades = new Upgrade[]{
            new Upgrade(new ExpBoost(10), Rarity.COMMON, 10, 1),
            new Upgrade(new CooldownReduction(10), Rarity.COMMON, 10, 1),
            new Upgrade(new ResourceAmountBoost(10), Rarity.COMMON, 10, 1),
            new Upgrade(new RepairTower(), Rarity.COMMON, 10, 1)};

    public GameManager(Consumer<GameManager> screenLauncher, Runnable clearPane){
        this.gameGUIManager = new GameGUIManager(screenLauncher, clearPane, this);
        gameGUIManager.launchScreen("Game Start");
    }

    public GameManager(GameData gameData, InventoryManager inventoryManager){
        this.gameData = gameData;
        this.inventoryManager = inventoryManager;
    }

    public Round getRound(){return round;}

    public void setRound(Round round){this.round = round;}
    public Tower[] getDefaultTowers() {
        return defaultTowers;
    }

    public Upgrade[] getDefaultUpgrades() {
        return defaultUpgrades;
    }

    public RarityData getDifferentRarities() { return rarityData;}

    public GameData getGameData() {
        return gameData;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public String[] getDefaultResources() { return defaultResources;}

    public GameGUIManager getGameGUIManager() {
        return this.gameGUIManager;
    }

}
