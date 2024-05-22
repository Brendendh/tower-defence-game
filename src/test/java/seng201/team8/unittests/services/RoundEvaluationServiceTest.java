package seng201.team8.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team8.exceptions.NoSpaceException;
import seng201.team8.models.*;
import seng201.team8.models.dataRecords.GameData;
import seng201.team8.services.GameManager;
import seng201.team8.services.InventoryManager;
import seng201.team8.services.RoundEvaluationService;

import static org.junit.jupiter.api.Assertions.*;

public class RoundEvaluationServiceTest {
    RoundEvaluationService roundEvaluationService;
    GameManager gameManager;

    @BeforeEach
    public void setUpTest() {
        InventoryManager inventoryManager = new InventoryManager();
        try {
            inventoryManager.moveToMain(new Tower("IRON", new TowerStats(2, Resource.IRON, 1), 10, Rarity.COMMON));
        } catch (NoSpaceException e) {
            throw new RuntimeException(e);
        }
        gameManager = new GameManager(new GameData(), inventoryManager);
        Round round = new Round(3);
        for(int i = 0; i < 3; i++){
            round.addCart(new Cart(10, Resource.IRON, 1));
        }
        gameManager.setRound(round);
        roundEvaluationService = new RoundEvaluationService(gameManager);
    }

    @Test
    public void testGenerateResourcesOneTower(){
        roundEvaluationService.produceResources();
        assertEquals(roundEvaluationService.getResourcesProduced().get(Resource.IRON), 2);
    }

    @Test
    public void testGenerateResourcesTwoSameTowers(){
        try {
            gameManager.getInventoryManager().moveToMain(new Tower("IRON", new TowerStats(2, Resource.IRON, 1), 10, Rarity.COMMON));
        } catch (NoSpaceException e) {
            throw new RuntimeException(e);
        }
        roundEvaluationService.produceResources();
        assertEquals(roundEvaluationService.getResourcesProduced().get(Resource.IRON), 4);
    }

    @Test
    public void testGenerateResourcesBrokenTowers(){
        gameManager.getInventoryManager().getInventoryData().getMainTowers()[0].setBroken(true);
        roundEvaluationService.produceResources();
        assertEquals(roundEvaluationService.getResourcesProduced().get(Resource.IRON), 0);
    }

    @Test
    public void testGenerateResourcesBrokenTowersNotCooldown(){
        roundEvaluationService.incrementCounter();
        gameManager.getInventoryManager().getInventoryData().getMainTowers()[0].setBroken(true);
        gameManager.getInventoryManager().getInventoryData().getMainTowers()[0].setTowerStats(new TowerStats(2, Resource.IRON, 2));
        roundEvaluationService.produceResources();
        assertEquals(roundEvaluationService.getResourcesProduced().get(Resource.IRON), 0);
    }

    @Test
    public void testFillCart(){
        roundEvaluationService.produceResources();
        assertEquals(roundEvaluationService.getResourcesProduced().get(Resource.IRON), 2);
        roundEvaluationService.fillCart(roundEvaluationService.getCarts()[0]);
        assertEquals(roundEvaluationService.getCarts()[0].getAmount(), 2);
    }

    @Test
    public void testFillCartsAllEmpty(){
        roundEvaluationService.produceResources();
        assertEquals(roundEvaluationService.getResourcesProduced().get(Resource.IRON), 2);
        roundEvaluationService.fillCarts();
        assertEquals(roundEvaluationService.getCarts()[0].getAmount(), 2);
        assertEquals(roundEvaluationService.getCarts()[1].getAmount(), 0);
        assertEquals(roundEvaluationService.getCarts()[2].getAmount(), 0);
    }

    @Test
    public void testFillCartsOverFill(){
        try {
            gameManager.getInventoryManager().moveToMain(new Tower("IRON", new TowerStats(15, Resource.IRON, 1), 10, Rarity.COMMON));
        } catch (NoSpaceException e) {
            throw new RuntimeException(e);
        }
        roundEvaluationService.produceResources();
        roundEvaluationService.fillCarts();
        assertEquals(roundEvaluationService.getCarts()[0].getAmount(), 10);
        assertEquals(roundEvaluationService.getCarts()[1].getAmount(), 7);
        assertEquals(roundEvaluationService.getCarts()[2].getAmount(), 0);
    }

    @Test
    public void testAdvanceCartsAllEmpty(){
        roundEvaluationService.advanceCarts();
        assertEquals(roundEvaluationService.getCarts()[0].getDistance(), 1);
        assertEquals(roundEvaluationService.getCarts()[1].getDistance(), 1);
        assertEquals(roundEvaluationService.getCarts()[2].getDistance(), 1);
    }

    @Test
    public void testAdvanceCartsOneFull(){
        roundEvaluationService.getCarts()[0].setAmount(10);
        roundEvaluationService.advanceCarts();
        assertEquals(roundEvaluationService.getCarts()[0].getDistance(), 0);
        assertEquals(roundEvaluationService.getCarts()[1].getDistance(), 1);
        assertEquals(roundEvaluationService.getCarts()[2].getDistance(), 1);
    }

    @Test
    public void testDidCartReachStart(){
        assertFalse(roundEvaluationService.didCartReach());
    }

    @Test
    public void testDidCartReachEnd(){
        roundEvaluationService.getCarts()[0].setDistance(15);
        assertTrue(roundEvaluationService.didCartReach());
    }

    @Test
    public void testAreAllCartsFullCartsFull(){
        for (int i = 0; i < 3; i ++){
            roundEvaluationService.getCarts()[i].setAmount(10);
        }
        assertTrue(roundEvaluationService.areAllCartsFull());

    }

    @Test
    public void testAreAllCartsFullCartsNotFull(){
        for (int i = 0; i < 3; i ++){
            roundEvaluationService.getCarts()[i].setAmount(5);
        }
        assertFalse(roundEvaluationService.areAllCartsFull());

    }
}
