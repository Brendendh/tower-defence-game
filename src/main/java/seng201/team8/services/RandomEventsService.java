package seng201.team8.services;

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

    public void levelUpRandomTower(){
        Tower randomTower = playerTowers.get(random.nextInt(playerTowers.size()));
        towerStatsManager.levelUp(randomTower);
    }

    public void destroyRandomTower(){
        Tower randomTower = playerTowers.get(random.nextInt(playerTowers.size()));
        randomTower.setBroken(true);
    }

    public void boostRandomTower(){
        Tower randomTower = playerTowers.get(random.nextInt(playerTowers.size()));
        randomTower.getTowerStats().setResourceAmount(randomTower.getTowerStats().getResourceAmount() + 10);
    }

    public void switchRssOfRandomTower(){
        Tower randomTower = playerTowers.get(random.nextInt(playerTowers.size()));
        //this may cause a glitch which could affect the list in GameManager itself...but we'll see :>
        randomTower.getTowerStats().setResourceType(gameManager.getDefaultResources()[random.nextInt(5)]);
    }
}
