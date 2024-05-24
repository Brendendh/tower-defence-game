package seng201.team8.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team8.exceptions.BuyingNullError;
import seng201.team8.exceptions.NoSpaceException;
import seng201.team8.exceptions.NotEnoughCurrencyException;
import seng201.team8.exceptions.SellingNullError;
import seng201.team8.models.*;
import seng201.team8.models.dataRecords.GameData;
import seng201.team8.models.dataRecords.InventoryData;
import seng201.team8.models.dataRecords.ShopData;
import seng201.team8.models.effects.ResourceAmountBoost;
import seng201.team8.services.GameManager;
import seng201.team8.services.InventoryManager;
import seng201.team8.services.ShopManager;

import static org.junit.jupiter.api.Assertions.*;

public class ShopManagerTest {
    private ShopManager shopManager;
    private GameManager gameManager;
    private InventoryManager inventoryManager;

    @BeforeEach
    public void setupTest(){
        Tower[] testTowers = new Tower[]{new Tower("Starting Tower", new TowerStats(10, Resource.CORN,10), 10, Rarity.COMMON), null, null, null, null};
        InventoryData inventoryData = new InventoryData();
        inventoryData.setMainTowers(testTowers);
        inventoryManager = new InventoryManager(inventoryData);
        GameData gameData = new GameData();
        gameManager = new GameManager(gameData,inventoryManager);
        shopManager = new ShopManager(gameManager);
        gameManager.getInventoryManager().addUpgrade(new Upgrade(new ResourceAmountBoost(10),Rarity.COMMON,10,3));
    }

    @Test
    public void buyingTowerToMainTest() throws NoSpaceException, NotEnoughCurrencyException, BuyingNullError {
        Tower testTower1 = new Tower("", new TowerStats(10,Resource.CORN,5),10, Rarity.COMMON);
        Tower testTower2 = new Tower("", new TowerStats(10,Resource.WOOD,5),10, Rarity.COMMON);
        Tower testTower3 = new Tower("", new TowerStats(10, Resource.IRON,5),10, Rarity.COMMON);
        shopManager.getShopData().setTowersSold(new Tower[]{testTower1, testTower2, testTower3});
        gameManager.getGameData().setMoney(20);
        shopManager.buyTower(0);
        shopManager.buyTower(1);
        //checks that the shop towers are actually removed from shop
        assertNull(shopManager.getTowersSold()[0]);
        assertNull(shopManager.getTowersSold()[1]);
        //makes sure money is properly subtracted
        assertEquals(0,gameManager.getGameData().getMoney());
        //makes sure towers are actually placed in Main Inventory
        assertEquals(gameManager.getInventoryManager().getInventoryData().getMainTowers()[1],testTower1);
        assertEquals(gameManager.getInventoryManager().getInventoryData().getMainTowers()[2], testTower2);
        assertThrows(BuyingNullError.class, ()-> shopManager.buyTower(0));
    }
    @Test
    public void notEnoughMoneyAndPointsTest(){
        Tower testTower1 = new Tower("", new TowerStats(10,Resource.CORN,5),10, Rarity.COMMON);
        Tower testTower2 = new Tower("", new TowerStats(10,Resource.WOOD,5),10, Rarity.COMMON);
        Tower testTower3 = new Tower("", new TowerStats(10,Resource.IRON,5),10, Rarity.COMMON);
        shopManager.getShopData().setTowersSold(new Tower[]{testTower1, testTower2, testTower3});
        gameManager.getGameData().setMoney(0);
        gameManager.getGameData().setPoint(0);
        assertThrows(NotEnoughCurrencyException.class, () -> shopManager.buyTower(1));
        assertThrows(NotEnoughCurrencyException.class, () -> shopManager.buyUpgrade(0));
        //making sure money, points, upgrades and towers are not removed when the error is thrown
        assertNotNull(shopManager.getTowersSold()[1]);
        assertNotNull(shopManager.getUpgradesSold()[0]);
        assertEquals(gameManager.getGameData().getMoney(),0);
        assertEquals(gameManager.getGameData().getPoint(),0);
    }
    @Test public void buyingToReserveTest() throws NoSpaceException, NotEnoughCurrencyException, BuyingNullError {
        Tower testTower1 = new Tower("", new TowerStats(10,Resource.CORN,5),10, Rarity.COMMON);
        Tower testTower2 = new Tower("", new TowerStats(10,Resource.WOOD,5),10, Rarity.COMMON);
        Tower testTower3 = new Tower("", new TowerStats(10,Resource.IRON,5),10, Rarity.COMMON);
        shopManager.getShopData().setTowersSold(new Tower[]{testTower1, testTower2, testTower3});
        gameManager.getGameData().setMoney(100);
        Tower fillerTower = new Tower("filler", new TowerStats(10,Resource.CORN,5),10, Rarity.COMMON);
        gameManager.getInventoryManager().getInventoryData().setMainTowers(new Tower[]{fillerTower,fillerTower,fillerTower,fillerTower,fillerTower});
        shopManager.buyTower(0);
        assertEquals(gameManager.getInventoryManager().getInventoryData().getReserveTowers()[0],testTower1);
        assertThrows(BuyingNullError.class, ()-> shopManager.buyTower(0));
    }
    @Test
    public void refreshTest() throws NotEnoughCurrencyException {
        gameManager.setShopData(new ShopData());
        shopManager = new ShopManager(gameManager);
        assertThrows(NotEnoughCurrencyException.class, () -> shopManager.refresh());
        gameManager.getGameData().setMoney(15);
        Tower[] initialTowersSold = shopManager.getTowersSold();
        Upgrade[] initialUpgradesSold = shopManager.getUpgradesSold();
        shopManager.refresh();
        assertNotEquals(initialTowersSold, shopManager.getTowersSold());
        assertNotEquals(initialUpgradesSold, shopManager.getUpgradesSold());
        assertEquals(10,gameManager.getGameData().getMoney());
        shopManager.getShopData().setTowersSold(new Tower[]{null,null,null});
        gameManager.getGameData().setRound(6);
        shopManager.refresh();
        assertNotNull(shopManager.getTowersSold()[0]);
        shopManager.getShopData().setTowersSold(new Tower[]{null,null,null});
        gameManager.getGameData().setRound(14);
        shopManager.refresh();
        assertNotNull(shopManager.getTowersSold()[0]);
    }

