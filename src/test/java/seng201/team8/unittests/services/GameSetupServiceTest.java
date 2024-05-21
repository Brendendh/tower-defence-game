package seng201.team8.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team8.models.*;
import seng201.team8.models.dataRecords.InventoryData;
import seng201.team8.services.GameSetupService;

import static org.junit.jupiter.api.Assertions.*;

public class GameSetupServiceTest {
    GameSetupService gameSetupService;

    @BeforeEach
    public void setUpTest(){
        gameSetupService = new GameSetupService();
    }

    @Test
    public void testCheckNameShort(){
        boolean result = gameSetupService.checkName("My");
        assertFalse(result);
    }

    @Test
    public void testCheckNameMedium(){
        boolean result = gameSetupService.checkName("BobRoss");
        assertTrue(result);
    }

    @Test
    public void testCheckNameLong(){
        boolean result = gameSetupService.checkName("MyNameIsTOOLONGGG");
        assertFalse(result);
    }

    @Test
    public void testCheckNameSpecial(){
        boolean result = gameSetupService.checkName("#ILOVECODING#");
        assertFalse(result);
    }

    @Test
    public void testCheckNameWhitespace(){
        boolean result = gameSetupService.checkName("MyName  Is");
        assertTrue(result);
    }

    @Test
    public void testCheckNameEmpty(){
        boolean result = gameSetupService.checkName("");
        assertFalse(result);
    }

    @Test
    public void testCheckNameWhitespaceFront(){
        boolean result = gameSetupService.checkName(" MyNameIs");
        assertFalse(result);
    }

    @Test
    public void testCreateInventory(){
        Tower[] towers = new Tower[]{new Tower("", new TowerStats(10, Resource.CORN,10), 10, Rarity.COMMON), new Tower("", new TowerStats(10, Resource.IRON,10), 10, Rarity.EPIC)};
        InventoryData inventoryData = gameSetupService.createInventory(towers);
        assertEquals(inventoryData.getMainTowers().length, 5);
    }
}
