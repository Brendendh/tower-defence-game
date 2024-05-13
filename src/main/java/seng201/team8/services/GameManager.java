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
    final private Resource[] defaultResources = new Resource[]{Resource.CORN, Resource.WOOD, Resource.IRON};
    final private Tower[] defaultTowers = new Tower[]{
            new Tower("Corn Farm", new TowerStats(10, Resource.CORN, 1 ), 10, Rarity.COMMON),
            new Tower("Corn Field", new TowerStats(35, Resource.CORN, 3), 15, Rarity.COMMON),
            new Tower("Timber Yard", new TowerStats(8, Resource.WOOD, 1), 10, Rarity.COMMON),
            new Tower("Forest", new TowerStats(30, Resource.WOOD, 3), 15, Rarity.COMMON),
            new Tower("Iron Mine", new TowerStats(1, Resource.IRON, 3), 10, Rarity.COMMON)};

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

    public RarityData getRarityData() { return rarityData;}

    public GameData getGameData() {
        return gameData;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public Resource[] getDefaultResources() { return defaultResources;}

    public GameGUIManager getGameGUIManager() {
        return this.gameGUIManager;
    }

    public void setGameData(GameData gameData){
        this.gameData = new GameData();
    }

    public void setInventoryManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

}
