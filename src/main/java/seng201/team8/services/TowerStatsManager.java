package seng201.team8.services;

import seng201.team8.models.*;
import seng201.team8.models.dataRecords.InventoryData;

import java.util.ArrayList;

/**
 * The service that handles the logic behind the leveling system of the {@link Tower}
 * <br><br>
 * Whenever a class deals with adding exp or levelling up towers, an instance of TowerStatsManager
 * is created to handle the logic behind it.
 * @see RandomEventsService#boostRandomTower()
 * @see seng201.team8.models.effects.ExpBoost
 * @see RoundEndService#applyStats(int, int, int)
 */

public class TowerStatsManager {
    /**
     * Stores the player's current {@link InventoryData}
     */
    private InventoryData inventoryData;
    /**
     * An Array of Integers which represents the required exp points a tower needs to level up
     * <br><br>
     * The Integer at the index is the exp points needed for that tower to reach that index's level.
     * Since the starting level of a tower is lvl 0, index 0 of the Array is set to -1.
     */
                                                       //lvl:0, 1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11,  12,  13,  14,  15(max)
    private static final int[] levelRequirements = new int[]{-1, 5, 10, 15, 20, 25, 35, 45, 55, 65, 75, 90, 105, 120, 135, 150};

    /**
     * An ArrayList containing all the player's currently owned {@link Tower}s.
     */
    private ArrayList<Tower> playerTowers;

    /**
     * The constructor for TowerStatsManager that does not require an {@link InventoryData}.
     * <br><br>
     * Used by classes that does not store player InventoryData but require levelling towers like
     * {@link RoundEndService} and the ExpBoost {@link Upgrade}.
     * @see seng201.team8.models.effects.ExpBoost
     */
    public TowerStatsManager(){
    }

    /**
     * The constructor for TowerStatsManager that takes in an {@link InventoryData} as a parameter, stores it
     * and then calls {@link TowerStatsManager#findPlayerTower(ArrayList)} to find all the player's owned {@link Tower}.
     * <br><br>
     * Called by {@link RandomEventsService} as one of the random events require to randomly select from the pool
     * of player owned towers.
     *
     * @param inventoryData {@link InventoryData}
     * @see RandomEventsService#boostRandomTower()
     */
    public TowerStatsManager(InventoryData inventoryData){
        this.inventoryData = inventoryData;

        this.playerTowers = new ArrayList<>();
        findPlayerTower(this.playerTowers);
    }

    /**
     * Takes in an ArrayList as a parameter and adds all the player owned {@link Tower} to it.
     * @param playerTowers an {@link ArrayList} of {@link Tower}s
     */
    public void findPlayerTower(ArrayList<Tower> playerTowers){
        for (int i = 0; i < inventoryData.getMainTowers().length; i++){
            if (inventoryData.getMainTowers()[i] != null){
                playerTowers.add(inventoryData.getMainTowers()[i]);
            }
        }
        for (int i = 0; i < inventoryData.getReserveTowers().length; i++){
            if (inventoryData.getReserveTowers()[i] != null){
                playerTowers.add(inventoryData.getReserveTowers()[i]);
            }
        }
    }

    /**
     * Getter for {@link TowerStatsManager#playerTowers}
     * @return {@link TowerStatsManager#playerTowers}
     */
    public ArrayList<Tower> getPlayerTowers(){
        return playerTowers;
    }

    /**
     * Takes in a {@link Tower} and {@link Integer} as parameters and adds that Integer value to the Tower's exp value.
     * Calls {@link TowerStatsManager#levelUp(Tower)} if the Tower's new exp value exceed its level requirements.
     * @param tower {@link Tower} to add exp to.
     * @param addedExp amount of exp to add ot the tower.
     */
    public void addExp(Tower tower, int addedExp){
        int oldLvl = tower.clone().getLevel();
        tower.setExperiencePoints(tower.getExperiencePoints() + addedExp);
        while (tower.getExperiencePoints() >= levelRequirements[oldLvl] && oldLvl != 15){
            oldLvl += 1;
            levelUp(tower);
        }
    }

    /**
     * Takes in a {@link Tower} and increases its level by one. The Tower's resource amount and selling price
     * are also increased accordingly.
     * @param tower the {@link Tower} to be leveled up
     */
    public void levelUp(Tower tower){
        if (tower.getLevel() < 15){
            tower.setLevel(tower.getLevel() + 1);
            Resource towerResource = tower.getTowerStats().getResourceType();
            tower.getTowerStats().setResourceAmount(tower.getTowerStats().getResourceAmount() + (10/towerResource.getResourceValue()));
            tower.setSellingPrice(tower.getSellingPrice() + (5 * tower.getRarity().getRarityStatMultiplier()));
        }
    }
    /**
     * Getter for {@link TowerStatsManager#levelRequirements}
     * @return {@link TowerStatsManager#levelRequirements}
     */
    public static int[] getLevelRequirements(){
        return levelRequirements;
    }
}
