package seng201.team8.unittests.services;

import seng201.team8.models.*;
import seng201.team8.models.dataRecords.GameData;
import seng201.team8.models.dataRecords.InventoryData;
import seng201.team8.models.effects.ResourceAmountBoost;
import seng201.team8.services.GameManager;
import seng201.team8.services.InventoryManager;
import seng201.team8.services.ShopManager;

public class ShopManagerGenerateTest {
    public static void main(String[] args) {
        Tower[] testTowers = new Tower[]{new Tower("Starting Tower", new TowerStats(10, Resource.CORN, 10), 10, Rarity.COMMON), null, null, null, null};
        InventoryData inventoryData = new InventoryData();
        inventoryData.setMainTowers(testTowers);
        InventoryManager inventoryManager = new InventoryManager(inventoryData);
        GameData gameData = new GameData();
        GameManager gameManager = new GameManager(gameData, inventoryManager);
        gameManager.getInventoryManager().addUpgrade(new Upgrade(new ResourceAmountBoost(10), Rarity.COMMON, 10, 3));
        gameManager.getGameData().setRound(14);
        ShopManager shopManager = new ShopManager(gameManager);
        for (Tower tower : shopManager.getTowersSold()) {
            System.out.println("Tower sold: " + tower.getName() + " Rss type: " + tower.getTowerStats().getResourceType() + " Rss cd: " + tower.getTowerStats().getCooldown() + " Rarity: " + tower.getRarity() + " Production amount " + tower.getTowerStats().getResourceAmount() + "Selling for " + tower.getBuyingPrice());
        }
        System.out.println("---------------------------------------------");
        for (Tower tower: gameManager.getDefaultTowers()){
            System.out.println("Tower sold: " + tower.getName() + " Rss type: " + tower.getTowerStats().getResourceType() + " Rss cd: " + tower.getTowerStats().getCooldown() + " Rarity: " + tower.getRarity() + " Production amount " + tower.getTowerStats().getResourceAmount() + "Selling for " + tower.getBuyingPrice());
        }
        System.out.println("---------------------------------------------");
        System.out.println("Upgrade section");
        for (Upgrade upgrade : shopManager.getUpgradesSold()){
            System.out.println("Upgrade sold: "+ upgrade.getEffect()+" effect amount : "+upgrade.getBoostAmount()+" Rarity: "+upgrade.getRarity()+" Selling for: "+upgrade.getBuyingPrice()+" Affects: "+upgrade.getMaximumTargets());
        }
        System.out.println("---------------------------------------------");
        for (Upgrade upgrade : gameManager.getDefaultUpgrades()){
            System.out.println("Default upgrade: "+ upgrade.getEffect()+" effect amount : "+upgrade.getBoostAmount()+" Rarity: "+upgrade.getRarity()+" Selling for: "+upgrade.getBuyingPrice()+" Affects: "+upgrade.getMaximumTargets());
        }
    }
}
