package seng201.team8.models;

public class Tower extends Item{
    private TowerStats towerStats;
    private int level;
    private int experiencePoints;

    private boolean isBroken;
    private String name;

    private int[] levelRequirements;

    public Tower(String name, TowerStats towerStats, int buyingPrice, Rarity rarity){
        super(buyingPrice * rarity.rarityStatMultiplier, 0, rarity);

        this.name = name;
        this.towerStats = towerStats;
        this.towerStats.setResourceAmount(this.towerStats.getResourceAmount()* rarity.rarityStatMultiplier);
        this.levelRequirements = new int[]{2, 3, 5};
    }

    public TowerStats getTowerStats(){
        return towerStats;
    }

    public int getLevel(){
        return level;
    }

    public int getExperiencePoints(){
        return experiencePoints;
    }

    public String getName(){
        return name;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public int[] getLevelRequirements() {
        return levelRequirements;
    }

    public void setTowerStats(TowerStats towerStats) {
        this.towerStats = towerStats;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public void setLevelRequirements(int[] levelRequirements) {
        this.levelRequirements = levelRequirements;
    }

}
