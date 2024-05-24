package seng201.team8.services;

import seng201.team8.exceptions.BuyingNullError;
import seng201.team8.exceptions.NoSpaceException;
import seng201.team8.exceptions.NotEnoughCurrencyException;
import seng201.team8.exceptions.SellingNullError;
import seng201.team8.models.*;
import seng201.team8.models.dataRecords.ShopData;

import java.util.Random;

/**
 * The service class for the Shop.
 * <br><br>
 * Handles the logic behind generating shop {@link Item} and the buying and selling
 * of player owned {@link Tower}s and {@link Upgrade}.
 * <br><br>
 * Is initialized by the ShopScreenController upon creation to manage the different logic operations
 * that involves the game's {@link ShopData} and player's inventory.
 * @see seng201.team8.gui.ShopScreenController
 * @see InventoryManager
 */

public class ShopManager {
    /**
     * The game's current {@link ShopData}. Set by the constructor based
     * of the {@link GameManager} value.
     *
     * @see GameManager#getShopData()
     */
    private final ShopData shopData;
    /**
     * The player's {@link InventoryManager}. Used by the manager to access and
     * change the player's inventory when interacting with the shop.
     */
    private final InventoryManager inventoryManager;
    /**
     * The current game's {@link GameManager}
     */
    private final GameManager gameManager;
    /**
     * Used to generate random numbers to generate random {@link Item}s.
     * @see Random
     */
    private final Random randomGenerator;
    /**
     * The default pool of {@link Tower}s
     * @see GameManager#getDefaultTowers()
     */
    private final Tower[] defaultTowersToPick;
    /**
     * The default pool of {@link Upgrade}s
     * @see GameManager#getDefaultUpgrades()
     */
    private final Upgrade[] defaultUpgrades;

    /**
     * The constructor for ShopManager.
     * <br><br>
     * Takes in the game's GameManager as a parameter in order for the ShopManager to be able to
     * manage the {@link ShopData} and communicate with the InventoryManager via the GameManager.
     * <br><br>
     * At the start of the game, the ShopData in the GameManager will not be initialized yet and if so, the ShopManager
     * will initialize the ShopData. From there onwards, everytime the player enters the shop again, a new ShopManager is
     * initialized where it compares the current round number to the ShopData's initializedRoundNumber to see if a round has passed
     * or not. If a round has passed, refresh the shop and update the ShopData's initializedRoundNumber. If not, do nothing.
     * <br><br>
     * This is to ensure the shop state is saved when the player exits and re-enters the shop within the same round, while
     * the shop refreshes itself after every round.
     * @see ShopData#getInitializedRoundNumber()
     * @see InventoryManager
     * @param gameManager {@link GameManager}
     */
    public ShopManager(GameManager gameManager){
        this.gameManager = gameManager;
        randomGenerator = new Random();
        defaultTowersToPick = gameManager.getDefaultTowers();
        defaultUpgrades = gameManager.getDefaultUpgrades();
        this.inventoryManager = this.gameManager.getInventoryManager();
        if (gameManager.getShopData() != null){
            this.shopData = gameManager.getShopData();
        }
        else{
            this.shopData = new ShopData();
            updateTowers();
            updateUpgrades();
        }
        if (this.shopData.getInitializedRoundNumber() != gameManager.getGameData().getRound()){
            updateTowers();
            updateUpgrades();
            this.shopData.setInitializedRoundNumber(gameManager.getGameData().getRound());
        }
        gameManager.setShopData(this.shopData);
    }

    /**
     * Refreshes the {@link Tower}s and {@link Upgrade}s sold by the shop at the cost
     * of 5 money.
     * <br><br>
     * Does so by calling {@link ShopManager#updateTowers()} to refresh Towers sold
     * and {@link ShopManager#updateUpgrades()} to refresh Upgrades sold.
     * <br><br>
     * If the player does not have enough money to refresh, it will throw a {@link NotEnoughCurrencyException}
     * to be handled by the ShopScreenController.
     * @throws NotEnoughCurrencyException occurs when there is not enough money.
     * @see seng201.team8.gui.ShopScreenController
     */
    public void refresh() throws NotEnoughCurrencyException {
        if (gameManager.getGameData().getMoney() < 5){
            throw new NotEnoughCurrencyException("Not enough money to refresh!");
        }
        else{
            updateTowers();
            updateUpgrades();
            gameManager.getGameData().setMoney(gameManager.getGameData().getMoney() - 5);
        }
    }

