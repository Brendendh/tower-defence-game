package seng201.team8.unittests.services;

import seng201.team8.models.*;
import seng201.team8.services.TowerStatsManager;

public class TowerStatsManagerTest {
    public static void main(String[] args) {
        Tower[] testTowers = new Tower[]{new Tower("Starting Tower", new TowerStats(10, Resource.CORN, 10), 10, Rarity.COMMON), new Tower("Seoncd Tower", new TowerStats(10, Resource.WOOD, 10), 10, Rarity.COMMON), null, null, null};
        InventoryData inventoryData = new InventoryData();
        inventoryData.setMainTowers(testTowers);
        TowerStatsManager test1 = new TowerStatsManager(inventoryData);
        test1.addExp(inventoryData.getMainTowers()[0], 56 );
        for (Tower tower: test1.getPlayerTowers()){
            System.out.println("Tower: " + tower.getName()+" Lvl: " + tower.getLevel() + " Exp value: " + tower.getExperiencePoints()+" rss amount: " + tower.getTowerStats().getResourceAmount());
        }
    }
}
