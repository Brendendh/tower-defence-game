package seng201.team8.models;

public class Tower extends Item implements Cloneable{
    private TowerStats towerStats;
    private int level;
    private int experiencePoints;

    private boolean isBroken;
    private String name;

    public Tower(String name, TowerStats towerStats, int buyingPrice, Rarity rarity){
        super(buyingPrice * rarity.rarityStatMultiplier, 10, rarity);

        this.name = name;
        this.towerStats = towerStats;
        this.towerStats.setResourceAmount(this.towerStats.getResourceAmount()* rarity.rarityStatMultiplier);
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

    public Tower clone(){
        Tower clonedTower = null;
        try {
            clonedTower = (Tower) super.clone();
        }
        catch (CloneNotSupportedException e){
            clonedTower = new Tower(this.getName(), this.getTowerStats(), this.getBuyingPrice(),this.getRarity());
        }
        clonedTower.towerStats = (TowerStats) this.towerStats.clone();
        return clonedTower;
    }

}
