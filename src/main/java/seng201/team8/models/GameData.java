package seng201.team8.models;

public class GameData {
    private String playerName;
    private int targetRound;
    private int round;
    private int difficulty;
    private int money;
    private int point;

    public GameData() {
        money = 0;
        point = 0;
        round = 1;
    }

    public int getTargetRound() {
        return targetRound;
    }

    public int getRound() {
        return round;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getMoney() {
        return money;
    }

    public int getPoint() {
        return point;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setName(String playerName) {
        this.playerName = playerName;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setTargetRound(int targetRound) {
        this.targetRound = targetRound;
    }
}
