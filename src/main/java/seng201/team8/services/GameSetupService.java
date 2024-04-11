package seng201.team8.services;

import seng201.team8.models.GameData;
import seng201.team8.models.InventoryData;
import seng201.team8.models.Tower;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameSetupService {
    private GameData gameData;

    public GameSetupService(){
        gameData = new GameData();
    }

    public boolean setName(String name){
        Pattern pattern = Pattern.compile("\\w{3,15}");
        Matcher matcher = pattern.matcher(name);
        if(matcher.matches()){
            gameData.setName(name);
            return true;
        }
        return false;
    }

    public void setMoney(int money){
        gameData.setMoney(money);
    }

    public void setPoint(int point){
        gameData.setPoint(point);
    }

    public void setDifficulty(int difficulty){
        gameData.setDifficulty(difficulty);
    }

    public void setTargetRound(int targetRound){
        gameData.setTargetRound(targetRound);
    }

    public GameData getGameData() {
        return gameData;
    }

    public InventoryData createInventory(Tower[] towers){
        InventoryData inventoryData = new InventoryData();
        Tower[] mainTowers = new Tower[5];
        System.arraycopy(towers, 0, mainTowers, 0, towers.length);
        inventoryData.setMainTowers(mainTowers);
        return inventoryData;
    }
}
