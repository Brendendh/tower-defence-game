package seng201.team8.services;

import seng201.team8.models.dataRecords.GameData;
import seng201.team8.models.dataRecords.InventoryData;
import seng201.team8.models.Tower;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The service class for GameSetupService. Created when the
 * GameSetupController is created.
 * <br><br>
 * Serves as a builder for {@link GameData} and {@link InventoryManager}.
 */
public class GameSetupService {
    /**
     * The {@link GameData} object that
     * is to be created for the game.
     */
    private final GameData gameData;

    /**
     * The constructor for {@link GameSetupService}.
     * <br><br>
     * Creates a new {@link GameData} object and stores it in the GameSetupService.
     */
    public GameSetupService(){
        gameData = new GameData();
    }

    /**
     * Setter to set the {@link String} player name.
     * @param name {@link String}
     */
    public void setName(String name){
        gameData.setName(name);
    }

    /**
     * Checks if the {@link String} name matches the following conditions:
     *   - No special characters except for " " and "_".
     *   - Between 3 and 15 characters.
     * returns True if the conditions are matched
     *
     * @param name {@link String}
     * @return {@link Boolean}
     */
    public boolean checkName(String name){
        Pattern pattern = Pattern.compile("\\w[\\w ]{2,14}");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    /**
     * Setter to set the {@link Integer} player's money.
     * @param money {@link Integer}
     */
    public void setMoney(int money){
        gameData.setMoney(money);
    }

    /**
     * Setter to set the {@link Integer} player's points.
     * @param point {@link Integer}
     */
    public void setPoint(int point){
        gameData.setPoint(point);
    }

    /**
     * Setter to set the {@link Integer} player's difficulty.
     * 0 : Normal
     * 1 : Hard
     * @param difficulty {@link Integer}
     */
    public void setDifficulty(int difficulty){
        gameData.setDifficulty(difficulty);
    }

    /**
     * Setter to set the {@link Integer} player's target round.
     * @param targetRound {@link Integer}
     */
    public void setTargetRound(int targetRound){
        gameData.setTargetRound(targetRound);
    }

    /**
     * Creates a {@link GameData} object.
     * @return {@link GameData}
     */
    public GameData createGameData() {
        if(gameData.getDifficulty() == 1){
            setMoney(40);
        } else {
            setMoney(20);
        }
        setPoint(0);
        return gameData;
    }

    /**
     * Creates an {@link InventoryData} object with the given array of {@link Tower}s.
     * @param towers an array of {@link Tower}s
     * @return {@link InventoryData}
     */
    public InventoryData createInventory(Tower[] towers){
        InventoryData inventoryData = new InventoryData();
        Tower[] mainTowers = new Tower[inventoryData.getMainTowers().length];
        System.arraycopy(towers, 0, mainTowers, 0, towers.length);
        inventoryData.setMainTowers(mainTowers);
        return inventoryData;
    }
}