    /**
     * Updates the {@link Tower} sold by the shop. The possible Towers are randomly
     * selected from the default Tower pool from GameManager. The Tower's rarity is then decided
     * by calling {@link ShopManager#generateRarities(int currentRoundNumber)}
     *
     * @see GameManager#getDefaultTowers()
     */
    public void updateTowers(){
        Tower[] towersToBeSold = new Tower[3];
        for (int i = 0; i < 3; i++){
            int randomTowerIndex = randomGenerator.nextInt(defaultTowersToPick.length);
            Tower tower = defaultTowersToPick[randomTowerIndex].clone();
            towersToBeSold[i] = new Tower(tower.getName(), tower.getTowerStats(), tower.getBuyingPrice(),generateRarities(gameManager.getGameData().getRound()));
        }
        shopData.setTowersSold(towersToBeSold);
    }
    /**
     * Updates the {@link Upgrade} sold by the shop. The possible Upgrades are randomly
     * selected from the default Upgrade pool from GameManager. The Upgrade's rarity is then decided
     * by calling {@link ShopManager#generateRarities(int currentRoundNumber)}
     *
     * @see GameManager#getDefaultUpgrades()
     */
    public void updateUpgrades(){
        Upgrade[] upgradesToBeSold = new Upgrade[3];
        for (int i = 0; i < 3; i++){
            int randomUpgradeIndex = randomGenerator.nextInt(defaultUpgrades.length);
            Upgrade upgrade = defaultUpgrades[randomUpgradeIndex].clone();
            upgradesToBeSold[i] = new Upgrade(upgrade.getEffect(),generateRarities(gameManager.getGameData().getRound()),upgrade.getBuyingPrice(),upgrade.getMaximumTargets());
        }
        shopData.setUpgradesSold(upgradesToBeSold);

    }

    /**
     * Return a random {@link Rarity} based on the current round number.
     * <br><br>
     * Does so by randomly choosing from different rarity probability pools based on the
     * current round number.
     * @param currentRound the current round number
     * @return {@link Rarity}
     * @see seng201.team8.models.dataRecords.RarityData
     */
    public Rarity generateRarities(int currentRound){
        int randomValue = randomGenerator.nextInt(10);
        Rarity generatedRarity = Rarity.COMMON;
        if (currentRound <= 5){
            generatedRarity = gameManager.getRarityData().getEarlyGameRarity()[randomValue];
        }
        if (currentRound <= 10 && currentRound > 5){
            generatedRarity = gameManager.getRarityData().getMidGameRarity()[randomValue];
        }
        if (currentRound <= 15 && currentRound > 10){
            generatedRarity = gameManager.getRarityData().getLateGameRarity()[randomValue];
        }
        return generatedRarity;
    }

    /**
     * Returns the {@link Tower}s sold by the shop.
     * @return An Array of the Towers sold
     */
    public Tower[] getTowersSold(){
        return shopData.getTowersSold();
    }
    /**
     * Returns the {@link Upgrade}s sold by the shop.
     * @return An Array of the Upgrades sold
     */
    public Upgrade[] getUpgradesSold(){
        return shopData.getUpgradesSold();
    }

    /**
     * Buys the selected {@link Tower}. Called by the ShopScreenController
     * <br><br>
     * The Tower bought will be moved to the first empty slot in the player's main towers, if
     * there is no space in the main towers, it will be moved to the first empty slot in the player's
     * reserve towers. Else, a {@link NoSpaceException} will be thrown and the player's money won't be
     * deducted.
     * <br><br>
     * If the player tries to buy an already sold Tower, a {@link BuyingNullError} will be thrown and
     * the player's money won't be deducted.
     * <br><br>
     * If the player does not have enough money to purchase the selected Tower, a {@link NotEnoughCurrencyException}
     * will be thrown.
     * <br><br>
     * All exceptions will be handled by the ShopScreenController.
     * @param towerIndex the selected Tower's index.
     * @throws NoSpaceException occurs when there is no space in the main towers and reserve towers.
     * @throws NotEnoughCurrencyException occurs when there is not enough money to buy a tower.
     * @throws BuyingNullError occurs when there is no tower selected.
     * @see seng201.team8.gui.ShopScreenController
     */
    public void buyTower(int towerIndex) throws NoSpaceException, NotEnoughCurrencyException, BuyingNullError {
        Tower towerBought = shopData.getTowersSold()[towerIndex];
        if (towerBought == null){ //check if the tower is not already sold
            throw new BuyingNullError("Why are you trying to buy air?");
        }
        if (gameManager.getGameData().getMoney() < towerBought.getBuyingPrice()){ //check if the player has the money required
            throw new NotEnoughCurrencyException("Not enough money to purchase tower!");
        } else{
            try{ //try to move tower to main towers
                inventoryManager.moveToMain(towerBought);
            }
            catch (NoSpaceException noSpaceInMain){ //try to move tower to reserve if no space in main
                inventoryManager.moveToReserve(towerBought); //will throw error, handled by method in ShopScreenController
            }
            gameManager.getGameData().setMoney(gameManager.getGameData().getMoney() - towerBought.getBuyingPrice());
            shopData.getTowersSold()[towerIndex] = null;
        }
    }
    /**
     * Buys the selected {@link Upgrade}. Called by the ShopScreenController.
     * <br><br>
     * The bought Upgrade will be added to the player's inventory.
     * <br><br>
     * If the player tries to buy an already sold Upgrade, a {@link BuyingNullError} will be thrown and
     * the player's points won't be deducted.
     * <br><br>
     * If the player does not have enough points to purchase the selected Upgrade, a {@link NotEnoughCurrencyException}
     * will be thrown.
     * <br><br>
     * All exceptions will be handled by the ShopScreenController.
     * @param upgradeIndex the selected Upgrade's index
     * @throws NotEnoughCurrencyException occurs when there is not enough points.
     * @throws BuyingNullError occurs when there is no upgrade selected.
     * @see seng201.team8.gui.ShopScreenController
     */
    public void buyUpgrade(int upgradeIndex) throws NotEnoughCurrencyException, BuyingNullError {
        Upgrade upgradeBought = shopData.getUpgradesSold()[upgradeIndex];
        if (upgradeBought == null){
            throw new BuyingNullError("Why are you trying to buy air?");
        }
        if (gameManager.getGameData().getPoint() < upgradeBought.getBuyingPrice()){
            throw new NotEnoughCurrencyException("Not enough points to purchase upgrade!");
        }
        else{
            inventoryManager.addUpgrade(upgradeBought);
            gameManager.getGameData().setPoint(gameManager.getGameData().getPoint() - upgradeBought.getBuyingPrice());
            shopData.getUpgradesSold()[upgradeIndex] = null;
        }
    }

