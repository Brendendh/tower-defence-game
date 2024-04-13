package seng201.team8.services;

import seng201.team8.exceptions.NoSpaceException;
import seng201.team8.exceptions.NotEnoughCurrencyException;
import seng201.team8.models.*;

import java.util.Random;

public class ShopManager {
    private ShopData shopData;
    private InventoryManager inventoryManager;
    private GameManager gameManager;
    private Random randomGenerator;
    private Tower[] defaultTowersToPick;
    private Upgrade[] defaultUpgrades;

    public ShopManager(GameManager gameManager){
        this.gameManager = gameManager;
        randomGenerator = new Random();
        defaultTowersToPick = gameManager.getDefaultTowers();
        defaultUpgrades = gameManager.getDefaultUpgrades();
        this.inventoryManager = this.gameManager.getInventoryManager();
        this.shopData = new ShopData();
        updateTowers();
        updateUpgrades();
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

    private void updateTowers(){
        Tower[] towersToBeSold = new Tower[3];
        for (int i = 0; i < 3; i++){
            int randomTowerIndex = randomGenerator.nextInt(defaultTowersToPick.length);
            Tower tower = defaultTowersToPick[randomTowerIndex];
            towersToBeSold[i] = new Tower(tower.getName(), tower.getTowerStats(), tower.getBuyingPrice(),generateRarities(gameManager.getGameData().getRound()));
        }
        shopData.setTowersSold(towersToBeSold);
    }

    private void updateUpgrades(){
        Upgrade[] upgradesToBeSold = new Upgrade[3];
        for (int i = 0; i < 3; i++){
            int randomUpgradeIndex = randomGenerator.nextInt(defaultUpgrades.length);
            Upgrade upgrade = defaultUpgrades[randomUpgradeIndex];
            upgradesToBeSold[i] = new Upgrade(upgrade.getEffect(),generateRarities(gameManager.getGameData().getRound()),upgrade.getBuyingPrice(),upgrade.getMaximumTargets());
        }
        shopData.setUpgradesSold(upgradesToBeSold);

    }
    public Rarity generateRarities(int currentRound){
        int randomValue = randomGenerator.nextInt(10);
        Rarity generatedRarity = Rarity.COMMON;
        if (currentRound <= 5){
            generatedRarity = gameManager.getEarlyGameRarity()[randomValue];
        }
        if (currentRound <= 10 && currentRound > 5){
            generatedRarity = gameManager.getMidGameRarity()[randomValue];
        }
        if (currentRound <= 15 && currentRound > 10){
            generatedRarity = gameManager.getLateGameRarity()[randomValue];
        }
        return generatedRarity;
    }

    public Tower[] getTowersSold(){
        return shopData.getTowersSold();
    }
    public Upgrade[] getUpgradesSold(){
        return shopData.getUpgradesSold();
    }

    public void buyTower(int towerIndex) throws NoSpaceException, NotEnoughCurrencyException {
        Tower towerBought = shopData.getTowersSold()[towerIndex];
        if (gameManager.getGameData().getMoney() < towerBought.getBuyingPrice()){
            throw new NotEnoughCurrencyException("Not enough money to purchase tower!");
        }
        else{
            try{
                inventoryManager.moveToMain(towerBought);
            }
            catch (NoSpaceException noSpaceInMain){
                inventoryManager.moveToReserve(towerBought);
            }
            gameManager.getGameData().setMoney(gameManager.getGameData().getMoney() - towerBought.getBuyingPrice());
            shopData.getTowersSold()[towerIndex] = null;
        }
    }
    public void buyUpgrade(int upgradeIndex) throws NotEnoughCurrencyException {
        Upgrade upgradeBought = shopData.getUpgradesSold()[upgradeIndex];
        if (gameManager.getGameData().getPoint() < upgradeBought.getBuyingPrice()){
            throw new NotEnoughCurrencyException("Not enough points to purchase upgrade!");
        }
        else{
            inventoryManager.getInventoryData().getUpgrades().add(upgradeBought);
            gameManager.getGameData().setPoint(gameManager.getGameData().getPoint() - upgradeBought.getBuyingPrice());
            shopData.getUpgradesSold()[upgradeIndex] = null;
        }
    }
    public void sellMainTower(int towerIndex){
        Tower towerSold = inventoryManager.getInventoryData().getMainTowers()[towerIndex];
        gameManager.getGameData().setMoney(gameManager.getGameData().getMoney() + towerSold.getSellingPrice());
        inventoryManager.getInventoryData().getMainTowers()[towerIndex] = null;
    }
    public void sellReserveTower(int towerIndex){
        Tower towerSold = inventoryManager.getInventoryData().getReserveTowers()[towerIndex];
        gameManager.getGameData().setMoney(gameManager.getGameData().getMoney() + towerSold.getSellingPrice());
        inventoryManager.getInventoryData().getReserveTowers()[towerIndex] = null;
    }

    public void sellUpgrade(int upgradeIndex){
        Upgrade upgradeSold = inventoryManager.getInventoryData().getUpgrades().get(upgradeIndex);
        gameManager.getGameData().setPoint(gameManager.getGameData().getPoint() + upgradeSold.getSellingPrice());
        inventoryManager.getInventoryData().getUpgrades().remove(upgradeIndex);
    }

    public ShopData getShopData() {
        return shopData;
    }

    public static void main(String[] args) {
        Tower[] testTowers = new Tower[]{new Tower("", new TowerStats(10, "Coal",10), 10, Rarity.COMMON), null, null, null, null};
        InventoryData inventoryData = new InventoryData();
        inventoryData.setMainTowers(testTowers);
        InventoryManager inventoryManager = new InventoryManager(inventoryData);
        GameData gameData = new GameData();
        gameData.setRound(11);
        GameManager gameManager = new GameManager(gameData,inventoryManager);
        ShopManager shopManager = new ShopManager(gameManager);

        shopManager.shopData.printStoreData();
    }

}
