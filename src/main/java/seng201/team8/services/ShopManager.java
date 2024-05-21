package seng201.team8.services;

import seng201.team8.exceptions.BuyingNullError;
import seng201.team8.exceptions.NoSpaceException;
import seng201.team8.exceptions.NotEnoughCurrencyException;
import seng201.team8.exceptions.SellingNullError;
import seng201.team8.models.*;
import seng201.team8.models.dataRecords.ShopData;

import java.util.Random;

public class ShopManager {
    private final ShopData shopData;
    private final InventoryManager inventoryManager;
    private final GameManager gameManager;
    private final Random randomGenerator;
    private final Tower[] defaultTowersToPick;
    private final Upgrade[] defaultUpgrades;

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

    public void updateTowers(){
        Tower[] towersToBeSold = new Tower[3];
        for (int i = 0; i < 3; i++){
            int randomTowerIndex = randomGenerator.nextInt(defaultTowersToPick.length);
            Tower tower = defaultTowersToPick[randomTowerIndex].clone();
            towersToBeSold[i] = new Tower(tower.getName(), tower.getTowerStats(), tower.getBuyingPrice(),generateRarities(gameManager.getGameData().getRound()));
        }
        shopData.setTowersSold(towersToBeSold);
    }

    public void updateUpgrades(){
        Upgrade[] upgradesToBeSold = new Upgrade[3];
        for (int i = 0; i < 3; i++){
            int randomUpgradeIndex = randomGenerator.nextInt(defaultUpgrades.length);
            Upgrade upgrade = defaultUpgrades[randomUpgradeIndex].clone();
            upgradesToBeSold[i] = new Upgrade(upgrade.getEffect(),generateRarities(gameManager.getGameData().getRound()),upgrade.getBuyingPrice(),upgrade.getMaximumTargets());
        }
        shopData.setUpgradesSold(upgradesToBeSold);

    }
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

    public Tower[] getTowersSold(){
        return shopData.getTowersSold();
    }
    public Upgrade[] getUpgradesSold(){
        return shopData.getUpgradesSold();
    }

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

    public ShopData getShopData() {
        return shopData;
    }
}