    /**
     * Sells the selected Main {@link Tower}. Called by the ShopScreenController.
     * <br><br>
     * The sold Tower is removed from player's inventory via the {@link InventoryManager}
     * and the Tower's selling price money is added to the player's money count.
     * <br><br>
     * If the selected towerIndex points to an empty slot instead of a Tower, a {@link SellingNullError}
     * is thrown to be handled by the ShopScreenController.
     * @param towerIndex the selected main tower {@link Integer} index.
     * @throws SellingNullError occurs when there is no tower selected.
     */
    public void sellMainTower(int towerIndex) throws SellingNullError {
        if (inventoryManager.getInventoryData().getMainTowers()[towerIndex] == null){
            throw new SellingNullError("Why are you trying to sell air...?");
        }
        else{
            Tower towerSold = inventoryManager.getInventoryData().getMainTowers()[towerIndex];
            gameManager.getGameData().setMoney(gameManager.getGameData().getMoney() + towerSold.getSellingPrice());
            inventoryManager.getInventoryData().getMainTowers()[towerIndex] = null;
        }
    }
    /**
     * Sells the selected Reserve {@link Tower}. Called by the ShopScreenController.
     * <br><br>
     * The sold Tower is removed from player's inventory via the {@link InventoryManager}
     * and the Tower's selling price money is added to the player's money count.
     * <br><br>
     * If the selected towerIndex points to an empty slot instead of a Tower, a {@link SellingNullError}
     * is thrown to be handled by the ShopScreenController.
     * @param towerIndex the selected reserve tower {@link Integer} index.
     * @throws SellingNullError occurs when there is no tower selected.
     */
    public void sellReserveTower(int towerIndex) throws SellingNullError {
        if (inventoryManager.getInventoryData().getReserveTowers()[towerIndex] == null){
            throw new SellingNullError("Why are you trying to sell air...?");
        }
        else{
            Tower towerSold = inventoryManager.getInventoryData().getReserveTowers()[towerIndex];
            gameManager.getGameData().setMoney(gameManager.getGameData().getMoney() + towerSold.getSellingPrice());
            inventoryManager.getInventoryData().getReserveTowers()[towerIndex] = null;
        }
    }

    /**
     * Sells the selected {@link Upgrade} by calling .get() on the player's owned Upgrades based on the selected
     * upgradeIndex. Called by the ShopScreenController.
     * <br><br>
     * The sold Upgrade is removed from the player's inventory via the {@link InventoryManager}
     * and the Upgrade's selling price points is added to the player's point count.
     * <br><br>
     * If the selected upgradeIndex points to null or is out of range of the owned Upgrades ArrayList, a
     * {@link SellingNullError} is thrown to be handled by the ShopScreenController.
     * @param upgradeIndex the selected Upgrade's {@link Integer} index.
     * @throws SellingNullError occurs when nothing is selected.
     * @see seng201.team8.gui.ShopScreenController
     */
    public void sellUpgrade(int upgradeIndex) throws SellingNullError {
        if(upgradeIndex >= inventoryManager.getInventoryData().getUpgrades().size()){
            throw new SellingNullError("Why are you trying to sell air...?");
        }
        if (inventoryManager.getInventoryData().getUpgrades().get(upgradeIndex) == null){
            throw new SellingNullError("Why are you trying to sell air...?");
        }
        else{
            Upgrade upgradeSold = inventoryManager.getInventoryData().getUpgrades().get(upgradeIndex);
            gameManager.getGameData().setPoint(gameManager.getGameData().getPoint() + upgradeSold.getSellingPrice());
            inventoryManager.getInventoryData().getUpgrades().remove(upgradeIndex);
        }
    }

    /**
     * Getter for {@link ShopManager#shopData}
     * @return {@link ShopManager#shopData}
     */
    public ShopData getShopData() {
        return shopData;
    }
}
