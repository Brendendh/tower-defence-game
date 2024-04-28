package seng201.team8.services;

import seng201.team8.models.InventoryData;
import seng201.team8.models.Rarity;
import seng201.team8.models.Tower;
import seng201.team8.models.TowerStats;

import java.util.ArrayList;

public class TowerStatsManager {
    private InventoryData inventoryData;
    private int[] levelRequirements;
    private ArrayList<Tower> playerTowers;

    public TowerStatsManager(InventoryData inventoryData){
        this.inventoryData = inventoryData;
                                     //lvl:0, 1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11,  12,  13,  14,  15(max)
        this.levelRequirements = new int[]{-1, 5, 10, 15, 20, 25, 35, 45, 55, 65, 75, 90, 105, 120, 135, 150};
        this.playerTowers = new ArrayList<Tower>();
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
        Tower clonedTower = tower.clone();
        int oldLvl = clonedTower.getLevel();
        tower.setExperiencePoints(tower.getExperiencePoints() + addedExp);
        while (tower.getExperiencePoints() >= levelRequirements[oldLvl] && oldLvl != 15){
            oldLvl += 1;
            levelUp(tower);
        }
    }

    public void levelUp(Tower tower){
        if (tower.getLevel() < 15){
            tower.setLevel(tower.getLevel() + 1);
            tower.getTowerStats().setResourceAmount(tower.getTowerStats().getResourceAmount() + 5);
        }
    }

    public static void main(String[] args) {
        Tower[] testTowers = new Tower[]{new Tower("Starting Tower", new TowerStats(10, "Coal", 10), 10, Rarity.COMMON), new Tower("Seoncd Tower", new TowerStats(10, "Coal", 10), 10, Rarity.COMMON), null, null, null};
        InventoryData inventoryData = new InventoryData();
        inventoryData.setMainTowers(testTowers);
        TowerStatsManager test1 = new TowerStatsManager(inventoryData);
        test1.addExp(inventoryData.getMainTowers()[0], 160 );
        for (Tower tower: test1.getPlayerTowers()){
            System.out.println("Tower: " + tower.getName()+" Lvl: " + tower.getLevel() + " Exp value: " + tower.getExperiencePoints()+" rss amount: " + tower.getTowerStats().getResourceAmount());
        }
    }
}
