package seng201.team8.services;

import seng201.team8.models.*;
import seng201.team8.models.dataRecords.GameData;
import seng201.team8.models.dataRecords.RarityData;
import seng201.team8.models.dataRecords.ShopData;
import seng201.team8.models.effects.CooldownReduction;
import seng201.team8.models.effects.ExpBoost;
import seng201.team8.models.effects.RepairTower;
import seng201.team8.models.effects.ResourceAmountBoost;

import java.util.ArrayList;
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
    private final RarityData rarityData = new RarityData();
    /**
     * The {@link GameGUIManager} for the game. Used by controllers
     * to launch different {@link Scene}s.
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
    /**
     * Boolean value that represents if the current round is won or not.
     * Used by round evaluation service and controller to decide which screen to
     * launch to.
     *
     * @see RoundEvaluationService
     * @see seng201.team8.gui.RoundEvaluationController
     */
    private boolean roundWon = false;
    /**
     * An array of {@link Resource} containing the possible resource types.
     *
     *<p></p>
     * Used by RoundSelectorService and RandomEventsService as a pool of possible
     * resource types to choose from.
     *
     * @see RoundSelectorService
     * @see RandomEventsService
     */
    final private Resource[] defaultResources = new Resource[]{Resource.CORN, Resource.WOOD, Resource.IRON};

    /**
     * An Array of the 5 different types of {@link Tower}s available in the game.
     *<p></p>
     * Accessed by the ShopManager and GameSetupService when generating towers for the players.
     * Their rarities are modified when generated.
     *
     * @see ShopManager
     * @see GameSetupService
     */
    final private Tower[] defaultTowers = new Tower[]{
            new Tower("Corn Farm", new TowerStats(10, Resource.CORN, 1 ), 10, Rarity.COMMON),
            new Tower("Corn Field", new TowerStats(35, Resource.CORN, 3), 15, Rarity.COMMON),
            new Tower("Timber Yard", new TowerStats(8, Resource.WOOD, 1), 10, Rarity.COMMON),
            new Tower("Forest", new TowerStats(30, Resource.WOOD, 3), 15, Rarity.COMMON),
            new Tower("Iron Mine", new TowerStats(1, Resource.IRON, 3), 10, Rarity.COMMON)};

    /**
     * An Array of the 4 different types of {@link Upgrade}s available in the game.
     *<p></p>
     * Accessed by the ShopManager when generating new upgrades in the shop for the players.
     * Their rarities are modified when generated.
     *
     * @see ShopManager
     */
    final private Upgrade[] defaultUpgrades = new Upgrade[]{
            new Upgrade(new ExpBoost(10), Rarity.COMMON, 10, 1),
            new Upgrade(new CooldownReduction(1), Rarity.COMMON, 10, 1),
            new Upgrade(new ResourceAmountBoost(10), Rarity.COMMON, 10, 1),
            new Upgrade(new RepairTower(), Rarity.COMMON, 10, 1)};

    /**
     * The constructor for {@link GameManager}.
     * <p></p>
     * Takes in a Consumer object which launches a screen and a Runnable object which clears the screen to set up a new screen.
     * These objects are stored in a newly generated GameGUIManager and launches the first screen of the application.
     *
     * @param screenLauncher Consumer object which runs the launchScreen method in the FXWrapper with the parameter, GameManager.
     * @param clearPane Runnable object which clears the screen to create a new screen.
     */
    public GameManager(Consumer<GameManager> screenLauncher, Runnable clearPane){
        this.gameGUIManager = new GameGUIManager(screenLauncher, clearPane, this);
        gameGUIManager.launchScreen("Game Start");
    }

    /**
     * The constructor for {@link GameManager} for testing purposes.
     * <p></p>
     * Takes in a newly generated GameData and InventoryManager as a parameter and stores
     * their value inside the GameManager.
     * @param gameData {@link GameData}
     * @param inventoryManager {@link InventoryManager}
     */
    public GameManager(GameData gameData, InventoryManager inventoryManager){
        this.gameData = gameData;
        this.inventoryManager = inventoryManager;
    }

    /**
     * Getter for {@link GameManager#shopData}
     * @return {@link ShopData}
     */
    public ShopData getShopData(){return shopData;}

    /**
     * Takes in a {@link ShopData} as a parameter to set the
     * GameManager's {@link GameManager#shopData} to
     * @param shopData {@link ShopData}
     */

    public void setShopData(ShopData shopData){this.shopData = shopData;}

    /**
     * Getter for the current {@link Round} of the game.
     * @return {@link Round}
     */
    public Round getRound(){return round;}

    /**
     * Takes in a parameter {@link Round} to set the current round to.
     * @param round {@link Round}
     */
    public void setRound(Round round){this.round = round;}

    /**
     * Getter for {@link GameManager#defaultTowers}
     * @return {@link GameManager#defaultTowers}
     */
    public Tower[] getDefaultTowers() {
        return defaultTowers;
    }

    /**
     * Getter for {@link GameManager#defaultUpgrades}
     * @return {@link GameManager#defaultUpgrades}
     */
    public Upgrade[] getDefaultUpgrades() {
        return defaultUpgrades;
    }

    /**
     * Getter for {@link GameManager#rarityData}
     * @return {@link GameManager#rarityData}
     */
    public RarityData getRarityData() { return rarityData;}

    /**
     * Getter for {@link GameManager#gameData}
     * @return {@link GameManager#gameData}
     */
    public GameData getGameData() {
        return gameData;
    }

    /**
     * Getter for {@link GameManager#inventoryManager}
     * @return {@link GameManager#inventoryManager}
     */
    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    /**
     * Getter for {@link GameManager#defaultResources}
     * @return {@link GameManager#defaultResources}
     */
    public Resource[] getDefaultResources() { return defaultResources;}

    /**
     * Getter for {@link GameManager#gameGUIManager}
     * @return {@link GameManager#gameGUIManager}
     */
    public GameGUIManager getGameGUIManager() {
        return this.gameGUIManager;
    }

    /**
     * Takes in a parameter {@link GameData} to set the current
     * GameManager's gameData to.
     * <p></p>
     * Called by the GameSetupController to initialize the GameData for GameManager.
     * @param gameData {@link GameData}
     * @see seng201.team8.gui.GameSetupController
     */
    public void setGameData(GameData gameData){
        this.gameData = gameData;
    }

    /**
     * Takes in a parameter {@link InventoryManager} to set the current
     * GameManager's inventoryManager to.
     * <p></p>
     * Called by the GameSetupController to initialize the InventoryManager for GameManager.
     * @param inventoryManager {@link InventoryManager}
     * @see seng201.team8.gui.GameSetupController
     */
    public void setInventoryManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    /**
     * Getter for {@link GameManager#roundWon}.
     * <p></p>
     * Returns true if the round is won and a false if the round is lost.
     * @return {@link GameManager#roundWon}
     */
    public boolean getRoundWon(){
        return roundWon;
    }

    /**
     * Setter to set the {@link GameManager#roundWon} value to the boolean parameter.
     * <p></p>
     * Used by the Round Evaluation Controller to signal that a round is won or lost, allowing
     * other methods to be called.
     * @see seng201.team8.gui.RoundEvaluationController
     * @param roundWon {@link Boolean}
     */
    public void setRoundWon(boolean roundWon) {
        this.roundWon = roundWon;
    }

    /**
     * Setter for {@link GameManager#roundResourceDisplay}, sets it to the inputted ArrayList
     * @param resourceDisplay {@link ArrayList<Resource>}
     */
    public void setRoundResourceDisplay(ArrayList<Resource> resourceDisplay){
        this.roundResourceDisplay = resourceDisplay;
    }

    /**
     * Getter for {@link GameManager#roundResourceDisplay}
     * @return {@link GameManager#roundResourceDisplay}
     */
    public ArrayList<Resource> getRoundResourceDisplay(){return roundResourceDisplay;}
}
