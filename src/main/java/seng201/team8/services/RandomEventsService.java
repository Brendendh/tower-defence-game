package seng201.team8.services;

import seng201.team8.models.Resource;
import seng201.team8.models.Tower;

import java.util.ArrayList;
import java.util.Random;

public class RandomEventsService {
    private GameManager gameManager;

    private ArrayList<Tower> playerTowers;
    private Random random;
    private TowerStatsManager towerStatsManager;

    public RandomEventsService(GameManager gameManager){
        this.gameManager = gameManager;
        this.playerTowers = new ArrayList<>();
        this.towerStatsManager = new TowerStatsManager(gameManager.getInventoryManager().getInventoryData());
        this.playerTowers = towerStatsManager.getPlayerTowers();
        random = new Random();
    }

    //this is stupid ugly code, I should've just made random events its own class like upgrade which implements
    //events, but hey, it works and unfortunately 262 and 261 is knocking on my door.
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
    public void levelUpRandomTower(){
        Tower randomTower = playerTowers.get(random.nextInt(playerTowers.size()));
        towerStatsManager.levelUp(randomTower);
    }

    public void destroyRandomTower(){
        boolean notDestroyed = true;
        while (notDestroyed){
            Tower randomTower = playerTowers.get(random.nextInt(playerTowers.size()));
            if (!randomTower.isBroken()){
                randomTower.setBroken(true);
                notDestroyed = false;
            }
        }
    }

    public void boostRandomTower(){
        Tower randomTower = playerTowers.get(random.nextInt(playerTowers.size()));
        randomTower.getTowerStats().setResourceAmount(randomTower.getTowerStats().getResourceAmount() + 10);
    }

    public void switchRssOfRandomTower(){
        Tower randomTower = playerTowers.get(random.nextInt(playerTowers.size()));
        boolean sameResource = true;
        Resource chosenResource =gameManager.getDefaultResources()[random.nextInt(3)];;
        while (sameResource){
            chosenResource = gameManager.getDefaultResources()[random.nextInt(3)];
            if (chosenResource != randomTower.getTowerStats().getResourceType()){
                sameResource = false;
            }
        }
        //this may cause a glitch which could affect the list in GameManager itself...but we'll see :>
        randomTower.getTowerStats().setResourceType(chosenResource);
    }
}
