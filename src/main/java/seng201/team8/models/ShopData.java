package seng201.team8.models;

public class ShopData {
    private Tower[] towersSold;
    private Upgrade[] upgradesSold;

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
