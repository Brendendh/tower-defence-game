package seng201.team8.services;

import seng201.team8.models.Tower;

import java.util.Random;

/**
 * The service class for RoundEndService. Created when the
 * RoundEndController is created.
 * <br><br>
 * Provides the methods to check if a random event will be played,
 * calculates the points, exp points and money reward, and
 * applying the stats to the model classes.
 */
public class RoundEndService {

    /**
     * The {@link GameManager} for getting the information required.
     */
    private final GameManager gameManager;
    /**
     * The {@link TowerStatsManager} to manage the tower levels and exp points.
     */
    private final TowerStatsManager towerStatsManager;
    /**
     * The {@link Integer} value that represents the number of carts that
     * were in the round.
     */
    private final int cartNum;
    /**
     * The {@link Integer} value that represents the current round number.
     */
    private final int roundNum;
    /**
     * The {@link Integer} value that represents the difficulty of the
     * current game.
     */
    private final int difficulty;

    /**
     * The constructor for {@link GameManager}.
     * <br><br>
     * Takes in a {@link GameManager} object and creates a {@link TowerStatsManager} which are
     * stored in the RoundEndService. The number of carts, round number and difficulty are also stored in the
     * RoundEndService.
     * @param gameManager {@link GameManager}
     */
    public RoundEndService(GameManager gameManager) {
        this.gameManager = gameManager;
        this.towerStatsManager = new TowerStatsManager();
        cartNum = gameManager.getRound().getCartNumber();
        roundNum = gameManager.getGameData().getRound();
        difficulty = gameManager.getGameData().getDifficulty();
    }

    /**
     * Checks if a random event should be played or not.
     * The probability of a random event happening is 40%.
     * @return {@link Boolean}
     */
    public boolean isRandomEventPlayed(){
        Random rand = new Random();
        return rand.nextInt(0, 10) < 4;
    }

    /**
     * Calculates the amount of exp point rewards using, the number
     * of carts defeated, the round number and the difficulty.
     * @return {@link Integer}
     */
    public int getExpPoints(){
        return cartNum + roundNum / 3 + difficulty * 2;
    }

    /**
     * Calculates the amount of point rewards using, the number
     * of carts defeated, the round number and the difficulty.
     * @return {@link Integer}
     */
    public int getPoints(){
        return roundNum * 4 + (difficulty+1) * cartNum;

    }

    /**
     * Calculates the amount of money rewards using, the number
     * of carts defeated, the round number and the difficulty.
     * @return {@link Integer}
     */
    public int getMoney(){
        return roundNum * 6 + (difficulty+1) * cartNum;

    }

    /**
     * Deposits the given points and money into the {@link seng201.team8.models.dataRecords.GameData} and adds exp
     * to each {@link Tower} in the main towers which are not broken.
     * @param points {@link Integer}
     * @param money {@link Integer}
     * @param expPoints {@link Integer}
     */
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
