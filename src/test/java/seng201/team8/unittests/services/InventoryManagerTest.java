package seng201.team8.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team8.models.*;
import seng201.team8.models.effects.CooldownReduction;
import seng201.team8.services.InventoryManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InventoryManagerTest {
    private InventoryManager inventoryManager;

    @BeforeEach
    public void setupTest(){
        Tower[] testTowers = new Tower[]{new Tower("", new TowerStats(10, Resource.CORN,5),10, Rarity.COMMON), null, null, null, null};
        InventoryData inventoryData = new InventoryData();
        inventoryData.setMainTowers(testTowers);
        inventoryManager = new InventoryManager(inventoryData);
    }

    @Test
    public void testRemoveFromMain(){
        Tower testTower = new Tower("", new TowerStats(10, Resource.CORN,10), 10, Rarity.EPIC);
        inventoryManager.getInventoryData().getMainTowers()[1] = testTower;
        assertEquals(inventoryManager.getInventoryData().getMainTowers()[1], testTower);
        inventoryManager.removeFromMain(1);
        assertNull(inventoryManager.getInventoryData().getMainTowers()[1]);
    }

    @Test
    public void testSwapTowers(){
        Tower testMainTower = new Tower("", new TowerStats(10, Resource.CORN,10), 10, Rarity.COMMON);
        Tower testReserveTower = new Tower("", new TowerStats(15, Resource.WOOD,10), 10, Rarity.COMMON);
        inventoryManager.getInventoryData().getMainTowers()[3] = testMainTower;
        inventoryManager.getInventoryData().getReserveTowers()[2] = testReserveTower;
        assertEquals(inventoryManager.getInventoryData().getMainTowers()[3],testMainTower);
        assertEquals(inventoryManager.getInventoryData().getReserveTowers()[2], testReserveTower);
        inventoryManager.swapTowers(3, 7);
        assertEquals(inventoryManager.getInventoryData().getMainTowers()[3], testReserveTower);
        assertEquals(inventoryManager.getInventoryData().getReserveTowers()[2], testMainTower);
    }

    @Test
    public void testApplyUpgradeTo(){
        Upgrade upgrade = new Upgrade(new CooldownReduction(2), Rarity.COMMON, 10, 2);
        inventoryManager.addUpgrade(upgrade);
        assertEquals(inventoryManager.getInventoryData().getUpgrades().get(0), upgrade);
    }
}
