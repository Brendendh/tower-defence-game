package seng201.team8.services;

import seng201.team8.gui.RandomEventScreenController;
import seng201.team8.models.Resource;
import seng201.team8.models.Tower;
import seng201.team8.models.dataRecords.InventoryData;

import java.util.ArrayList;
import java.util.Random;

/**
 * The service class responsible for handling the logic behind the different random events
 * that occur during the game.
 * <br><br>
 * A specified random event is randomly chosen by the RandomEventScreenController and then the service applies
 * the effects of the random event.
 * @see RandomEventScreenController
 */
public class RandomEventsService {
    /**
     * The current game's {@link GameManager}
     */
    private final GameManager gameManager;
    /**
     * An {@link ArrayList} containing the player owned {@link Tower}s.
     */
    private ArrayList<Tower> playerTowers;
    /**
     * Used to randomly generate numbers.
     * @see Random
     */
    private final Random random;
    /**
     * The {@link TowerStatsManager} responsible for setting the {@link RandomEventsService#playerTowers} and handling
     * the levelling aspect of certain random events.
     * @see RandomEventsService#boostRandomTower()
     */
    private final TowerStatsManager towerStatsManager;

    /**
     * The constructor for RandomEventsService. Takes in the game's {@link GameManager} as a parameter.
     * <br><br>
     * Called by the RandomEventScreenController during initialization.
     * @see RandomEventScreenController#RandomEventScreenController(GameManager)
     * @param gameManager {@link GameManager}
     */
    public RandomEventsService(GameManager gameManager){
        this.gameManager = gameManager;
        this.playerTowers = new ArrayList<>();
        this.towerStatsManager = new TowerStatsManager(gameManager.getInventoryManager().getInventoryData());
        this.playerTowers = towerStatsManager.getPlayerTowers();
        random = new Random();
    }

    /**
     * Takes in an {@link Integer} i as a parameter that decides which random event to be called.
     * <br><br>
     * If i = 0, it calls {@link RandomEventsService#levelUpRandomTower()}
     * <br><br>
     * If i = 1, it calls {@link RandomEventsService#switchRssOfRandomTower()}
     * <br><br>
     * If i = 2, it calls {@link RandomEventsService#destroyRandomTower()}
     * <br><br>
     * If i = 3, it calls {@link RandomEventsService#boostRandomTower()}
     * <br><br>
     * If i > 3, nothing happens.
     * @param i {@link Integer}
     */
    public void executeRandomEvent(int i){
        if (i == 0){
            levelUpRandomTower();
        }
        if (i == 1){
            switchRssOfRandomTower();
        }
        if (i == 2){
            destroyRandomTower();
        }
        if (i == 3){
            boostRandomTower();
        }
    }

    /**
     * Randomly selects a player {@link Tower} and increases it's level by 1.
     */
    public void levelUpRandomTower(){
        Tower randomTower = playerTowers.get(random.nextInt(playerTowers.size()));
        towerStatsManager.levelUp(randomTower);
    }

    /**
     * Randomly selects a non-destroyed main player {@link Tower} and destroys it.
     */
    public void destroyRandomTower(){
        InventoryData inventoryData = gameManager.getInventoryManager().getInventoryData();
        ArrayList<Tower> mainTowers = new ArrayList<>();
        for (int i = 0; i < inventoryData.getMainTowers().length; i++){
            if (inventoryData.getMainTowers()[i] != null){
                mainTowers.add(inventoryData.getMainTowers()[i]);
            }
        }
        boolean notDestroyed = true;
        while (notDestroyed){
            Tower randomTower = mainTowers.get(random.nextInt(mainTowers.size()));
            if (!randomTower.isBroken()){
                randomTower.setBroken(true);
                notDestroyed = false;
            }
        }
    }

    /**
     * Randomly boosts the resource amount of a random player {@link Tower}.
     */
    public void boostRandomTower(){
        Tower randomTower = playerTowers.get(random.nextInt(playerTowers.size()));
        randomTower.getTowerStats().setResourceAmount(randomTower.getTowerStats().getResourceAmount() + 10);
    }

    /**
     * Randomly chooses a player owned {@link Tower} and change its {@link Resource} type to a random Resource type that
     * isn't the same as the original type.
     */
    public void switchRssOfRandomTower(){
        Tower randomTower = playerTowers.get(random.nextInt(playerTowers.size()));
        boolean sameResource = true;
        Resource chosenResource =gameManager.getDefaultResources()[random.nextInt(3)];
        while (sameResource){
            chosenResource = gameManager.getDefaultResources()[random.nextInt(3)];
            if (chosenResource != randomTower.getTowerStats().getResourceType()){
                sameResource = false;
            }
        }
        randomTower.getTowerStats().setResourceType(chosenResource);
    }
}
