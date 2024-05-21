package seng201.team8.services;

import seng201.team8.models.Tower;

import java.util.Random;

public class RoundEndService {

    private final GameManager gameManager;
    private final TowerStatsManager towerStatsManager;
    private final int cartNum;
    private final int roundNum;
    private final int difficulty;

    public RoundEndService(GameManager gameManager) {
        this.gameManager = gameManager;
        this.towerStatsManager = new TowerStatsManager();
        cartNum = gameManager.getRound().getCartNumber();
        roundNum = gameManager.getGameData().getRound();
        difficulty = gameManager.getGameData().getDifficulty();
    }

    public boolean isRandomEventPlayed(){
        Random rand = new Random();
        return rand.nextInt(0, 10) < 3;
    }

    public int getExpPoints(){
        return cartNum + roundNum / 3 + difficulty * 2;
    }

    public int getPoints(){
        return roundNum * 4 + (difficulty+1) * cartNum;

    }

    public int getMoney(){
        return roundNum * 6 + (difficulty+1) * cartNum;

    }

    public void applyStats(int points, int money, int expPoints){
        for(Tower tower : gameManager.getInventoryManager().getInventoryData().getMainTowers()){
            if(tower != null){
                if(!tower.isBroken()) {
                    towerStatsManager.addExp(tower, expPoints);
                }
            }
        }
        gameManager.getGameData().setPoint(gameManager.getGameData().getPoint() + points);
        gameManager.getGameData().setMoney(gameManager.getGameData().getMoney() + money);
    }


}
