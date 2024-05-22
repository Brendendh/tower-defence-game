package seng201.team8.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team8.models.*;
import seng201.team8.models.dataRecords.InventoryData;
import seng201.team8.services.TowerStatsManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TowerStatsManagerTest {
    private Tower[] testTowers;
    private InventoryData inventoryData;
    private TowerStatsManager towerStatsManager;
    @BeforeEach
    public void setupTest(){
        testTowers = new Tower[]{
                new Tower("Corn Farm", new TowerStats(10, Resource.CORN, 1 ), 10, Rarity.COMMON),
                new Tower("Corn Field", new TowerStats(35, Resource.CORN, 3), 15, Rarity.COMMON),
                new Tower("Timber Yard", new TowerStats(8, Resource.WOOD, 1), 10, Rarity.COMMON),
                new Tower("Forest", new TowerStats(30, Resource.WOOD, 3), 15, Rarity.COMMON),
                new Tower("Iron Mine", new TowerStats(1, Resource.IRON, 3), 10, Rarity.COMMON)};
        inventoryData = new InventoryData();
        inventoryData.setMainTowers(testTowers);
    }
    //makes sure findPlayerTowers is able to get player main towers and reserve towers properly
    @Test
    public void testSearchingTest() {
        Tower testTower1 = new Tower("test tower 1", new TowerStats(10,Resource.CORN,1),10,Rarity.COMMON);
        Tower testTower2 = new Tower("test tower 2", new TowerStats(10,Resource.CORN,1),10,Rarity.COMMON);
        inventoryData.setReserveTowers(new Tower[]{null,null,testTower2,testTower2,null});
        inventoryData.setMainTowers(new Tower[]{testTower1,testTower1,testTower1,testTower1,testTower1});
        ArrayList<Tower> expectedResult = new ArrayList<>();
        for (int i =0;i<5;i++){
            expectedResult.add(testTower1);
        }
        for (int i = 0; i<2;i++){
            expectedResult.add(testTower2);
        }
        towerStatsManager = new TowerStatsManager(inventoryData);
        assertEquals(towerStatsManager.getPlayerTowers(),expectedResult);
    }
    @Test
    public void addExpAndLevelUpTest(){
        Tower tower1 = inventoryData.getMainTowers()[0];
        int initialSellingPrice = tower1.getSellingPrice();
        int oldCornFarmExp = tower1.getExperiencePoints();
        int oldCornFarmLevel = tower1.getLevel();
        towerStatsManager = new TowerStatsManager(inventoryData);
        towerStatsManager.addExp(tower1,5);
        assertNotEquals(oldCornFarmExp,tower1.getExperiencePoints());
        assertNotEquals(oldCornFarmLevel,tower1.getLevel());
        assertEquals(tower1.getLevel(),2);
        assertEquals(tower1.getExperiencePoints(),5);
        //check if stats are increased appropriately
        int expectedResourceAmount = 10 + (2 * (10/tower1.getTowerStats().getResourceType().getResourceValue()));
        assertEquals(tower1.getTowerStats().getResourceAmount(), expectedResourceAmount);
        int expectedSellingPrice = initialSellingPrice + 2*(5*tower1.getRarity().getRarityStatMultiplier());
        assertEquals(tower1.getSellingPrice(), expectedSellingPrice);
    }

    @Test
    public void maxLevelTest(){
        Tower tower1 = inventoryData.getMainTowers()[0];
        tower1.setLevel(15);
        towerStatsManager = new TowerStatsManager(inventoryData);
        towerStatsManager.levelUp(tower1);
        assertEquals(15,tower1.getLevel());
    }

}
