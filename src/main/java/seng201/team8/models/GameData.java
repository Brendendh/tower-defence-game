package seng201.team8.models;

public class GameData {
    private String playerName;
    private Integer targetRound;
    private Integer round;
    private Integer difficulty;
    private Integer money;
    private Integer point;

    public GameData() {
        money = 0;
        point = 0;
    }

    public Integer getTargetRound() {
        return targetRound;
    }

    public Integer getRound() {
        return round;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getPoint() {
        return point;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public void setName(String playerName) {
        this.playerName = playerName;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public void setTargetRound(Integer targetRound) {
        this.targetRound = targetRound;
    }
}
