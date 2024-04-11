package seng201.team8.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team8.services.GameSetupService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameSetupServiceTest {

    @Test
    public void testSetNameShort(){
        GameSetupService gameSetupService = new GameSetupService();
        boolean result = gameSetupService.setName("My");
        assertFalse(result);
    }

    @Test
    public void testSetNameMedium(){
        GameSetupService gameSetupService = new GameSetupService();
        boolean result = gameSetupService.setName("BobRoss");
        assertTrue(result);
    }

    @Test
    public void testSetNameLong(){
        GameSetupService gameSetupService = new GameSetupService();
        boolean result = gameSetupService.setName("MyNameIsTOOLONGGG");
        assertFalse(result);
    }

    @Test
    public void testSetNameSpecial(){
        GameSetupService gameSetupService = new GameSetupService();
        boolean result = gameSetupService.setName("#ILOVECODING#");
        assertFalse(result);
    }
}
