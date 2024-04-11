package seng201.team8.models;

import java.util.ArrayList;

public class InventoryData {
    private Tower[] mainTowers;
    private Tower[] reserveTowers;
    private ArrayList<Upgrade> upgrades;

    public InventoryData(){
        mainTowers = new Tower[5];
        reserveTowers = new Tower[5];
        upgrades = new ArrayList<>();
    }

    public Tower[] getReserveTowers() {
        return reserveTowers;
    }

    public Tower[] getMainTowers() {
        return mainTowers;
    }

    public ArrayList<Upgrade> getUpgrades() {
        return upgrades;
    }

    public void setReserveTowers(Tower[] reserveTowers) {
        this.reserveTowers = reserveTowers;
    }

    public void setMainTowers(Tower[] mainTowers) {
        this.mainTowers = mainTowers;
    }

    public void setUpgrades(ArrayList<Upgrade> upgrades) {
        this.upgrades = upgrades;
    }
}
