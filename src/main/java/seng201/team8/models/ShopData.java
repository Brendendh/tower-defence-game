package seng201.team8.models;

public class ShopData {
    private Tower[] towersSold;
    private Upgrade[] upgradesSold;

    private int initializedRoundNumber;

    public void setInitializedRoundNumber(int i){
        this.initializedRoundNumber = i;
    }
    public int getInitializedRoundNumber(){
        return initializedRoundNumber;
    }
    public Tower[] getTowersSold() {
        return towersSold;
    }

    public Upgrade[] getUpgradesSold() {
        return upgradesSold;
    }

    public void setTowersSold(Tower[] towersSold) {
        this.towersSold = towersSold;
    }

    public void setUpgradesSold(Upgrade[] upgradesSold) {
        this.upgradesSold = upgradesSold;
    }
}