    @Test
    public void upgradeNullBuyTest() throws BuyingNullError, NotEnoughCurrencyException {
        gameManager.getGameData().setPoint(100);
        int upgradePrice = shopManager.getUpgradesSold()[0].getBuyingPrice();
        shopManager.buyUpgrade(0);
        assertNull(shopManager.getUpgradesSold()[0]);
        assertNotNull(inventoryManager.getInventoryData().getUpgrades().get(0));
        assertEquals(100-upgradePrice,gameManager.getGameData().getPoint());
        assertThrows(BuyingNullError.class,()->shopManager.buyUpgrade(0));
    }

    @Test
    public void sellingItemsTest() throws SellingNullError {
        int initialTowerPrice = inventoryManager.getInventoryData().getMainTowers()[0].getSellingPrice();
        int initialUpgradePrice = inventoryManager.getInventoryData().getUpgrades().get(0).getSellingPrice();
        inventoryManager.getInventoryData().setReserveTowers(new Tower[]{new Tower("Starting Tower", new TowerStats(10, Resource.CORN,10), 10, Rarity.COMMON), null, null, null, null});
        shopManager.sellMainTower(0);
        assertEquals(gameManager.getGameData().getMoney(),initialTowerPrice);
        shopManager.sellReserveTower(0);
        assertEquals(gameManager.getGameData().getMoney(),2*initialTowerPrice);
        assertThrows(SellingNullError.class,()->shopManager.sellMainTower(0));
        assertThrows(SellingNullError.class, () -> shopManager.sellReserveTower(0));
        shopManager.sellUpgrade(0);
        assertEquals(gameManager.getGameData().getPoint(),initialUpgradePrice);
        assertThrows(SellingNullError.class, () -> shopManager.sellUpgrade(0));
        inventoryManager.addUpgrade(null);
        assertThrows(SellingNullError.class, () -> shopManager.sellUpgrade(0));
    }

}
