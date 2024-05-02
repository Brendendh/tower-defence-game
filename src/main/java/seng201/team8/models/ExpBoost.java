package seng201.team8.models;

import seng201.team8.services.TowerStatsManager;

import java.util.ArrayList;

public class ExpBoost implements Effect, Cloneable{
    private int boostAmount;
    private TowerStatsManager towerStatsManager;

    public ExpBoost(int boostAmount){
        this.boostAmount = boostAmount;
        this.towerStatsManager = new TowerStatsManager();
    }

    @Override
    public void affects(ArrayList<Tower> towers) {
        for(Tower tower: towers){
            towerStatsManager.addExp(tower, boostAmount);
        }
    }

    @Override
    public ExpBoost clone(){
        try{
            return (ExpBoost) super.clone();
        }
        catch (CloneNotSupportedException e){
            return new ExpBoost(this.getBoostAmount());
        }
    }

    public int getBoostAmount(){
        return boostAmount;
    }
    public void setBoostAmount(int boostAmount){
        this.boostAmount = boostAmount;
    }

}
