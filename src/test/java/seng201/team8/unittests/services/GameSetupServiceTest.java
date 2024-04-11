package seng201.team8.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team8.models.InventoryData;
import seng201.team8.models.Rarity;
import seng201.team8.models.Tower;
import seng201.team8.models.TowerStats;
import seng201.team8.services.GameSetupService;

import static org.junit.jupiter.api.Assertions.*;

public class GameSetupServiceTest {
    GameSetupService gameSetupService;

    @BeforeEach
    public void setUpTest(){
        gameSetupService = new GameSetupService();
    }

    @Test
    public void testSetNameShort(){
        boolean result = gameSetupService.setName("My");
        assertFalse(result);
    }

    @Test
    public void testSetNameMedium(){
        boolean result = gameSetupService.setName("BobRoss");
        assertTrue(result);
    }

    @Test
    public void testSetNameLong(){
        boolean result = gameSetupService.setName("MyNameIsTOOLONGGG");
        assertFalse(result);
    }

    @Test
    public void testSetNameSpecial(){
        boolean result = gameSetupService.setName("#ILOVECODING#");
        assertFalse(result);
    }

    @Test
    public void testCreateInventory(){
        Tower[] towers = new Tower[]{new Tower("", new TowerStats(10, "Coal",10), 10, Rarity.COMMON), new Tower("", new TowerStats(10, "Diamond",10), 10, Rarity.EPIC)};
        InventoryData inventoryData = gameSetupService.createInventory(towers);
        assertEquals(inventoryData.getMainTowers().length, 5);
    }
}
