package seng201.team8.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team8.models.Rarity;
import seng201.team8.models.Resource;
import seng201.team8.models.Tower;
import seng201.team8.models.TowerStats;
import seng201.team8.models.dataRecords.GameData;
import seng201.team8.models.dataRecords.InventoryData;
import seng201.team8.services.GameManager;
import seng201.team8.services.InventoryManager;
import seng201.team8.services.RandomEventsService;
import seng201.team8.services.TowerStatsManager;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RandomEventsTest {
    private GameManager gameManager;
    private Random random;
    private TowerStatsManager towerStatsManager;
    private ArrayList<Tower> playerTowers;
    private InventoryManager inventoryManager;
    private RandomEventsService randomEventsService;

    @BeforeEach
    public void setUpTest(){
        Tower[] testTowers = new Tower[]{new Tower("Starting Tower", new TowerStats(10, Resource.CORN,10), 10, Rarity.COMMON), null, null, null, null};
        InventoryData inventoryData = new InventoryData();
        inventoryData.setMainTowers(testTowers);
        this.inventoryManager = new InventoryManager(inventoryData);
        GameData gameData = new GameData();
        gameManager = new GameManager(gameData,this.inventoryManager);
        randomEventsService = new RandomEventsService(gameManager);
    }

    @Test
    public void testLevelRandomTower(){
        int initialLevel = inventoryManager.getInventoryData().getMainTowers()[0].getLevel();
        randomEventsService.executeRandomEvent(0);
        assertNotEquals(inventoryManager.getInventoryData().getMainTowers()[0].getLevel(),initialLevel);
        assertEquals(inventoryManager.getInventoryData().getMainTowers()[0].getLevel(),1);
    }

    @Test
    public void testBoostRandomTower(){
        randomEventsService.executeRandomEvent(3);
        assertEquals(inventoryManager.getInventoryData().getMainTowers()[0].getTowerStats().getResourceAmount(),20);
    }

    @Test
    public void testSwitchRssRandomTower(){
        Resource initialResource = inventoryManager.getInventoryData().getMainTowers()[0].getTowerStats().getResourceType();
        randomEventsService.executeRandomEvent(1);
        assertNotEquals(initialResource, inventoryManager.getInventoryData().getMainTowers()[0].getTowerStats().getResourceType());
    }

    @Test
    public void testDestroyRandomTower(){
        randomEventsService.executeRandomEvent(2);
        assertTrue(inventoryManager.getInventoryData().getMainTowers()[0].isBroken());
    }

}
