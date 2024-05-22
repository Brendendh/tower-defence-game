package seng201.team8.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team8.exceptions.NoSpaceException;
import seng201.team8.models.*;
import seng201.team8.models.dataRecords.InventoryData;
import seng201.team8.models.effects.CooldownReduction;
import seng201.team8.services.InventoryManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testRemoveFromReserve(){
        Tower testTower = new Tower("", new TowerStats(10, Resource.CORN,10), 10, Rarity.EPIC);
        inventoryManager.getInventoryData().getReserveTowers()[3] = testTower;
        assertEquals(inventoryManager.getInventoryData().getReserveTowers()[3], testTower);
        inventoryManager.removeFromReserve(3);
        assertNull(inventoryManager.getInventoryData().getReserveTowers()[3]);
    }

    @Test
    public void testSwapTowersMainAndReserve(){
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
    public void testSwapTowersReserveAndMain(){
        Tower testMainTower = new Tower("", new TowerStats(10, Resource.CORN,10), 10, Rarity.COMMON);
        Tower testReserveTower = new Tower("", new TowerStats(15, Resource.WOOD,10), 10, Rarity.COMMON);
        inventoryManager.getInventoryData().getMainTowers()[3] = testMainTower;
        inventoryManager.getInventoryData().getReserveTowers()[2] = testReserveTower;
        assertEquals(inventoryManager.getInventoryData().getMainTowers()[3],testMainTower);
        assertEquals(inventoryManager.getInventoryData().getReserveTowers()[2], testReserveTower);
        inventoryManager.swapTowers(7, 3);
        assertEquals(inventoryManager.getInventoryData().getMainTowers()[3], testReserveTower);
        assertEquals(inventoryManager.getInventoryData().getReserveTowers()[2], testMainTower);
    }

    @Test
    public void testRemoveUpgrade() {
        Upgrade upgrade = new Upgrade(new CooldownReduction(2), Rarity.COMMON, 10, 2);
        inventoryManager.addUpgrade(upgrade);
        assertEquals(upgrade, inventoryManager.getInventoryData().getUpgrades().get(0));
        inventoryManager.removeUpgrade(0);
        assertEquals(0, inventoryManager.getInventoryData().getUpgrades().size());

    }

    @Test
    public void testApplyUpgradeTo(){
        Upgrade upgrade = new Upgrade(new CooldownReduction(2), Rarity.COMMON, 10, 2);
        Tower testTower = new Tower("", new TowerStats(10, Resource.IRON, 10), 10, Rarity.COMMON);
        inventoryManager.addUpgrade(upgrade);
        assertEquals(upgrade, inventoryManager.getInventoryData().getUpgrades().get(0));
        inventoryManager.applyUpgradeTo(0, List.of(testTower));
        assertEquals(8, testTower.getTowerStats().getCooldown());
    }

    @Test
    public void testMoveToReserve(){
        Tower testTower = new Tower("", new TowerStats(10, Resource.CORN, 10), 10, Rarity.EPIC);

        assertDoesNotThrow(() -> inventoryManager.moveToReserve(testTower));
        assertEquals(testTower, inventoryManager.getInventoryData().getReserveTowers()[0]);
    }

    @Test
    public void testMoveToMain(){
        Tower testTower = new Tower("", new TowerStats(10, Resource.CORN, 10), 10, Rarity.EPIC);

        assertDoesNotThrow(() -> inventoryManager.moveToMain(testTower));
        assertEquals(testTower, inventoryManager.getInventoryData().getMainTowers()[1]);
    }

    @Test
    public void testMoveToMainNoSpace() {
        Tower testTower = new Tower("", new TowerStats(10, Resource.CORN, 10), 10, Rarity.EPIC);
        for(int i = 0; i < 4; i++){
            assertDoesNotThrow(() -> inventoryManager.moveToMain(testTower));
        }
        assertThrows(NoSpaceException.class, () -> inventoryManager.moveToMain(testTower));
    }

    @Test
    public void testMoveToReserveNoSpace() {
        Tower testTower = new Tower("", new TowerStats(10, Resource.CORN, 10), 10, Rarity.EPIC);
        for(int i = 0; i < 5; i++){
            assertDoesNotThrow(() -> inventoryManager.moveToReserve(testTower));
        }
        assertThrows(NoSpaceException.class, () -> inventoryManager.moveToReserve(testTower));
    }
}
