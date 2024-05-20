package seng201.team8.models;

/**
 * A model that stores the game data such as the player name, target round
 * number, the current round number, the chosen difficulty, and the player's
 * current money and points.
 *<p></p>
 * Is created upon the start of the game by the GameSetupService and stored
 * in the GameManager
 *
 * @see seng201.team8.services.GameManager
 * @see seng201.team8.services.GameSetupService
 */
public class GameData {
    /**
     * A string representing the player name
     */
    private String playerName;
    /**
     * An integer representing the target round number
     */
    private int targetRound;
    /**
     * An integer representing the current round number
     */
    private int round;
    /**
     * An integer representing the difficulty scaling
     */
    private int difficulty;
    /**
     * An integer representing the player's current money count
     */
    private int money;
    /**
     * An integer representing the player's current point count
     */
    private int point;

    /**
     * The constructor for GameData that takes in no parameters.
     * <p></p>
     * Sets player money, point to 0 and current round to 1 upon
     * creation.
     */

    public GameData() {
        money = 0;
        point = 0;
        round = 1;
    }

    /**
     * Returns the target round number
     * @return {@link GameData#targetRound}
     */
    public int getTargetRound() {
        return targetRound;
    }

    /**
     * Returns the current round number
     * @return {@link GameData#round}
     */
    public int getRound() {
        return round;
    }

    /**
     * Returns the difficulty number
     * @return {@link GameData#difficulty}
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Returns the player name string
     * @return {@link GameData#playerName}
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Returns the player's current money count
     * @return {@link GameData#money}
     */
    public int getMoney() {
        return money;
    }

    /**
     * Returns the player's current point count
     * @return {@link GameData#point}
     */
    public int getPoint() {
        return point;
    }

    /**
     * Sets the current round number to the specified round
     * number
     * @param round The specified integer round number
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Sets the player name to the given player name
     * @param playerName The String to set the player name to
     */
    public void setName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Sets the difficulty scaling to the specified integer
     * @param difficulty The new difficulty scaling Integer
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Sets the player money to the specified integer
     * @param money The Intger amount to set the player money to
     */
    public void setMoney(int money) {
        this.money = money;
    }
    /**
     * Sets the player points to the specified integer
     * @param point The Intger amount to set the player points to
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * Sets the target round number to the given Integer
     * @param targetRound The Integer to set the target round to
     */
    public void setTargetRound(int targetRound) {
        this.targetRound = targetRound;
    }
}
