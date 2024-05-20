package seng201.team8.services;

import seng201.team8.models.*;

import java.util.ArrayList;

public class TowerStatsManager {
    private InventoryData inventoryData;
                                                //lvl:0, 1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11,  12,  13,  14,  15(max)
    private static final int[] levelRequirements = new int[]{-1, 5, 10, 15, 20, 25, 35, 45, 55, 65, 75, 90, 105, 120, 135, 150};
    private ArrayList<Tower> playerTowers;

    public TowerStatsManager(){
    }
    public TowerStatsManager(InventoryData inventoryData){
        this.inventoryData = inventoryData;

        this.playerTowers = new ArrayList<>();
        findPlayerTower(this.playerTowers);
    }

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
    public ArrayList<Tower> getPlayerTowers(){
        return playerTowers;
    }

    public void addExp(Tower tower, int addedExp){
        int oldLvl = tower.clone().getLevel();
        tower.setExperiencePoints(tower.getExperiencePoints() + addedExp);
        while (tower.getExperiencePoints() >= levelRequirements[oldLvl] && oldLvl != 15){
            oldLvl += 1;
            levelUp(tower);
        }
    }

    public void levelUp(Tower tower){
        if (tower.getLevel() < 15){
            tower.setLevel(tower.getLevel() + 1);
            Resource towerResource = tower.getTowerStats().getResourceType();
            tower.getTowerStats().setResourceAmount(tower.getTowerStats().getResourceAmount() + (10/towerResource.getResourceValue()));
            tower.setSellingPrice(tower.getSellingPrice() + (5 * tower.getRarity().getRarityStatMultiplier()));
        }
    }

    public static int[] getLevelRequirements(){
        return levelRequirements;
    }
}
