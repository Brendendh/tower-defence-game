package seng201.team8.services;

import seng201.team8.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 *The service class for GameManager. Created during the start
 * of the game by the FxWrapper.
 * <p></p>
 * Serves as an information hub that keeps tracks and stores every information
 * of the game. Such as the current game state, player information, and the default
 * model {@link Item}s. Frequently accessed by other services in order to obtain the
 * information they require to perform their respective logic operations.
 * <p></p>
 * @see seng201.team8.gui.FXWrapper
 */
public class GameManager {
    /**
     * The {@link InventoryManager} that handles
     * the inventory of the player.
     */
    private InventoryManager inventoryManager;
    /**
     * The {@link GameData} of the current game.
     */
    private GameData gameData;
    /**
     * The {@link Round} that stores the information
     * of the current Round.
     */
    private Round round;
    /**
     * The {@link ShopData} of the shop throughout the
     * duration of the game
     */
    private ShopData shopData;
    /**
     * The {@link RarityData} for the game. Used to calculate
     * item {@link Rarity} probabilities and accessed by other
     * services via the GameManager.
     * @see Item
     */
    private RarityData rarityData = new RarityData();
    /**
     *
     */
    private GameGUIManager gameGUIManager;
    /**
     * An ArrayList that represents the proportions of the current
     * round's {@link Cart} {@link Resource} types.
     * <p></p>
     * Used by the GameMenuController to display a simplified representation
     * of the upcoming round's cart resource types to the player.
     * <p></p>
     * @see seng201.team8.gui.GameMenuController
     */
    private ArrayList<Resource> roundResourceDisplay;
    private boolean gameWon = false;
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
    public ShopData getShopData(){return shopData;}

    public void setShopData(ShopData shopData){this.shopData = shopData;}
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
        this.gameData = gameData;
    }

    public void setInventoryManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public boolean getGameWon(){
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }
    public void setRoundResourceDisplay(ArrayList<Resource> resourceDisplay){
        this.roundResourceDisplay = resourceDisplay;
    }
    public ArrayList<Resource> getRoundResourceDisplay(){return roundResourceDisplay;}
}
