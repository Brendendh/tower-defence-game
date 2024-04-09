package seng201.team8.services;

import seng201.team8.models.GameData;

public class GameSetupService {
    private GameData gameData;

    public GameSetupService(){
        gameData = new GameData();
    }

    public void setName(String name){
        gameData.setName(name);
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
}
