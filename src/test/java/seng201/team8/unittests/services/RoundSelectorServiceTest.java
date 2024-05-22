package seng201.team8.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team8.models.*;
import seng201.team8.models.dataRecords.GameData;
import seng201.team8.models.dataRecords.InventoryData;
import seng201.team8.services.GameManager;
import seng201.team8.services.InventoryManager;
import seng201.team8.services.RoundSelectorService;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoundSelectorServiceTest {
    private GameManager gameManager;
    private RoundSelectorService roundSelectorService;
    @BeforeEach
    public void setupTest(){
        Tower[] testTowers = new Tower[]{new Tower("Starting Tower", new TowerStats(10, Resource.CORN,10), 10, Rarity.COMMON), null, null, null, null};
        InventoryData inventoryData = new InventoryData();
        inventoryData.setMainTowers(testTowers);
        InventoryManager inventoryManager = new InventoryManager(inventoryData);
        GameData gameData = new GameData();
        this.gameManager = new GameManager(gameData,inventoryManager);
    }

    @Test
    public void testGetPlayerOwnedResourceTypes(){
        ArrayList<Resource> expectedResourceTypes = new ArrayList<>();
        expectedResourceTypes.add(Resource.CORN);
        roundSelectorService = new RoundSelectorService(gameManager);
        assertEquals(expectedResourceTypes,roundSelectorService.getPlayerOwnedResourceTypes());
        expectedResourceTypes.add(Resource.IRON);
        expectedResourceTypes.add(Resource.WOOD);
        gameManager.getInventoryManager().getInventoryData().setMainTowers(new Tower[]{new Tower("Starting Tower", new TowerStats(10, Resource.CORN,10), 10, Rarity.COMMON),
                new Tower("Starting Tower", new TowerStats(10, Resource.IRON,10), 10, Rarity.COMMON), null, null, null});
        gameManager.getInventoryManager().getInventoryData().setReserveTowers(new Tower[]{new Tower("Starting Tower", new TowerStats(10, Resource.WOOD,10),10, Rarity.COMMON),
                new Tower("Starting Tower", new TowerStats(10, Resource.WOOD,10),10, Rarity.COMMON),null,null,null});
        assertEquals(expectedResourceTypes,roundSelectorService.getPlayerOwnedResourceTypes());
    }
    @Test
    public void testDifficultyRoundGeneration(){
        gameManager.getGameData().setDifficulty(1);
        roundSelectorService = new RoundSelectorService(gameManager);
        Round[] possibleRounds = roundSelectorService.getPossibleRounds();
        assertNotNull(possibleRounds);
        assertEquals(possibleRounds[1],roundSelectorService.getSelectedRound(1));
    }

}
